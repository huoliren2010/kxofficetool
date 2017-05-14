package com.kx.officetool.infos;

import com.kx.officetool.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MeetingRoom implements Serializable {
    private static final long serialVersionUID = 5077246312492265345L;

    public int getRoomStatus() {
        if (status == Status.ORDERING)
            return R.string.string_meetingroom_status_ordering;
        if (status == Status.USED)
            return R.string.string_meetingroom_status_used;
        return R.string.string_meetingroom_status_idle;
    }

    public MeetingRoom setTime(long fromTime, long endTime) {
        this.fromTime = fromTime;
        this.endTime = endTime;
        return this;
    }

    public enum Status {IDLE, ORDERING, USED}

    String roomname;
    Status status = Status.IDLE;
    long fromTime;
    long endTime;


    public String getEndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-HH时-mm分");
        return simpleDateFormat.format(new Date(endTime));
    }

    public MeetingRoom setEndTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getFromTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-HH时-mm分");
        return simpleDateFormat.format(new Date(fromTime));
    }

    public MeetingRoom setFromTime(long fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public String getRoomname() {
        return roomname;
    }

    public MeetingRoom setRoomname(String roomname) {
        this.roomname = roomname;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public MeetingRoom setStatus(Status status) {
        this.status = status;
        return this;
    }
}
