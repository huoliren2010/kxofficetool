package com.kx.officetool.service.response;

import com.kx.officetool.infos.UserInfo;

public class RegResponse extends BaseResponse{
    UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}
