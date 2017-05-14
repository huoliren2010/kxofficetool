package com.kx.officetool.infos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DailySign implements Serializable{
    private static final long serialVersionUID = -5785945314939394615L;
    public static final String KEY_SIGNINFO_OBJ = "key_signinfo_object";
    int id;
    int uid;
    String address;
    String time;
    int departmentid;

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("uid", uid);
            json.put("departmentid", departmentid);
            if (address != null)
                json.put("address", address);
            if (time != null)
                json.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    public DailySign(int id2, int uid2, String address2, String time2, int departid) {
        this.id = id2;
        this.uid = uid2;
        this.address = address2;
        this.time = time2;
        this.departmentid = departid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("uid", uid);
            json.put("departmentid", departmentid);
            if (address != null)
                json.put("address", address);
            if (time != null)
                json.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}
