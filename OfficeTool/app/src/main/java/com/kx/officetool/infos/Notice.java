package com.kx.officetool.infos;

import java.io.Serializable;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public class Notice implements Serializable{
    private static final long serialVersionUID = -4896209715333064966L;
    public static String TYPE_UPDATE = "update";
    public static String TYPE_DELETE = "delete";
    int id;
    int uid;
    int departid;
    String title;
    String message;
    String time;

    @Override
    public boolean equals(Object obj) {
        return id == ((Notice)obj).id;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("uid", uid);
            if (title != null)
                json.put("title", title);
            if (message != null)
                json.put("message", message);
            json.put("departid", departid);
            json.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public Notice(int id2, int uid, int departid, String title, String message2, String time) {
        this.id = id2;
        this.uid = uid;
        this.departid = departid;
        this.title = title;
        this.message = message2;
        this.time = time;
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

    public int getDepartid() {
        return departid;
    }

    public void setDepartid(int departid) {
        this.departid = departid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("uid", uid);
            if (title != null)
                json.put("title", title);
            if (message != null)
                json.put("message", message);
            json.put("departid", departid);
            json.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}
