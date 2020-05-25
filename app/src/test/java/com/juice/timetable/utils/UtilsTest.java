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
    public void getColor() {
        for (int i = 0; i < 1000; i++) {
            int color = Utils.getColor(i);
            System.out.println("颜色：" + color);
        }
    }


}