package com.kx.officetool.infos;

import com.kx.officetool.R;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = -750243008729743460L;
    public static String KEY_USERINFO_OBJ = "key_userinfo_object";
    public static String KEY_USERINFO_AVATAR = "key_userinfo_avatar";
    public static String STR_BOY = "m";
    public static String STR_GIRL = "f";

    public enum Level {NORMAL, MIDDLE, ADVANCED, BOSS}

    public enum GENDER {GIRL, BOY}

    String password;
    String gender;
    int departmentid = -1;
    String phonenumber;
    String signmessage;
    int id;
    String avatar;
    String username;

    public UserInfo setCompanyId(int companyId) {
        return this;
    }

    public boolean isJoinedInCompany() {
        return false;
    }

    public String getUserAvatar() {
        return avatar;
    }

    public UserInfo setUserAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public Level getLevel() {
        return Level.NORMAL;
    }

    public UserInfo setLevel(Level level) {
        return this;
    }

    public boolean isManager() {
        return false;
    }

    public String getUserName() {
        return username;
    }

    public UserInfo setUserName(String userNickName) {
        this.username = userNickName;
        return this;
    }

    public String getOrgGender(){
        return gender;
    }

    public int getGender() {
        return gender.equalsIgnoreCase(STR_BOY) ? R.string.string_sex_boy : R.string.string_sex_girl;
    }

    public GENDER getUserGender() {
        return gender.equalsIgnoreCase(STR_BOY) ? GENDER.BOY : GENDER.GIRL;
    }

    public UserInfo setUserGender(GENDER userGender) {
        if(userGender == GENDER.BOY){
            gender = STR_BOY;
        }else gender = STR_GIRL;
        return this;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }

    public UserInfo setPhoneNumber(String userPhoneNumber) {
        this.phonenumber = userPhoneNumber;
        return this;
    }

    public String getSignmessage() {
        return signmessage;
    }

    public void setSignmessage(String signmessage) {
        this.signmessage = signmessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((UserInfo)obj).getId();
    }
}
