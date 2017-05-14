package com.kx.officetool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kx.officetool.infos.Actions;
import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.service.IWebService;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.DensityUtil;
import com.kx.officetool.utils.KeyBoardUtil;
import com.kx.officetool.utils.SharedPreferencesUtil;
import com.kx.officetool.utils.ToastUtil;

import java.util.List;

public class JoinCompanyActivity extends BaseActivity implements View.OnClickListener {
    private View mTvTitle, mToppanelInputSearch, mIvClearEdittextTxt, mContentpanelInputSearch, mNoInfoHintView;
    private EditText mEditText;
    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_company);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview);
        mTvTitle = findViewById(R.id.tv_title);
        mToppanelInputSearch = findViewById(R.id.toppanel_input_search);
        mIvClearEdittextTxt = findViewById(R.id.iv_clear_edittext_content);
        mIvClearEdittextTxt.setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.editText);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    mIvClearEdittextTxt.setVisibility(View.VISIBLE);
                } else mIvClearEdittextTxt.setVisibility(View.GONE);
            }
        });
        mContentpanelInputSearch = findViewById(R.id.contentpanel_input_search);
        mContentpanelInputSearch.setOnClickListener(this);
        showTopPannelInputSearch(false);
        mNoInfoHintView = findViewById(R.id.tv_noanycompany_info);
        mNoInfoHintView.setVisibility(View.GONE);
    }

    /**
     * 显示顶部输入栏
     *
     * @param show true：显示 false:隐藏
     */
    private void showTopPannelInputSearch(boolean show) {
        if (show) {
            mTvTitle.setVisibility(View.GONE);
            mIvClearEdittextTxt.setVisibility(View.GONE);
            mContentpanelInputSearch.setVisibility(View.GONE);
            mToppanelInputSearch.setVisibility(View.VISIBLE);
            mEditText.setText("");
            mEditText.setError(null);
            mEditText.setFocusable(true);
            mEditText.setFocusableInTouchMode(true);
            mEditText.requestFocus();
            KeyBoardUtil.openKeybord(mEditText, JoinCompanyActivity.this);
        } else {
            KeyBoardUtil.closeKeybord(mEditText, JoinCompanyActivity.this);
            mTvTitle.setVisibility(View.VISIBLE);
            mIvClearEdittextTxt.setVisibility(View.VISIBLE);
            mContentpanelInputSearch.setVisibility(View.VISIBLE);
            mToppanelInputSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mToppanelInputSearch.getVisibility() == View.VISIBLE) {
            showTopPannelInputSearch(false);
        } else
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (mToppanelInputSearch.getVisibility() == View.VISIBLE) {
                    showTopPannelInputSearch(false);
                } else finish();
                break;
            case R.id.iv_clear_edittext_content:
                mEditText.setText("");
                mIvClearEdittextTxt.setVisibility(View.GONE);
                break;
            case R.id.btn_search:
                if (TextUtils.isEmpty(mEditText.getText().toString())) {
                    mEditText.setError(getResources().getString(R.string.error_field_required));
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<CompanyInfo> list = WebService.getInstance().QueryCompanyByCompanyName(mEditText.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.setVisibility(View.VISIBLE);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        joinComany(mAppConfig.mUserInfo.getId(), list.get(position).getId());
                                    }
                                });
                                listView.setAdapter(new BaseAdapter() {
                                    @Override
                                    public int getCount() {
                                        return list == null ? 0 : list.size();
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
                                            textView = new TextView(JoinCompanyActivity.this);
                                            int padding = DensityUtil.dp2px(JoinCompanyActivity.this, 12.0f);
                                            textView.setPadding(padding, padding, padding, padding);
                                            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                                            convertView = textView;
                                            convertView.setTag(textView);
                                        } else textView = (TextView) convertView.getTag();
                                        textView.setText(list.get(position).getCompanyName());
                                        return convertView;
                                    }
                                });
                            }
                        });
                    }
                }).start();
                break;
            case R.id.contentpanel_input_search:
                showTopPannelInputSearch(true);
                break;
        }
    }

    private void joinComany(final int id, final int cid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int departId = WebService.getInstance().JoinCompany(id, cid);
                if (departId != 0) {
                    mAppConfig.mUserInfo.setDepartmentid(departId);
                    mAppConfig.saveUserInfo(mAppConfig.mUserInfo);
                    WebService.getInstance().updateUserInfo(mAppConfig.mUserInfo);
                    mAppConfig.refreshCompanyInfo();
                    sendBroadcast(new Intent(Actions.ACTION_ON_COMPANY_ATTACHED));
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(JoinCompanyActivity.this, "加入公司失败！", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }
        }).start();
    }

    void onNoAnyCompanyInfoAttached() {
        mNoInfoHintView.setVisibility(View.VISIBLE);
    }
}
