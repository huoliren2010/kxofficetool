package com.kx.officetool.fragment;

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

import com.kx.officetool.R;
import com.kx.officetool.infos.LoadUserAvatar;

public class MessageFragment extends Fragment {
    ListView mListView =null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View viewRoot = getView();
        mListView = (ListView) viewRoot.findViewById(R.id.listview);
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 1;
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
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.row_contact, null);
                    viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.avatar);
                    viewHolder.nickName = (TextView) convertView.findViewById(R.id.name);
                    viewHolder.lastMsg = (TextView) convertView.findViewById(R.id.signature);
                    convertView.setTag(viewHolder);
                }else viewHolder = (ViewHolder) convertView.getTag();
                if(position == 0){
                    viewHolder.ivAvatar.setImageResource(R.mipmap.ic_officetool_helper);
                    viewHolder.nickName.setText(R.string.string_helper_name);
                    viewHolder.lastMsg.setVisibility(View.VISIBLE);
                    viewHolder.lastMsg.setText("很高兴为你服务(❁ᴗ͈ˬᴗ͈)");
                }
                return convertView;
            }
        });
    }
    class ViewHolder{
        ImageView ivAvatar;
        TextView nickName;
        TextView lastMsg;
    }
}
