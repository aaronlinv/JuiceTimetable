package com.juice.timetable.utils;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        // 2020.2.17 为第一周周一
        Utils.setFirstWeekPref(15);
    }

    @Test
    public void getCurrentWeek() {
        // 写入第一天
        int currentWeek = Utils.getCurrentWeek();
        LogUtils.getInstance().d("currentWeek:" + currentWeek);
    }

    @Test
    public void testGetFirstWeekMon() throws ParseException {
        // 1581868800000 2020.2.17
        long expectedTime = 1581868800000L;
        Date date = null;
        Calendar firstWeekMon = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 第一周周一
        date = simpleDateFormat.parse("2020-2-17 0:0:0");
        firstWeekMon = Utils.getFirstWeekMon(1, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + simpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());

        // 第一周五
        date = simpleDateFormat.parse("2020-2-21 12:0:0");
        firstWeekMon = Utils.getFirstWeekMon(1, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + simpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());

        // 周一
        date = simpleDateFormat.parse("2020-5-25 0:0:0");

        firstWeekMon = Utils.getFirstWeekMon(15, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + simpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());
        // 周一
        date = simpleDateFormat.parse("2020-5-25 23:59:59");
        firstWeekMon = Utils.getFirstWeekMon(15, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + simpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());

        // 周五
        date = simpleDateFormat.parse("2020-5-29 12:22:22");
        firstWeekMon = Utils.getFirstWeekMon(15, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + simpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());

        // 周日
        date = simpleDateFormat.parse("2020-5-24 23:59:59");
        firstWeekMon = Utils.getFirstWeekMon(14, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + simpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());
    }

}