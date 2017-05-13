package com.kx.officetool.infos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DepartMent implements Serializable{
    private static final long serialVersionUID = -6076822358611459148L;
    int id;
    String partname;
    int leaderid;// as uid througth table user id
    int companyId;

    public DepartMent(int did, String dname, int cid, int duid) {
        this.id = did;
        this.partname = dname;
        this.leaderid = duid;
        this.companyId = cid;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            if (partname != null)
                json.put("partname", partname);
            json.put("leaderid", leaderid);
            json.put("companyId", companyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getLeaderid() {
        return leaderid;
    }

    public void setLeaderid(int leaderid) {
        this.leaderid = leaderid;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            if (partname != null)
                json.put("partname", partname);
            json.put("leaderid", leaderid);
            json.put("companyId", companyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}
