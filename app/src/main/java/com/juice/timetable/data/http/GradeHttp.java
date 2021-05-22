package com.juice.timetable.data.http;

import com.juice.timetable.app.Constant;
import com.juice.timetable.utils.CookieUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/6 10:08
 * </pre>
 */
public class GradeHttp {
    public static void getGrade(){
        //从shp中(相当于硬盘)读取加密的cookies进行解密
        String prefEduCookie = CookieUtils.getCookie(Constant.PREF_EDU_COOKIE);
//        Connection con = Jsoup.connect(url);
    }


}
