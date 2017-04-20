package com.kx.officetool.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;

public class LogUtil {
    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static HashMap<String, Long> mMap = null;
    static {
        isDebug = true;
        if (isDebug) {
            mMap = new HashMap<String, Long>();
        }
    }
    public static void initDebugMode(@NonNull Context context) {

    }

    private static final String TAG = "LogUtil";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Object... args) {
        if (!isDebug) {
            return;
        }
        if (args != null && args.length > 0) {
            msg = String.format(msg, args);
        }
        Log.v(tag, msg);
    }

    public static void e(String tag, String msg, Object... args) {
        if (!isDebug) {
            return;
        }
        if (args != null) {
            msg = String.format(msg, args);
        }
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Exception e) {
        if (e != null) {
            msg = msg + e.getMessage();
        }
        e(tag, msg);
    }

    public static void e(String tag, Exception e) {
        String msg = e.getMessage();
        e(tag, msg);
    }

    public static void startLogTime(String tag) {
        if (!isDebug) {
            return;
        }
        if (mMap.containsKey(tag)) {
            // throw new
            // RuntimeException("startLogTime error. Tag "+tag+" already start.");
            return;
        }
        long time = System.currentTimeMillis();
        mMap.put(tag, time);
    }

    public static void stopLogTime(String tag) {
        if (!isDebug) {
            return;
        }
        if (!mMap.containsKey(tag)) {
            // throw new
            // RuntimeException("stopLogTime error. Tag "+tag+" not started.");
            return;
        }
        long time = System.currentTimeMillis();
        Long startTime = mMap.get(tag);
        mMap.remove(tag);
        if(startTime==null) {
            return;
        }
        Log.v("Performance", tag + " cost " + (time - startTime) + "ms");
    }

}
