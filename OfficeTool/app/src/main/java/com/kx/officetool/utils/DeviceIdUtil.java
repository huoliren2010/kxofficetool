package com.kx.officetool.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class DeviceIdUtil {

    public static String getDeviceId(Context context){
        StringBuilder stringBuilder=new StringBuilder(36);
        stringBuilder.append(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID))
                .append(Build.SERIAL);
        if(stringBuilder.length()>36){
            stringBuilder.delete(36,stringBuilder.length());
        }
        char[] chars=stringBuilder.toString().toCharArray();
        for(int i=0;i<chars.length;i++){
            if(chars[i]>=128 || chars[i]<=0){
                chars[i]=64;
            }
        }

        String result=new String(chars);
        return result;
    }
}
