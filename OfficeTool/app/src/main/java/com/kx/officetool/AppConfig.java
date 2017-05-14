package com.kx.officetool;

import android.content.Context;

import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.User;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

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

    public void ensureInfos() {
        if (mUserInfo == null)
            mUserInfo = SharedPreferencesUtil.getObject(mContext, UserInfo.KEY_USERINFO_OBJ, UserInfo.class);
        if (mCompanyInfo == null)
            mCompanyInfo = SharedPreferencesUtil.getObject(mContext, CompanyInfo.KEY_COMPANYINFO_OBJ, CompanyInfo.class);
    }

    public boolean isManager() {
        ensureInfos();
        List<UserInfo> manager = mCompanyInfo.getManager();
        if (manager != null && manager.size() > 0)
            return (manager.contains(mUserInfo) || mUserInfo.getId() == mCompanyInfo.getOwnerid());
        return false;
    }

    public void refreshCompanyInfo(){
        CompanyInfo companyInfo = WebService.getInstance().QueryCompanyByDepartId(mUserInfo.getDepartmentid());
        if((companyInfo != null)) {
            SharedPreferencesUtil.putObject(mContext, CompanyInfo.KEY_COMPANYINFO_OBJ, companyInfo);
            mCompanyInfo = companyInfo;
        }
    }

    public UserInfo getUserInfo(){
        if(mUserInfo == null)mUserInfo = SharedPreferencesUtil.getObject(mContext, UserInfo.KEY_USERINFO_OBJ, UserInfo.class);
        return mUserInfo;
    }

    public void saveUserInfo(UserInfo userInfo){
        if(userInfo ==null)return;
        mUserInfo = userInfo;
        SharedPreferencesUtil.putObject(mContext, UserInfo.KEY_USERINFO_OBJ, userInfo);
    }

    public List<UserInfo> getNormalUserList() {
        List<UserInfo> companyMembers = mCompanyInfo.getCompanyMembers();
        List<UserInfo> manager = mCompanyInfo.getManager();
        List<UserInfo> normals = new ArrayList<>();
        for(UserInfo userInfo : companyMembers){
            if(manager.contains(userInfo))continue;
            normals.add(userInfo);
        }
        return normals;
    }
}
