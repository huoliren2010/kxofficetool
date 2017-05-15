package com.kx.officetool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kx.officetool.BaseActivity;
import com.kx.officetool.R;
import com.kx.officetool.infos.Actions;
import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.KeyBoardUtil;
import com.kx.officetool.utils.SharedPreferencesUtil;

public class CreateCompanyActivity extends BaseActivity implements View.OnClickListener {
    private View mIvClearEdittextTxt, mViewInputPanel, mViewResultPanel;
    private EditText mEditText;
    private TextView mTvResultCompanyName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_company);
        mViewInputPanel = findViewById(R.id.contentpanel_create_input_infos);
        mViewResultPanel = findViewById(R.id.contentpanel_result);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_sure).setOnClickListener(this);
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
                mIvClearEdittextTxt.setVisibility((s.length() != 0) ? View.VISIBLE : View.GONE);
            }
        });

        findViewById(R.id.btn_goto_company).setOnClickListener(this);
        mTvResultCompanyName = (TextView) findViewById(R.id.tv_companename);
        showInputPanel(true);
    }

    private void showInputPanel(boolean show) {
        focusEditText(show);
        if (show) {
            mViewResultPanel.setVisibility(View.GONE);
            mViewInputPanel.setVisibility(View.VISIBLE);
        } else {
            mViewResultPanel.setVisibility(View.VISIBLE);
            mViewInputPanel.setVisibility(View.GONE);
        }
    }

    private void focusEditText(boolean focus) {
        if (focus) {
            mEditText.setText("");
            mEditText.setError(null);
            mEditText.setFocusable(true);
            mEditText.setFocusableInTouchMode(true);
            mEditText.requestFocus();
            KeyBoardUtil.openKeybord(mEditText, CreateCompanyActivity.this);
        } else {
            KeyBoardUtil.closeKeybord(mEditText, CreateCompanyActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                focusEditText(false);
                finish();
                break;
            case R.id.btn_sure:
                if (TextUtils.isEmpty(mEditText.getText().toString())) {
                    mEditText.setError(getResources().getString(R.string.error_field_required));
                    return;
                }
                createCompanyWithName(mEditText.getText().toString());
                break;
            case R.id.iv_clear_edittext_content:
                mIvClearEdittextTxt.setVisibility(View.VISIBLE);
                mEditText.setText("");
                break;
            case R.id.btn_goto_company:
                break;
        }
    }

    @Override
    protected void onResume() {
        if (mViewInputPanel.getVisibility() == View.VISIBLE) {
            focusEditText(true);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        focusEditText(false);
        super.onPause();
    }

    /**
     * 创建公司逻辑
     *
     * @param companyName 公司名字
     */
    private void createCompanyWithName(final String companyName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CompanyInfo companyInfo = WebService.getInstance().createCompany(companyName, mAppConfig.mUserInfo.getId());
                mAppConfig.mUserInfo.setDepartmentid(companyInfo.getDepartment().get(0).getId());
                WebService.getInstance().updateUserInfo(mAppConfig.mUserInfo);
                mAppConfig.refreshCompanyInfo();
                createCompany(companyInfo);
            }
        }).start();
    }

    private void createCompany(final CompanyInfo companyInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvResultCompanyName.setText(String.format(getResources().getString(R.string.string_format_company_name), companyInfo.getCompanyName()));
                onCompanyCreateSuccess();
            }
        });
    }


    private void onCompanyCreateSuccess() {
        UserInfo mUsrInfo = SharedPreferencesUtil.getObject(CreateCompanyActivity.this, UserInfo.KEY_USERINFO_OBJ, UserInfo.class);
        mUsrInfo.setLevel(UserInfo.Level.BOSS);
        SharedPreferencesUtil.putObject(CreateCompanyActivity.this, UserInfo.KEY_USERINFO_OBJ, mUsrInfo);
        showInputPanel(false);
        sendBroadcast(new Intent(Actions.ACTION_ON_COMPANY_ATTACHED));
        finish();
    }
}
