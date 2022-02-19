package com.juice.timetable.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import androidx.preference.PreferenceManager;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/2/19 0:06
 * </pre>
 */
public class NightModeUtil {
    /**
     * 获取系统是否跟随系统，默认false
     */
    public static boolean getSystemMode(Activity activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.getBoolean("dark", false);
    }

    /**
     * 设置App跟随系统深色模式
     */
    public static void AppFromSysNightMode(Activity activity) {
        boolean nightMode = getSystemMode(activity);
        if (!nightMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity.getWindow().getDecorView().setForceDarkAllowed(false);
        }
    }

    /**
     * 获取当前系统是否是深色模式
     */
    public static boolean isNightMode(Context context) {
        int uiMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return uiMode == Configuration.UI_MODE_NIGHT_YES;
    }
}
