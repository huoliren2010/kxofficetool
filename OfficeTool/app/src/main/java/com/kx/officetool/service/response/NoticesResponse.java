package com.kx.officetool.service.response;

import com.kx.officetool.infos.Notice;

import java.util.List;


public class NoticesResponse extends BaseResponse {
    List<Notice> data;

    public List<Notice> getData() {
        return data;
    }

    public void setData(List<Notice> data) {
        this.data = data;
    }
}
