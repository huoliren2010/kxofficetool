package com.kx.officetool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.kx.officetool.infos.DepartMent;
import com.kx.officetool.infos.Notice;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.kx.officetool.infos.Actions.ACTION_ON_POST_NOTICE_RETACHED;

public class BroadActivity extends BaseActivity implements View.OnClickListener {
    boolean isNormalMode = true;
    List<Notice> mList = null;
    List<Notice> mEditList = new ArrayList<>();
    boolean isManager = false;
    Button mBtnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isManager = mAppConfig.isManager();
        registerReceiver(mBroadcastReceiver, new IntentFilter(ACTION_ON_POST_NOTICE_RETACHED));
        setContentView(R.layout.activity_broad);
        mBtnDel = (Button) findViewById(R.id.btn_delete);
        mBtnDel.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_post_board).setOnClickListener(this);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Notice notice = mList.get(position);
                    Intent intent = new Intent(BroadActivity.this, NoticeDetailActivity.class);
                    intent.putExtra("notice", notice);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setAdapter(mAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadNotices();
            }
        }).start();
    }

    public void loadNotices() {
        if (mList != null) mList.clear();
        if (isManager) {
            List<DepartMent> department = mAppConfig.mCompanyInfo.getDepartment();
            for (DepartMent departMent : department) {
                List<Notice> notices = WebService.getInstance().QueryNotices(departMent.getId());
                if (mList == null) mList = new ArrayList<>();
                if (notices != null)
                    mList.addAll(notices);
            }
        } else
            mList = WebService.getInstance().QueryNotices(mAppConfig.mUserInfo.getDepartmentid());
        if (mList != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mBtnDel.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
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
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(BroadActivity.this).inflate(R.layout.layout_broadact_item, null);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.message = (TextView) convertView.findViewById(R.id.message);
                viewHolder.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder.partname = (TextView) convertView.findViewById(R.id.partname);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                convertView.setTag(viewHolder);
            } else viewHolder = (ViewHolder) convertView.getTag();
            final Notice notice = mList.get(position);
            viewHolder.time.setText(notice.getTime());
            viewHolder.title.setText(notice.getTitle());
            viewHolder.message.setText(notice.getMessage());
            viewHolder.partname.setText(getPartName(notice.getDepartid()));
            if (isNormalMode) {
                viewHolder.checkBox.setVisibility(View.GONE);
            } else viewHolder.checkBox.setVisibility(View.VISIBLE);
            if (mEditList.contains(notice)) viewHolder.checkBox.setChecked(true);
            else viewHolder.checkBox.setChecked(false);
            if (isManager) {
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!isNormalMode) return false;
                        if (isNormalMode) isNormalMode = false;
                        if (!mEditList.contains(notice)) mEditList.add(notice);
                        mBtnDel.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged();
                        return false;
                    }
                });
            }
            return convertView;
        }
    };

    private String getPartName(int departid) {
        List<DepartMent> department = mAppConfig.mCompanyInfo.getDepartment();
        for (DepartMent departMent : department) {
            if (departMent.getId() == departid) return departMent.getPartname();
        }
        return "";
    }

    class ViewHolder {
        TextView title;
        TextView message;
        TextView time;
        TextView partname;
        CheckBox checkBox;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_post_board:
                if (!mAppConfig.isManager()) {
                    ToastUtil.showShort(BroadActivity.this, "只有管理员才能发布公告！");
                    return;
                }
                startActivity(new Intent(BroadActivity.this, SendNoticeActivity.class));
                refresh();
                break;
            case R.id.btn_delete:
                if (mEditList.isEmpty()) {
                    ToastUtil.showShort(BroadActivity.this, "没有选择项！");
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        isNormalMode = true;
                        WebService.getInstance().deleteNotice(mEditList);
                        mEditList.clear();
                        loadNotices();
                    }
                }).start();
                break;
        }
    }

    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    isNormalMode = true;
                    mEditList.clear();
                    loadNotices();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    };
}
