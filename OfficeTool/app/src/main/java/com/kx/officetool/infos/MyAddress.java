package com.kx.officetool.infos;

import java.util.List;

public class MyAddress {
    String address;
    List<String> listNearBy;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getListNearBy() {
        return listNearBy;
    }

    public void setListNearBy(List<String> listNearBy) {
        this.listNearBy = listNearBy;
    }
}
