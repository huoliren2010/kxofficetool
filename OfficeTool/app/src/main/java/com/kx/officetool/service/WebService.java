package com.kx.officetool.service;

import android.text.TextUtils;
import android.util.Log;

import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.DepartMent;
import com.kx.officetool.infos.Manager;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.response.BoooleanResponse;
import com.kx.officetool.service.response.CompanyResponse;
import com.kx.officetool.service.response.DepartMentResponse;
import com.kx.officetool.service.response.LogResponse;
import com.kx.officetool.service.response.ManagerResponse;
import com.kx.officetool.service.response.RegResponse;
import com.kx.officetool.service.response.UserInfoResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {
    //    private static String HOST = "192.168.60.177:8080";
    private static String HOST = "http://192.168.0.105:8080/";
    //把TOMCATURL改为你的服务地址
    private Retrofit mRetrofitInstance = new Retrofit.Builder().baseUrl(HOST).addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build()).build();
    private IWebService mIWebService = null;
    private static WebService mInstance = null;

    private WebService() {
        mIWebService = mRetrofitInstance.create(IWebService.class);
    }

    public static WebService getInstance() {
        if (mInstance == null) {
            mInstance = new WebService();
        }
        return mInstance;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    public UserInfo login(String username, String password) {
        Call<LogResponse> login = mIWebService.login(username, password);
        try {
            Response<LogResponse> execute = login.execute();
            UserInfo data = execute.body().getData();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param phonenumber
     * @return
     */
    public UserInfo regist(String username, String password, String phonenumber) {
        Call<RegResponse> regist = mIWebService.regist(username, password, phonenumber);
        try {
            Response<RegResponse> execute = regist.execute();
            UserInfo data = execute.body().getData();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserInfo(UserInfo userInfo) {
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("id", String.valueOf(userInfo.getId()));
        mapParams.put("gender", userInfo.getOrgGender());
        if (!TextUtils.isEmpty(userInfo.getAvatar()))
            mapParams.put("avatar", userInfo.getAvatar());
        if (!TextUtils.isEmpty(userInfo.getSignmessage()))
            mapParams.put("signmessage", userInfo.getSignmessage());
        mapParams.put("departmentid", String.valueOf(userInfo.getDepartmentid()));
        if (!TextUtils.isEmpty(userInfo.getUsername()))
            mapParams.put("uname", userInfo.getUsername());
        Call<RegResponse> boooleanResponseCall = mIWebService.updateUserInfo(mapParams);
        try {
            Response<RegResponse> execute = boooleanResponseCall.execute();
            boolean data = execute.body().getData() != null;
            Log.e("xx", "updateUserInfo data = " + data);
        } catch (IOException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CompanyInfo createCompany(String companyName, int uid) {
        Call<CompanyResponse> create = mIWebService.createCompany(companyName, uid);
        try {
            Response<CompanyResponse> execute = create.execute();
            CompanyInfo data = execute.body().getData();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DepartMent createDepartMent(String departName, int compayid, int uid){
        Call<DepartMentResponse> departMent = mIWebService.createDepartMent(departName, compayid, uid);
        try {
            Response<DepartMentResponse> execute = departMent.execute();
            return execute.body().getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserInfo createManager(int uid, int companayid){
        Call<UserInfoResponse> manager = mIWebService.createManager(uid, companayid);
        try {
            Response<UserInfoResponse> execute = manager.execute();
            return execute.body().getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
