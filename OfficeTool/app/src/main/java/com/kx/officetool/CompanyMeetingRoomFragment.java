package com.kx.officetool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.MeetingRoom;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.utils.SharedPreferencesUtil;

import java.util.List;

public class CompanyMeetingRoomFragment extends Fragment implements View.OnClickListener{
    CompanyInfo mCompanyInfo;
    ListView mListView;
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
        UserInfo userInfo = SharedPreferencesUtil.getObject(getActivity(), UserInfo.KEY_USERINFO_OBJ, UserInfo.class);
        if(userInfo.getLevel() != UserInfo.Level.NORMAL){
            View addBtn = viewRoot.findViewById(R.id.tv_add_meetingroom);
            addBtn.setVisibility(View.VISIBLE);
            addBtn.setOnClickListener(this);
        }else
            viewRoot.findViewById(R.id.tv_add_meetingroom).setVisibility(View.GONE);
        mCompanyInfo = SharedPreferencesUtil.getObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, CompanyInfo.class);
        final List<MeetingRoom> companyMeetingRooms = mCompanyInfo.getCompanyMeetingRooms();
        mListView = (ListView) viewRoot.findViewById(R.id.listview);
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return companyMeetingRooms == null ? 0: companyMeetingRooms.size();
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
                if(convertView == null){
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_meetingroom, null);
                    viewHolder.mTvMeetingRoom = (TextView) convertView.findViewById(R.id.tv_meetingroom);
                    viewHolder.mTvMeetingRoomStatus = (TextView) convertView.findViewById(R.id.tv_meetingroom_status);
                    viewHolder.mTvMeetingRoomTime = (TextView) convertView.findViewById(R.id.tv_meetingroom_time);
                    convertView.setTag(viewHolder);
                }else viewHolder = (ViewHolder) convertView.getTag();
                MeetingRoom meetingRoom = companyMeetingRooms.get(position);
                viewHolder.mTvMeetingRoom.setText(meetingRoom.getRoomNumber());
                viewHolder.mTvMeetingRoomStatus.setText(meetingRoom.getRoomStatus());
                if(meetingRoom.getStatus() != MeetingRoom.Status.IDLE) {
                    viewHolder.mTvMeetingRoomTime.setVisibility(View.VISIBLE);
                    viewHolder.mTvMeetingRoomTime.setText(String.format(getResources().getString(R.string.string_format_meetingroom_time), meetingRoom.getFromTime(), meetingRoom.getEndTime()));
                }else viewHolder.mTvMeetingRoomTime.setVisibility(View.GONE);
                return convertView;
            }
        });
    }

    class ViewHolder{
        TextView mTvMeetingRoom;
        TextView mTvMeetingRoomStatus;
        TextView mTvMeetingRoomTime;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_meetingroom:
                break;

        }
    }
}
