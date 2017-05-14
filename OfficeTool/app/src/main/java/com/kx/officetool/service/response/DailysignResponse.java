package com.kx.officetool.service.response;

import com.kx.officetool.infos.DailySign;

public class DailysignResponse extends BaseResponse {
    DailySign data;

    public DailySign getData() {
        return data;
    }

    public void setData(DailySign data) {
        this.data = data;
    }
}
