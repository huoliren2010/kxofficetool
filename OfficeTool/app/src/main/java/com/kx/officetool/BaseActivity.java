package com.kx.officetool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public AppConfig mAppConfig = AppConfig.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ProgressDialog mProgressDialog = null;
    public void showProgressDlg(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.show();
    }
    public void hideProgressDlg(){
        if(mProgressDialog != null)mProgressDialog.dismiss();
        mProgressDialog = null;
    }
}
