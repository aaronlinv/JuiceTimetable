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

    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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

        // 第一周周一
        date = mSimpleDateFormat.parse("2020-2-17 0:0:0");
        firstWeekMon = Utils.getFirstWeekMon(1, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + mSimpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());

        // 第一周五
        date = mSimpleDateFormat.parse("2020-2-21 12:0:0");
        firstWeekMon = Utils.getFirstWeekMon(1, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + mSimpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());

        // 周一
        date = mSimpleDateFormat.parse("2020-5-25 0:0:0");

        firstWeekMon = Utils.getFirstWeekMon(15, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + mSimpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());
        // 周一
        date = mSimpleDateFormat.parse("2020-5-25 23:59:59");
        firstWeekMon = Utils.getFirstWeekMon(15, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + mSimpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());

        // 周五
        date = mSimpleDateFormat.parse("2020-5-29 12:22:22");
        firstWeekMon = Utils.getFirstWeekMon(15, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + mSimpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());

        // 周日
        date = mSimpleDateFormat.parse("2020-5-24 23:59:59");
        firstWeekMon = Utils.getFirstWeekMon(14, date);
        LogUtils.getInstance().d("解析后 第一周周一 -- > " + mSimpleDateFormat.format(firstWeekMon.getTimeInMillis()));
        Assert.assertEquals(null, expectedTime, firstWeekMon.getTimeInMillis());
    }

    @Test
    public void getWeekGap() throws ParseException {
        int week;
        // 第一周 周一
        week = weekGap("2020-2-17 0:0:0");
        LogUtils.getInstance().d("第几周 -- > " + week);
        Assert.assertEquals(null, week, 0);
        // 第一周 周一
        week = weekGap("2020-2-17 23:59:59");
        LogUtils.getInstance().d("第几周 -- > " + week);
        Assert.assertEquals(null, week, 0);
        // 第一周 周五
        week = weekGap("2020-2-21 12:59:59");
        LogUtils.getInstance().d("第几周 -- > " + week);
        Assert.assertEquals(null, week, 0);
        // 第一周 周日
        week = weekGap("2020-2-23 23:59:59");
        LogUtils.getInstance().d("第几周 -- > " + week);
        Assert.assertEquals(null, week, 0);
        // 第一周 周日
        week = weekGap("2020-2-23 23:59:59");
        LogUtils.getInstance().d("第几周 -- > " + week);
        Assert.assertEquals(null, week, 0);
        // 第二周 周一
        week = weekGap("2020-2-24 0:0:0");
        LogUtils.getInstance().d("第几周 -- > " + week);
        Assert.assertEquals(null, week, 1);
        // 第十五周 周一
        week = weekGap("2020-5-25 23:59:59");
        LogUtils.getInstance().d("第几周 -- > " + week);
        Assert.assertEquals(null, week, 14);
        // 第十五周 周五
        week = weekGap("2020-5-29 12:59:59");
        LogUtils.getInstance().d("第几周 -- > " + week);
        Assert.assertEquals(null, week, 14);
        // 第十五周 周日
        week = weekGap("2020-5-31 23:59:59");
        LogUtils.getInstance().d("第几周 -- > " + week);
        Assert.assertEquals(null, week, 14);
    }

    public static int weekGap(String time) throws ParseException {
        // 1581868800000 2020.2.17
        long firstWeekMondayTime = 1581868800000L;
        Date date = mSimpleDateFormat.parse(time);
        return Utils.getWeekGap(firstWeekMondayTime, date.getTime());
    }
}