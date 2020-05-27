package com.juice.timetable.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.juice.timetable.app.Constant;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Utils {
    private static int[] colorArr = {0xFFEB9D6C, 0xFFF4B997, 0xFFD0DDE3, 0xFFB4DDCF, 0xFFEDB6D5, 0xFFE9D2E2, 0xFFFCE2C1, 0xFFE35B45, 0xFFFD8256, 0xFFF9C272, 0xFF8BD5C8, 0xFFACC864, 0xFFAED5D4, 0xFFF9CE89, 0xFFFFB296, 0xFFFD485B, 0xFF75CAE9, 0xFFA7DDE9, 0xFFF7DB70, 0xFFEF727A, 0xFFFFA3A8, 0xFFFFA791, 0xFF7186A3, 0xFF7CCDEA, 0xFFCBE7F5, 0xFFBEF4E6, 0xFF65EEDE, 0xFF91C7ED, 0xFFF9D1AD, 0xFFDCE1E4, 0xFFF9C0B9, 0xFFFDE9AC, 0xFFAFE9DB};

    /**
     * 解决一些布局问题
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        return (int) (0.5f + dpValue * context.getResources().getDisplayMetrics().density);
    }

    /**
     * 生成UUID
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 返回一个随机颜色
     *
     * @param num
     * @return
     */
    public static int getColor(int num) {
        return colorArr[num % (colorArr.length - 1)];
    }

    /**
     * 获取颜色数组的个数
     *
     * @return
     */
    public static int getColorCount() {
        return colorArr.length;
    }

    /**
     * 判断当前是否为签到时间（注意！时间左右都不包括）
     *
     * @return
     */
    public static boolean isCheckInTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            // 注意！时间左右都不包括 实际是21:40~22:40
            beginTime = df.parse("21:39");
            endTime = df.parse("22:41");
//            LogUtils.getInstance().d(new Date()+"  "+beginTime+"  "+endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now.after(beginTime) && now.before(endTime);
    }

    private static int[] darkColorList = new int[]{
            0xFF5ABF6C,
            0xFFF79060,
            0xFF63C0BD,
            0xFFED837F,
            0xFFF5B94E,
            0xFFCA9483,
            0xFF31A6D3,
            0xFF8BC73D,
            0xFF87A6C6,
            0xFFDF7999,
            0xFFD6A858,
            0xFF997FC3,
            0xFFDDB97B,
            0xFFd3dEe5
    };

    private static Random random = new Random();

    public static int getDarkRandomColor() {
        return darkColorList[random.nextInt(20) % darkColorList.length];
    }

    /**
     * 设置第一周星期一到Preference
     *
     * @param week 当前周
     */
    public static void setFirstWeekPref(int week) {
        Calendar cal = getFirstWeekMon(week, new Date());
        PreferencesUtils.putLong(Constant.PREF_FIRST_WEEK_MONDAY, cal.getTimeInMillis());

    }

    @NotNull
    public static Calendar getFirstWeekMon(int week, Date date) {
        // 当前时间
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        LogUtils.getInstance().d("cal now time -- > " + cal.getTime());

        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        // 周日为:1 周一:2 周二:3 ....
        if (dayWeek == 1) {
            dayWeek = 7;
        } else {
            dayWeek--;
        }
        LogUtils.getInstance().d("dayWeek -- > " + dayWeek);
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        //使用下面这个无效！
//        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天


        // 根据日历的规则，给当前日期减去 周差值
        int dTime = -(week - 1) * 7 - dayWeek + 1;
        LogUtils.getInstance().d("dTime -- > " + dTime);
        cal.add(Calendar.DATE, dTime);
        // 设置0点
        // 时
        cal.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        cal.set(Calendar.MINUTE, 0);
        // 秒
        cal.set(Calendar.SECOND, 0);
        // 毫秒
        cal.set(Calendar.MILLISECOND, 0);
        LogUtils.getInstance().d("第一周星期一：" + cal.getTime());
        return cal;
    }

    /**
     * 获取当前周
     * 不存在return -1
     *
     * @return
     */
    public static int getCurrentWeek() {
        // 获取第一周星期一
        long firstWeekMonday = PreferencesUtils.getLong(Constant.PREF_FIRST_WEEK_MONDAY, 0);
        // 不存在return -1
        if (firstWeekMonday == 0) {
            return -1;
        }
        return getWeekGap(firstWeekMonday, System.currentTimeMillis()) + 1;
    }

    /**
     * 从0开始计数
     * 返回某一周的第一周距离现在的实际周数
     *
     * @param weekBeginMillis
     * @param endMillis
     * @return
     */
    public static int getWeekGap(long weekBeginMillis, long endMillis) {
        return (int) (((endMillis - weekBeginMillis) / (1000 * 3600 * 24)) / 7);
    }

    /**
     * toast接口
     */
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


}
