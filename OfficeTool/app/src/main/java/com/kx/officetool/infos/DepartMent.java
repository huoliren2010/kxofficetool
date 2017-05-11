package com.kx.officetool.infos;

import java.io.Serializable;
import java.util.List;

public class DepartMent implements Serializable{
    private static final long serialVersionUID = -7972330034026683789L;
    int id;
    String partname;
    int leaderid;// as uid througth table user id
    int companyId;

    List<UserInfo> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }

    public int getLeaderid() {
        return leaderid;
    }

    public void setLeaderid(int leaderid) {
        this.leaderid = leaderid;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
