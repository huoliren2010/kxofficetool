package com.kx.officetool.service.response;

import java.util.List;

public class NoticeResponse extends BaseResponse {
    List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
