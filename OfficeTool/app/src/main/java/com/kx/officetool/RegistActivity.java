package com.kx.officetool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.mapapi.map.Text;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.CommonUtil;
import com.kx.officetool.utils.SharedPreferencesUtil;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnRegist = null;
    private EditText mEditTextNickName = null, mEditTextPsw = null, mEditTextPhoneNumber = null;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mEditTextNickName = (EditText) findViewById(R.id.nickname);
        mEditTextPsw = (EditText) findViewById(R.id.password);
        mEditTextPhoneNumber = (EditText) findViewById(R.id.phone);
        mBtnRegist = (Button) findViewById(R.id.btn_regist);
        mBtnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempRegist();
            }
        });
    }

    private void attempRegist() {
        if (TextUtils.isEmpty(mEditTextNickName.getText().toString())) {
            mEditTextNickName.setError(getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(mEditTextPsw.getText().toString())) {
            mEditTextPsw.setError(getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(mEditTextPhoneNumber.getText().toString())) {
            mEditTextPhoneNumber.setError(getString(R.string.error_field_required));
            return;
        }
        if (!CommonUtil.validPhoneNumber(mEditTextPhoneNumber.getText().toString())) {
            mEditTextPhoneNumber.setError(getString(R.string.error_invalid_phonenumber));
            return;
        }
        final String nickname = mEditTextNickName.getText().toString();
        final String password = mEditTextPsw.getText().toString();
        final String phonenumber = mEditTextPhoneNumber.getText().toString();
        showProgress(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfo userInfo = WebService.getInstance().regist(nickname, password, phonenumber);
                if (userInfo != null) {
                    SharedPreferencesUtil.putObject(RegistActivity.this, UserInfo.KEY_USERINFO_OBJ, userInfo);
                    startActivity(new Intent(RegistActivity.this, MainActivity.class));
                    finish();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgress(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegistActivity.this, LoginActivity.class));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
