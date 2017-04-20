package com.kx.officetool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.kx.officetool.utils.KeyBoardUtil;

public class JoinCompanyActivity extends AppCompatActivity implements View.OnClickListener {
    private View mTvTitle, mToppanelInputSearch, mIvClearEdittextTxt, mContentpanelInputSearch, mNoInfoHintView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_company);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
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
        }else
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
                if(TextUtils.isEmpty(mEditText.getText().toString())){
                    mEditText.setError(getResources().getString(R.string.error_field_required));
                    return;
                }
                break;
            case R.id.contentpanel_input_search:
                showTopPannelInputSearch(true);
                break;
        }
    }

    void onNoAnyCompanyInfoAttached(){
        mNoInfoHintView.setVisibility(View.VISIBLE);
    }
}
