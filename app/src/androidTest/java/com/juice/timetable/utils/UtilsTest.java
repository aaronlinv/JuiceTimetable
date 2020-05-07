package com.juice.timetable.utils;

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

    @Test
    public void isCheckInTime() {
        LogUtils.getInstance().d("isCheckInTime:" + Utils.isCheckInTime());
    }
}