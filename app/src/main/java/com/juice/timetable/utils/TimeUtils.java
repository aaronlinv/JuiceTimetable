package com.juice.timetable.utils;

import android.icu.util.Calendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.juice.timetable.app.Constant;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/8/16 18:47
 * </pre>
 */
public class TimeUtils {
    //获取当前时间+7天后的时间戳并保存在本地
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void saveSevenTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);
        long unixTime = calendar.getTimeInMillis();
        PreferencesUtils.putLong(Constant.PREF_TIME, unixTime);
    }
}
