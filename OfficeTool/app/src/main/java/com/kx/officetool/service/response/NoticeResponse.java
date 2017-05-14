package com.kx.officetool.service.response;

import com.kx.officetool.infos.Notice;


public class NoticeResponse extends BaseResponse {
    Notice data;

    public Notice getData() {
        return data;
    }

    public void setData(Notice data) {
        this.data = data;
    }
}
