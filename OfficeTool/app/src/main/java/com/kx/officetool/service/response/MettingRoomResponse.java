package com.kx.officetool.service.response;

import com.kx.officetool.infos.MeetingRoom;

public class MettingRoomResponse extends BaseResponse {
    MeetingRoom data;

    public MeetingRoom getData() {
        return data;
    }

    public void setData(MeetingRoom data) {
        this.data = data;
    }
}
