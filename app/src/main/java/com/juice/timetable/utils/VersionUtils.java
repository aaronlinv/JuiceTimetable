package com.juice.timetable.utils;

import android.content.Context;
import android.content.pm.PackageManager;

public class VersionUtils {

    public static String getVersionCode(Context context) {
        String versionCode = null;
        try {
            //获取软件版本号，对应 build.gradle 下的 versionName
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
