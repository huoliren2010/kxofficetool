package com.kx.officetool.service.response;

import com.kx.officetool.infos.Message;

import java.util.List;

public class MessageResponse extends BaseResponse {
    List<Message> data;

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}
