package com.kx.officetool.service;

import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.response.RegResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWebService {
    @GET("HelloWeb/RegLet")
    Call<RegResponse> regist(@Query("username") String username, @Query("password") String password, @Query("phonenumber") String phonenumber);
}
