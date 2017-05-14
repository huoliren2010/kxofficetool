package com.kx.officetool;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kx.officetool.fragment.SignFillFragment;
import com.kx.officetool.infos.DailySign;
import com.kx.officetool.infos.DailySignInfo;
import com.kx.officetool.infos.LoadUserAvatar;
import com.kx.officetool.infos.MyAddress;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.SharedPreferencesUtil;
import com.kx.officetool.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class DailySignActivity extends BaseActivity {
    FragmentManager mFragmentManager = null;
    SignFillFragment mSignFillFragment;
    BdLocationFragment mBdLocationFragment;
    List<DailySign> mListSignInfos = null;
    public MyAddress mMyAddress = new MyAddress();
    UserInfo mUserInfo;
    View mMainContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_sign);
        mMainContent = findViewById(R.id.main_root);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUserInfo = mAppConfig.getUserInfo();
        mFragmentManager = getSupportFragmentManager();
        mSignFillFragment = new SignFillFragment();
        mBdLocationFragment = new BdLocationFragment();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.viewroot_fragments, mSignFillFragment).add(R.id.viewroot_fragments, mBdLocationFragment).
                hide(mSignFillFragment).hide(mBdLocationFragment).commit();
        findViewById(R.id.btn_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignFillFragment(mMyAddress.getAddress());
            }
        });
        final ListView mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mListAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                queryDailySigns();
            }
        }).start();
    }

    private void queryDailySigns() {
        mListSignInfos = WebService.getInstance().queryDailySign(mUserInfo.getId(), mUserInfo.getDepartmentid());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mListAdapter.notifyDataSetChanged();
    }

    private BaseAdapter mListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mListSignInfos == null ? 0 : mListSignInfos.size();
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
                convertView = LayoutInflater.from(DailySignActivity.this).inflate(R.layout.layout_sign_item, null);
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
                viewHolder.nickName = (TextView) convertView.findViewById(R.id.name);
                viewHolder.address = (TextView) convertView.findViewById(R.id.address);
                viewHolder.dateTime = (TextView) convertView.findViewById(R.id.datetime);
                convertView.setTag(viewHolder);
            } else viewHolder = (ViewHolder) convertView.getTag();
            LoadUserAvatar.getInstance(DailySignActivity.this).loadAvatar(viewHolder.avatar, mUserInfo.getUserAvatar());
            viewHolder.nickName.setText(mUserInfo.getUserName());
            DailySign dailySign = mListSignInfos.get(position);
            viewHolder.address.setText(dailySign.getAddress());
            viewHolder.dateTime.setText(dailySign.getTime());
            return convertView;
        }
    };

    public void addDailySignInfo(String message, final String time, final String address) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DailySign dailySign = WebService.getInstance().createDailySign(mUserInfo.getId(), mUserInfo.getDepartmentid(), address);
                if (dailySign != null) {
                    if (mListSignInfos == null) mListSignInfos = new ArrayList<DailySign>();
                    mListSignInfos.add(dailySign);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mListAdapter.notifyDataSetChanged();
                        }
                    });
                } else runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showShort(DailySignActivity.this, "签到异常!");
                    }
                });
            }
        }).start();
    }

    class ViewHolder {
        ImageView avatar;
        TextView nickName, address, dateTime;
    }

    int fragment = 0;

    public void showSignFillFragment(String fillLocation) {
        if (mMainContent.getVisibility() == View.VISIBLE) mMainContent.setVisibility(View.GONE);
        mSignFillFragment.setLocation(fillLocation);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.show(mSignFillFragment).hide(mBdLocationFragment).commit();
        fragment = 1;
    }

    public void showBdLocationFragment() {
        mBdLocationFragment.setMyAddress(mMyAddress);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.show(mBdLocationFragment).hide(mSignFillFragment).commit();
        fragment = 2;
    }

    @Override
    public void onBackPressed() {
        if (fragment == 2) {
            showSignFillFragment(mMyAddress.getAddress());
            return;
        } else if (fragment == 1) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.hide(mSignFillFragment).hide(mBdLocationFragment).commit();
            mMainContent.setVisibility(View.VISIBLE);
            fragment = 0;
            return;
        }
        super.onBackPressed();
    }
}
