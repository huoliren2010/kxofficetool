package com.kx.officetool.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kx.officetool.AppConfig;
import com.kx.officetool.BaseActivity;
import com.kx.officetool.R;
import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.MeetingRoom;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.SharedPreferencesUtil;
import com.kx.officetool.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CompanyMeetingRoomFragment extends Fragment implements View.OnClickListener {
    CompanyInfo mCompanyInfo;
    ListView mListView;
    List<MeetingRoom> companyMeetingRooms = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_meetingroom, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View viewRoot = getView();
        viewRoot.findViewById(R.id.tv_add_meetingroom).setOnClickListener(this);
        if (AppConfig.getInstance().isManager()) {
            View addBtn = viewRoot.findViewById(R.id.tv_add_meetingroom);
            addBtn.setVisibility(View.VISIBLE);
            addBtn.setOnClickListener(this);
        } else
            viewRoot.findViewById(R.id.tv_add_meetingroom).setVisibility(View.GONE);
        mCompanyInfo = SharedPreferencesUtil.getObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, CompanyInfo.class);
        companyMeetingRooms = mCompanyInfo.getCompanyMeetingRooms();
        mListView = (ListView) viewRoot.findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
    }
    BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return companyMeetingRooms == null ? 0 : companyMeetingRooms.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_meetingroom, null);
                viewHolder.mTvMeetingRoom = (TextView) convertView.findViewById(R.id.tv_meetingroom);
                viewHolder.mTvMeetingRoomStatus = (TextView) convertView.findViewById(R.id.tv_meetingroom_status);
                viewHolder.mTvMeetingRoomTime = (TextView) convertView.findViewById(R.id.tv_meetingroom_time);
                convertView.setTag(viewHolder);
            } else viewHolder = (ViewHolder) convertView.getTag();
            MeetingRoom meetingRoom = companyMeetingRooms.get(position);
            viewHolder.mTvMeetingRoom.setText(meetingRoom.getRoomname());
            viewHolder.mTvMeetingRoomStatus.setText(meetingRoom.getRoomStatus());
            if (meetingRoom.getStatus() != MeetingRoom.Status.IDLE) {
                viewHolder.mTvMeetingRoomTime.setVisibility(View.VISIBLE);
                viewHolder.mTvMeetingRoomTime.setText(String.format(getResources().getString(R.string.string_format_meetingroom_time), meetingRoom.getFromTime(), meetingRoom.getEndTime()));
            } else viewHolder.mTvMeetingRoomTime.setVisibility(View.GONE);
            return convertView;
        }
    };
    class ViewHolder {
        TextView mTvMeetingRoom;
        TextView mTvMeetingRoomStatus;
        TextView mTvMeetingRoomTime;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_meetingroom:
                showAddDialog();
                break;
            case R.id.btn_cancel:
                if (mAddDialog != null && mAddDialog.isShowing()) mAddDialog.dismiss();
                mAddDialog = null;
                break;
            case R.id.btn_ensure:
                if (TextUtils.isEmpty(mEditText.getText().toString())) {
                    ToastUtil.show(getActivity(), "会议室名字不能为空！", Toast.LENGTH_SHORT);
                    return;
                }
                ((BaseActivity) getActivity()).showProgressDlg();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppConfig appConfig = AppConfig.getInstance();
                        String name = mEditText.getText().toString();
                       MeetingRoom meetingRoom = WebService.getInstance().createMeetinRoom(name, appConfig.mCompanyInfo.getCompanyId());
                        if(meetingRoom != null){
                            if(companyMeetingRooms == null)
                                companyMeetingRooms = new ArrayList<MeetingRoom>();
                                companyMeetingRooms.add(meetingRoom);
                            appConfig.mCompanyInfo.setCompanyMeetingRooms(companyMeetingRooms);
                            appConfig.refreshCompanyInfo();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                ((BaseActivity) getActivity()).hideProgressDlg();
                                if (mAddDialog != null && mAddDialog.isShowing()) mAddDialog.dismiss();
                                mAddDialog = null;
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    Dialog mAddDialog = null;
    EditText mEditText = null;

    public void showAddDialog() {
        if (mAddDialog == null)
            mAddDialog = new Dialog(getActivity());
        mAddDialog.setContentView(R.layout.layout_dialog_addmeetingroom);
        mAddDialog.findViewById(R.id.btn_cancel).setOnClickListener(this);
        mAddDialog.findViewById(R.id.btn_ensure).setOnClickListener(this);
        mEditText = (EditText) mAddDialog.findViewById(R.id.editText);
        mAddDialog.show();
    }
}
