package com.kx.officetool.service;

import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.response.RegResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final String ACTION_LOGIN = "LogLet";
    private static final String ACTION_REGIST = "RegLet";
    private IWebService mIWebService = null;
    private static WebService mInstance = null;
    private WebService(){
        mIWebService = mRetrofitInstance.create(IWebService.class);
    }
    public static WebService getInstance(){
        if(mInstance == null){
            mInstance = new WebService();
        }
        return mInstance;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public static String login(String username, String password) {
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            // 用户名 密码
            // URL 地址
            String path = "http://" + HOST + "/HelloWeb/";
            path = path + ACTION_LOGIN + "?username=" + username + "&password=" + password;

            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000); // 设置超时时间
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET"); // 设置获取信息方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * 注册
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

    // 将输入流转化为 String 型
    private static String parseInfo(InputStream inStream) throws Exception {
        byte[] data = read(inStream);
        // 转化为字符串
        return new String(data, "UTF-8");
    }

    // 将输入流转化为byte型
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }
}
