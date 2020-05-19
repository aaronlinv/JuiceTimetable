package com.juice.timetable.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.Toast;

import com.juice.timetable.app.Constant;

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
        int[] colorArr = {0xFFEB9D6C, 0xFFF4B997, 0xFFD0DDE3, 0xFFB4DDCF, 0xFFEDB6D5, 0xFFE9D2E2, 0xFFFCE2C1, 0xFFE35B45, 0xFFFD8256, 0xFFF9C272, 0xFF8BD5C8, 0xFFACC864, 0xFFAED5D4, 0xFFF9CE89, 0xFFFFB296, 0xFFFD485B, 0xFF75CAE9, 0xFFA7DDE9, 0xFFF7DB70, 0xFFEF727A, 0xFFFFA3A8, 0xFFFFA791, 0xFF7186A3, 0xFF7CCDEA, 0xFFCBE7F5, 0xFFBEF4E6, 0xFF65EEDE, 0xFF91C7ED, 0xFFF9D1AD, 0xFFDCE1E4, 0xFFF9C0B9, 0xFFFDE9AC, 0xFFAFE9DB};
        return colorArr[num % (colorArr.length - 1)];
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

    /*
    阿发功能
     */
    private static int[] colorList = new int[]{
            0xFF8AD297,
            0xFFF9A883,
            0xFF88CFCC,
            0xFFF19C99,
            0xFFF7C56B,
            0xFFD2A596,
            0xFF67BDDE,
            0xFF9CCF5A,
            0xFF9AB4CF,
            0xFFE593AD,
            0xFFE2C38A,
            0xFFB29FD2,
            0xFFE2C490,
            0xFFE2C490,
    };

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
            0xFFd3dEe5};

    public static GradientDrawable
    getDrawable(Context context, int rgb,
                float radius, int stroke, int strokeColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(rgb);
        gradientDrawable.setCornerRadius(dip2px(context, radius));
        gradientDrawable.setStroke(dip2px(context, stroke), strokeColor);
        return gradientDrawable;
    }

    public static StateListDrawable
    getPressedSelector(Context context, int color, int pressedColor, float radius) {
        GradientDrawable normalD = getDrawable(context, color, radius, 0, 0);
        GradientDrawable pressedD = getDrawable(context, pressedColor, radius, 0, 0);

        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressedD);
        drawable.addState(new int[]{}, normalD);
        return drawable;
    }

    private static Random random = new Random();

    public static int getRandomColor() {
        return colorList[random.nextInt(20) % colorList.length];
    }

    public static int getDarkRandomColor() {
        return darkColorList[random.nextInt(20) % colorList.length];
    }
    /**
     * 设置第一周星期一到Preference
     *
     * @param week 当前周
     */
    public static void setFirstWeekPref(int week) {
        // 当前时间
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        // 周日实际上是上一周
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去 周差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - week * 7);

        LogUtils.getInstance().d("第一周星期一：" + cal.getTime());

        PreferencesUtils.putLong(Constant.PREF_FIRST_WEEK_MONDAY, cal.getTimeInMillis());

    }

    /**
     * 获取当前周
     *
     * @return
     */
    public static int getCurrentWeek() {
        // 获取第一周星期一
        long first_week_monday = PreferencesUtils.getLong(Constant.PREF_FIRST_WEEK_MONDAY, System.currentTimeMillis());
        return getWeekGap(first_week_monday, System.currentTimeMillis()) + 1;
    }

    /**
     * 返回某一周的第一周距离现在的实际周数
     *
     * @param weekBeginMillis
     * @param endMillis
     * @return
     */
    public static int getWeekGap(long weekBeginMillis, long endMillis) {
        return (int) (((endMillis - weekBeginMillis) / (1000 * 3600 * 24)) / 7);
    }
    public static int getColor(Resources resources, int colorId) {
        return resources.getColor(colorId);
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
