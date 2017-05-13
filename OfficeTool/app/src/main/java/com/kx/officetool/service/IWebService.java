package com.kx.officetool.service;

import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.response.ApprovalResponse;
import com.kx.officetool.service.response.BoooleanResponse;
import com.kx.officetool.service.response.CompanyResponse;
import com.kx.officetool.service.response.DailysignResponse;
import com.kx.officetool.service.response.DepartMentResponse;
import com.kx.officetool.service.response.LogResponse;
import com.kx.officetool.service.response.MessageResponse;
import com.kx.officetool.service.response.NoticeResponse;
import com.kx.officetool.service.response.RegResponse;
import com.kx.officetool.service.response.UserInfoResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IWebService {
    @GET("HelloWeb/RegLet")
    Call<RegResponse> regist(@Query("username") String username, @Query("password") String password, @Query("phonenumber") String phonenumber);

    @GET("HelloWeb/LogLet")
    Call<LogResponse> login(@Query("username") String username, @Query("password") String password);

    @GET("HelloWeb/CreateCompany")
    Call<CompanyResponse> createCompany(@Query("companyname") String companyname, @Query("uid") int uid);

    @GET("HelloWeb/UpdateUserInfo")
    Call<RegResponse> updateUserInfo(@QueryMap Map<String, String> params);

    @GET("HelloWeb/CreateManager")
    Call<UserInfoResponse> createManager(@Query("uid") int uid, @Query("companyid") int companyid);

    @GET("HelloWeb/DeleteManager")
    Call<BoooleanResponse> deleteManager(@QueryMap Map<String, String> params);

    @GET("HelloWeb/CreateDepartMent")
    Call<DepartMentResponse> createDepartMent(@Query("departName") String departName, @Query("companyid")int companyid, @Query("leaderid") int leaderid);

    @GET("HelloWeb/UpdateDepartMent")
    Call<BoooleanResponse> updateDepartMent(@QueryMap Map<String, String> params);

    @GET("HelloWeb/DeleteDepartMent")
    Call<BoooleanResponse> deleteDepartMent(@QueryMap Map<String, String> params);

    @GET("HelloWeb/CreateCompanyRoom")
    Call<BoooleanResponse> createCompanyRoom(@QueryMap Map<String, String> params);

    @GET("HelloWeb/UpdateCompanyRoom")
    Call<BoooleanResponse> updateCompanyRoom(@QueryMap Map<String, String> params);

    @GET("HelloWeb/DeleteCompanyRoom")
    Call<BoooleanResponse> deleteCompanyRoom(@QueryMap Map<String, String> params);

    @GET("HelloWeb/CreateNotice")
    Call<BoooleanResponse> createNotice(@QueryMap Map<String, String> params);

    @GET("HelloWeb/UpdateNotice")
    Call<BoooleanResponse> updateNotice(@QueryMap Map<String, String> params);

    @GET("HelloWeb/DeleteNotice")
    Call<BoooleanResponse> deleteNotice(@QueryMap Map<String, String> params);

    @GET("HelloWeb/QueryNotice")
    Call<NoticeResponse> queryNotice(@QueryMap Map<String, String> params);

    @GET("HelloWeb/CreateMessage")
    Call<BoooleanResponse> createMessage(@QueryMap Map<String, String> params);

    @GET("HelloWeb/UpdateMessage")
    Call<BoooleanResponse> updateMessage(@QueryMap Map<String, String> params);

    @GET("HelloWeb/QueryMessage")
    Call<MessageResponse> queryMessage(@QueryMap Map<String, String> params);

    @GET("HelloWeb/CreateApproval")
    Call<BoooleanResponse> createApproval(@QueryMap Map<String, String> params);

    @GET("HelloWeb/DeleteApproval")
    Call<BoooleanResponse> deleteApproval(@QueryMap Map<String, String> params);

    @GET("HelloWeb/UpdateApproval")
    Call<BoooleanResponse> updateApproval(@QueryMap Map<String, String> params);

    @GET("HelloWeb/QueryApproval")
    Call<ApprovalResponse> queryApproval(@QueryMap Map<String, String> params);

    @GET("HelloWeb/CreateDailysign")
    Call<BoooleanResponse> createDailysign(@QueryMap Map<String, String> params);

    @GET("HelloWeb/DeleteDailysign")
    Call<BoooleanResponse> deleteDailysign(@QueryMap Map<String, String> params);

    @GET("HelloWeb/UpdateDailysign")
    Call<BoooleanResponse> updateDailysign(@QueryMap Map<String, String> params);

    @GET("HelloWeb/QueryDailysign")
    Call<DailysignResponse> queryDailysign(@QueryMap Map<String, String> params);

}
