package com.kx.officetool.service.response;

import com.kx.officetool.infos.CompanyInfo;

import java.util.List;

public class CompanysResponse extends BaseResponse{
    List<CompanyInfo> data;

    public List<CompanyInfo> getData() {
        return data;
    }

    public void setData(List<CompanyInfo> data) {
        this.data = data;
    }
}
