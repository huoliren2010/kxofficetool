package com.kx.officetool.infos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompanyInfo implements Serializable{
    private static final long serialVersionUID = 815608332340720495L;
    public static final String KEY_COMPANYINFO_OBJ = "key_company_object";
    public static final String KEY_COMPANYINFO_AVATAR = "key_company_avatar";
    /**
     * 公司id
     */

    int id;
    /**
     * 公司名字
     */
    String companyName;
    /**
     * 公司创始人id
     */
    int ownerid;

    List<DepartMent> department;

    /**
     * 公司头像地址
     */
     String companAvatarUrl;

    /**
     * 公司成员
     */
    List<UserInfo> companyMembers;
    /**
     *公司管理员
     */
    List<UserInfo> companyManagers;

    /**
     * 公司会议室
     */
    List<MeetingRoom> companyMeetingRooms;

    public List<MeetingRoom> getCompanyMeetingRooms() {
        return companyMeetingRooms;
    }

    public void setCompanyMeetingRooms(List<MeetingRoom> companyMeetingRooms) {
        this.companyMeetingRooms = companyMeetingRooms;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getCompanyId() {
        return id;
    }

    public void setCompanyId(int companyId) {
        this.id = companyId;
    }

    public String getCompanAvatarUrl() {
        return companAvatarUrl;
    }

    public void setCompanAvatarUrl(String avatarUrl) {
        this.companAvatarUrl = avatarUrl;
    }

    public List<UserInfo> getCompanyMembers() {
        return companyMembers;
    }

    public void setCompanyMembers(List<UserInfo> companyMembers) {
        this.companyMembers = companyMembers;
    }

    public List<UserInfo> getManager(){
       return companyManagers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(int ownerid) {
        this.ownerid = ownerid;
    }

    public List<DepartMent> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartMent> department) {
        this.department = department;
    }

    public void setManager(List<UserInfo> manager) {
        this.companyManagers = manager;
    }
}
