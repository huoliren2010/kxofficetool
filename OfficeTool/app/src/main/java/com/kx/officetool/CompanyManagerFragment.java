package com.kx.officetool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.LoadUserAvatar;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.utils.SharedPreferencesUtil;

import java.util.List;

public class CompanyManagerFragment extends Fragment implements View.OnClickListener{
    CompanyInfo mCompanyInfo;
    ListView mListView;

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
        mCompanyInfo = SharedPreferencesUtil.getObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, CompanyInfo.class);
        final List<UserInfo> listMangers = mCompanyInfo.getManager();
        mListView = (ListView) viewRoot.findViewById(R.id.listview);
        mListView.setAdapter(new BaseAdapter() {
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
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_manager:
                break;
        }
    }

    class ViewHolder {
        TextView header;
        ImageView avatar;
        TextView name;
        TextView signature;
        TextView unread_msg_number;
    }
}
