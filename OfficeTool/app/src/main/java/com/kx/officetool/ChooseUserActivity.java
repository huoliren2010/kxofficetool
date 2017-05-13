package com.kx.officetool;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.utils.DensityUtil;

import java.util.List;

public class ChooseUserActivity extends BaseActivity {
    List<UserInfo> mList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        CompanyInfo companyInfo = mAppConfig.mCompanyInfo;
        if(companyInfo != null){
            mList = companyInfo.getCompanyMembers();
        }
        ListView mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    UserInfo userInfo = mList.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("userinfo", userInfo);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
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
            TextView textView = null;
            if (convertView == null) {
                textView = new TextView(ChooseUserActivity.this);
                int padding = DensityUtil.dp2px(ChooseUserActivity.this, 12.0f);
                textView.setPadding(padding, padding, padding, padding);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                convertView = textView;
                convertView.setTag(textView);
            } else textView = (TextView) convertView.getTag();
            textView.setText(mList.get(position).getUserName());
            return convertView;
        }
    };
}
