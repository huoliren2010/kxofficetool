package com.kx.officetool.infos;

import java.io.Serializable;

public class DailySignInfo implements Serializable{
    private static final long serialVersionUID = -2995257080942100024L;
    public static final String KEY_SIGNINFO_OBJ = "key_signinfo_object";
    String time;
    String address;
    String marks;

    public DailySignInfo(String marks, String mCurrentTime, String mCurrentLocation) {
        this.marks = marks; this.time = mCurrentTime; this.address = mCurrentLocation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
