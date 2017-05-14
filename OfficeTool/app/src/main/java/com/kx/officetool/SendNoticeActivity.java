package com.kx.officetool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.kx.officetool.infos.Actions;
import com.kx.officetool.infos.DepartMent;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.DensityUtil;
import com.kx.officetool.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class SendNoticeActivity extends BaseActivity implements View.OnClickListener {
    EditText title, message;
    Spinner mSpinner = null;
    List<DepartMent> department = null;
    List<Integer> sendDepartmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notice);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_post_board).setOnClickListener(this);
        title = (EditText) findViewById(R.id.edit_title);
        message = (EditText) findViewById(R.id.edit_message);
        department = mAppConfig.mCompanyInfo.getDepartment();
        mSpinner.setAdapter(mAdapter);
    }

    BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return department == null ? 0 : department.size() == 1 ? 1 : department.size() + 1;
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
                textView = new TextView(SendNoticeActivity.this);
                int padding = DensityUtil.dp2px(SendNoticeActivity.this, 12);
                textView.setPadding(padding, padding, padding, padding);
                convertView = textView;
                convertView.setTag(textView);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            } else textView = (TextView) convertView.getTag();
            if (department.size() == 1) textView.setText(department.get(position).getPartname());
            else if (position == 0) textView.setText("所有");
            else
                textView.setText(department.get(position - 1).getPartname());
            return convertView;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_post_board:
                final String titleTxt = title.getText().toString();
                final String messageTxt = message.getText().toString();
                if (TextUtils.isEmpty(titleTxt) || TextUtils.isEmpty(messageTxt)) {
                    ToastUtil.showShort(SendNoticeActivity.this, "标题或内容不能为空！");
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int uid = mAppConfig.mUserInfo.getId();
                        int position = mSpinner.getSelectedItemPosition();
                        if (department.size() == 1) {
                            int departId = department.get(position).getId();
                            if (!sendDepartmentList.contains(departId))
                                sendDepartmentList.add(departId);
                        } else if (position == 0) {
                            for (DepartMent departMent : department) {
                                int departId = departMent.getId();
                                if (!sendDepartmentList.contains(departId))
                                    sendDepartmentList.add(departId);
                            }
                        } else {
                            int departId = department.get(position - 1).getId();
                            if (!sendDepartmentList.contains(departId))
                                sendDepartmentList.add(departId);
                        }
                        WebService.getInstance().createNotice(uid, sendDepartmentList, titleTxt, messageTxt);
                        sendBroadcast(new Intent(Actions.ACTION_ON_POST_NOTICE_RETACHED));
                        finish();
                    }
                }).start();
                break;
        }
    }
}
