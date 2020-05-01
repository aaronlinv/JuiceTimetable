package com.juice.timetable.utils;

import android.content.Context;

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
}
