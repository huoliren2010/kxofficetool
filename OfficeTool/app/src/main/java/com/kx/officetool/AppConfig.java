package com.kx.officetool;

import android.content.Context;

import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.UserInfo;

public class AppConfig {
    private static AppConfig mInstance = null;
    private Context mContext = null;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        if (mInstance == null) {
            mInstance = new AppConfig();
        }
        return mInstance;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public UserInfo mUserInfo = null;
    public CompanyInfo mCompanyInfo = null;
}
