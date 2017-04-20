package com.kx.officetool.infos;


import android.support.annotation.StringRes;

import com.kx.officetool.R;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = -750243008729743460L;
    public static String KEY_USERINFO_OBJ = "key_userinfo_object";
    public static String KEY_USERINFO_AVATAR = "key_userinfo_avatar";

    public enum Level {NORMAL, MIDDLE, ADVANCED, BOSS}

    public enum GENDER {GIRL, BOY}

    /**
     * 用户所在公司id
     */
    int companyId = -1;
    /**
     * 用户昵称
     */
    private String userNickName;
    /**
     * 用户性别 默认boy
     */
    private GENDER userGender = GENDER.BOY;
    /**
     * 用户手机号
     */
    private String userPhoneNumber;
    /**
     * 用户头像
     */
    String userAvatar;

    /**
     * 所在公司级别
     */
    Level level = Level.NORMAL;

    public int getCompanyId() {
        return companyId;
    }

    public UserInfo setCompanyId(int companyId) {
        this.companyId = companyId;
        return this;
    }

    public boolean isJoinedInCompany() {
        return companyId != -1;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public UserInfo setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    public UserInfo setLevel(Level level) {
        this.level = level;
        return this;
    }

    public boolean isManager() {
        return level == Level.ADVANCED;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public UserInfo setUserNickName(String userNickName) {
        this.userNickName = userNickName;
        return this;
    }

    public int getGender() {
        return userGender == GENDER.BOY ? R.string.string_sex_boy : R.string.string_sex_girl;
    }

    public GENDER getUserGender() {
        return userGender;
    }

    public UserInfo setUserGender(GENDER userGender) {
        this.userGender = userGender;
        return this;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public UserInfo setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        return this;
    }
}
