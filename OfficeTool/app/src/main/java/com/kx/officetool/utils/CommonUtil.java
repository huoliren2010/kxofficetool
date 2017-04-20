package com.kx.officetool.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.view.View;

import java.util.regex.Pattern;

public class CommonUtil {

    public static void setBackgroundWithShapDrwable(View view, int color_arrays_luck_colors, int luckyColor, float radius) {
        TypedArray typedArray = view.getResources().obtainTypedArray(color_arrays_luck_colors);
        Shape shape = new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(typedArray.getColor(luckyColor, 0));
        typedArray.recycle();
        view.setBackgroundDrawable(shapeDrawable);
    }

    public static boolean isAppInstalled(Context context, String packagename) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(packagename, 0);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static PackageInfo getPackgeInfo(Context context, String packagename) {
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return -1;
    }

    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param phoneNumber
     * @return
     */
    public static boolean validPhoneNumber(String phoneNumber) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,2,3,5-9]))\\d{8}$";
        Pattern compile = Pattern.compile(regex);
        return compile.matcher(phoneNumber).matches();
    }
}
