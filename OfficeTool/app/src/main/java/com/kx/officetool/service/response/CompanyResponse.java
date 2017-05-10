package com.kx.officetool.service.response;

import com.kx.officetool.infos.CompanyInfo;
public class CompanyResponse extends BaseResponse{
    CompanyInfo data;

    public CompanyInfo getData() {
        return data;
    }

    public void setData(CompanyInfo data) {
        this.data = data;
    }
}
