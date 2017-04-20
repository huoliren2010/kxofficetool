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
import com.kx.officetool.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

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

    private void ensureResult(boolean issuccess) {
        if (issuccess) {
            SharedPreferencesUtil.putObject(RegistActivity.this, UserInfo.KEY_USERINFO_OBJ, new UserInfo().setUserPhoneNumber(mEditTextPhoneNumber.getText().toString()).setUserNickName(mEditTextNickName.getText().toString()));
            startActivity(new Intent(RegistActivity.this, MainActivity.class));
            finish();
        }
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
        String nickname = mEditTextNickName.getText().toString();
        String password = mEditTextPsw.getText().toString();
        String phonenumber = mEditTextPhoneNumber.getText().toString();
        showProgress(true);
        mRegistTask = new UserRegistTask(nickname, password, phonenumber);
        mRegistTask.execute((Void) null);
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

    private UserRegistTask mRegistTask = null;
    public class UserRegistTask extends AsyncTask<Void, Void, String> {

        private final String mNickname;
        private final String mPassword;
        private final String mPhoneNumber;

        UserRegistTask(String nickname, String password, String phonenumber) {
            mNickname = nickname;
            mPassword = password;
            mPhoneNumber = phonenumber;
        }

        @Override
        protected String doInBackground(Void... params) {
            return WebService.regist(mNickname, mPassword, mPhoneNumber);
        }

        @Override
        protected void onPostExecute(final String response) {
            mRegistTask = null;
            showProgress(false);
            try {
                if (TextUtils.isEmpty(response)) {
                    ToastUtil.showShort(RegistActivity.this, R.string.string_regist_failed);
                } else {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = (jsonObject.getInt("status") == 200);
                    ToastUtil.showShort(RegistActivity.this, success ? getResources().getString(R.string.string_regist_success) : jsonObject.getString("message")/*R.string.string_regist_failed*/);
                    ensureResult(success);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            mRegistTask = null;
            showProgress(false);
        }
    }
}
