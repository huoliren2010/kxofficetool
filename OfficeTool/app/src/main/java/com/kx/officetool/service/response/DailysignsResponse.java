package com.kx.officetool.service.response;

import com.kx.officetool.infos.DailySign;

import java.util.List;

public class DailysignsResponse extends BaseResponse {
    List<DailySign> data;

    public List<DailySign> getData() {
        return data;
    }

    public void setData(List<DailySign> data) {
        this.data = data;
    }
}
