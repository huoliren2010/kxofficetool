package com.kx.officetool.service.response;


import com.kx.officetool.infos.DepartMent;

public class DepartMentResponse extends BaseResponse {
    DepartMent data;

    public DepartMent getData() {
        return data;
    }

    public void setData(DepartMent data) {
        this.data = data;
    }
}
