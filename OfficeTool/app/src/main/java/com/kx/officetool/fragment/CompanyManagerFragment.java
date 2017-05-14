package com.kx.officetool.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kx.officetool.AppConfig;
import com.kx.officetool.BaseActivity;
import com.kx.officetool.ChooseUserActivity;
import com.kx.officetool.R;
import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.LoadUserAvatar;
import com.kx.officetool.infos.Manager;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.SharedPreferencesUtil;
import com.kx.officetool.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.kx.officetool.infos.Actions.ACTION_ON_COMPANY_ATTACHED;

public class CompanyManagerFragment extends Fragment implements View.OnClickListener {
    CompanyInfo mCompanyInfo;
    ListView mListView;
    List<UserInfo> listMangers = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_manager, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View viewRoot = getView();
        viewRoot.findViewById(R.id.tv_add_manager).setOnClickListener(this);
        if (AppConfig.getInstance().isManager()) {
            viewRoot.findViewById(R.id.tv_add_manager).setVisibility(View.VISIBLE);
        } else viewRoot.findViewById(R.id.tv_add_manager).setVisibility(View.GONE);
        mCompanyInfo = AppConfig.getInstance().mCompanyInfo;
        listMangers = mCompanyInfo.getManager();
        if (listMangers == null) listMangers = new ArrayList<>(0);
        mListView = (ListView) viewRoot.findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
    }

    BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listMangers == null ? 0 : listMangers.size();
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.row_contact, null);
                viewHolder.header = (TextView) convertView.findViewById(R.id.header);
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.signature = (TextView) convertView.findViewById(R.id.signature);
                viewHolder.unread_msg_number = (TextView) convertView.findViewById(R.id.unread_msg_number);
                convertView.setTag(viewHolder);
            } else viewHolder = (ViewHolder) convertView.getTag();
            LoadUserAvatar.getInstance(getActivity()).loadAvatar(viewHolder.avatar, listMangers.get(position).getUserAvatar());
            viewHolder.name.setText(listMangers.get(position).getUserName());
            return convertView;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_manager:
                showAddDialog();
                break;
            case R.id.btn_cancel:
                if (mAddDialog != null && mAddDialog.isShowing()) mAddDialog.dismiss();
                mAddDialog = null;
                break;
            case R.id.btn_ensure:
                if (mChooseUserInfo == null) {
                    ToastUtil.show(getActivity(), "未选择要添加的管理员", Toast.LENGTH_SHORT);
                    return;
                }
                ((BaseActivity) getActivity()).showProgressDlg();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserInfo mUserInfo = WebService.getInstance().createManager(mChooseUserInfo.getId(), mCompanyInfo.getId());
                        if (mUserInfo != null) {
                            listMangers.add(mUserInfo);
                            AppConfig.getInstance().mCompanyInfo.setManager(listMangers);
                            SharedPreferencesUtil.putObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, AppConfig.getInstance().mCompanyInfo);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                ((BaseActivity) getActivity()).hideProgressDlg();
                                if (mAddDialog != null && mAddDialog.isShowing())
                                    mAddDialog.dismiss();
                                mAddDialog = null;
                            }
                        });
                    }
                }).start();
                break;
            case R.id.rl_contacts:
                getActivity().startActivity(new Intent(getActivity(), ChooseUserActivity.class));
                break;
        }
    }

    Dialog mAddDialog = null;
    TextView mTvContacts = null;

    public void showAddDialog() {
        if (mAddDialog == null)
            mAddDialog = new Dialog(getActivity());
        mAddDialog.setContentView(R.layout.layout_dialog_addmanager);
        mAddDialog.findViewById(R.id.rl_contacts).setOnClickListener(this);
        mAddDialog.findViewById(R.id.btn_cancel).setOnClickListener(this);
        mAddDialog.findViewById(R.id.btn_ensure).setOnClickListener(this);
        mTvContacts = (TextView) mAddDialog.findViewById(R.id.textview);
        mAddDialog.show();
    }

    UserInfo mChooseUserInfo = null;

    class ViewHolder {
        TextView header;
        ImageView avatar;
        TextView name;
        TextView signature;
        TextView unread_msg_number;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(ACTION_ON_COMPANY_ATTACHED));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }


    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                mChooseUserInfo = (UserInfo) intent.getSerializableExtra("userinfo");
                mTvContacts.setText(mChooseUserInfo.getUserName() + "\n" + mChooseUserInfo.getPhoneNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
