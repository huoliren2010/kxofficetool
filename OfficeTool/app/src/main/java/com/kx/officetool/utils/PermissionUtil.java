package com.kx.officetool.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtil {
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10;
    public static final int CAMERA_REQ_CODE = 1;


    public static boolean requestExternalStoragePermission(Activity activity) {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                Toast.makeText(activity,activity.getString(R.string.request_for_external_storage_permission_denied)
//                        , Toast.LENGTH_SHORT).show();
            }
            //进行权限请求
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQ_CODE);


            return false;
        } else {
            return true;
        }
    }

    public static boolean requestCameraPermission(Activity activity) {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CAMERA)) {
//                Toast.makeText(activity,activity.getString(R.string.request_for_camera_permission_denied)
//                        , Toast.LENGTH_SHORT).show();
            }
            //进行权限请求
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQ_CODE);

            return false;
        } else {
            return true;
        }
    }
}
