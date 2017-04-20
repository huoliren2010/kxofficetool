package com.kx.officetool.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;

public class HttpUtil {
    private static final String TAG = "HttpUtil";
    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack {
        void onRequestComplete(String result);
    }


    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 异步的Post请求
     *
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr, final String params,
                                  final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String HTTPRequest(Context context, String urlStr, String content, Map<String, String> headers) {
        HttpURLConnection con = null;
        InputStream is = null;
        InputStreamReader reader = null;
        OutputStream os = null;
        OutputStreamWriter writer = null;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setInstanceFollowRedirects(true);
            con.setConnectTimeout(15000);
            if (content != null) {
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                if (headers != null) {
                    Iterator<Map.Entry<String, String>> i = headers.entrySet().iterator();
                    if (i.hasNext()) {
                        Map.Entry<String, String> entry = i.next();
                        con.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
            }
            long start = System.currentTimeMillis();
            con.connect();
            String country = Locale.getDefault().getCountry();
//			if(!TextUtils.isEmpty(country) &&
//					(country.equalsIgnoreCase("br") ||
//					country.equalsIgnoreCase("tr") ||
//					country.equalsIgnoreCase("mx") ||
//					country.equalsIgnoreCase("in") ||
//					country.equalsIgnoreCase("us"))){
//			}


            if (content != null) {
                os = con.getOutputStream();
                writer = new OutputStreamWriter(os);
                writer.write(content);
                writer.flush();
                writer.close();
                writer = null;
            }
            int httpCode = con.getResponseCode();
            LogUtil.v(TAG, "ResponseCode = %d", httpCode);
            if (httpCode / 100 != 2) {
                return null;
            }
            is = con.getInputStream();
            reader = new InputStreamReader(is);
            char[] buffer = new char[512];
            int len = 0;
            StringBuilder sb = new StringBuilder();
            while ((len = reader.read(buffer)) > 0) {
                sb.append(buffer, 0, len);
            }
            return sb.toString();
        } catch (Exception e) {
            LogUtil.e(TAG, "HTTPRequest error: %s, URL: %s", e.getMessage(), urlStr);
        } finally {
            HttpUtil.closeSilently(reader);
            HttpUtil.closeSilently(is);
            HttpUtil.closeSilently(writer);
            HttpUtil.closeSilently(os);
            if (con != null) con.disconnect();
        }
        return null;
    }

    public static void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // do nothing
        }
    }

    public static Bitmap decodeBitmapHttp(String urlStr) {
        Log.d(TAG, "decodeBitmapHttp :" + urlStr);
        HttpURLConnection con = null;
        InputStream is = null;
        Bitmap res = null;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(15000);
            con.setInstanceFollowRedirects(true);
            con.connect();
            int httpCode = con.getResponseCode();
            LogUtil.v(TAG, "ResponseCode = %d", httpCode);
            if (httpCode / 100 != 2) {
                return null;
            }
            is = con.getInputStream();
            int contentLength = con.getContentLength();
            int length = 0;
            byte[] b = new byte[2048];
            int j = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((j = is.read(b)) != -1) {
                baos.write(b, 0, j);
                length += j;
            }
            if (length == contentLength) {
                byte[] byteArray = baos.toByteArray();
                res = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                //res = BitmapFactory.decodeStream(is);
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            HttpUtil.closeSilently(is);
            if (con != null)
                con.disconnect();
        }
        return res;
    }

    public static Bitmap getThumbnail(String urlStr) {
        Log.d(TAG, "decodeBitmapHttp :" + urlStr);
        HttpURLConnection con = null;
        InputStream is = null;
        Bitmap res = null;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(15000);
            con.setInstanceFollowRedirects(true);
            con.connect();
            int httpCode = con.getResponseCode();
            LogUtil.v(TAG, "ResponseCode = %d", httpCode);
            if (httpCode / 100 != 2) {
                return null;
            }
            is = con.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 5; // width，hight设为原来的十分一
            res = BitmapFactory.decodeStream(is,
                    null, options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            HttpUtil.closeSilently(is);
            if (con != null)
                con.disconnect();
        }
        return res;
    }
}
