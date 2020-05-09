package com.juice.timetable.utils;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class UtilsTest {
    public Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Before
    public void initPreferencesUtils() {
        PreferencesUtils.init(getContext());
    }


    @Test
    public void isCheckInTime() {
        LogUtils.getInstance().d("isCheckInTime:" + Utils.isCheckInTime());
    }

    @Test
    public void setFirstWeekPref() {

        Utils.setFirstWeekPref(12);
    }

    @Test
    public void getCurrentWeek() {
        // 写入第一天
        int currentWeek = Utils.getCurrentWeek();
        LogUtils.getInstance().d("currentWeek:" + currentWeek);
    }

}