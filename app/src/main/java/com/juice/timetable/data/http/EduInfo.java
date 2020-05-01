package com.juice.timetable.data.http;

import android.content.Context;

import com.juice.timetable.utils.LogUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   : 通过Cookie获取教务系统信息
 *     version: 1.0
 * </pre>
 */
public class EduInfo {
    public static String getTimeTable(String stuID, String stuPassword, String uri, Context context) throws Exception {

        String cookie = EduHttp.getCookie(stuID, stuPassword, context);
        String parse = EduInfo.parse(cookie, uri);
        return parse;
    }

    /**
     * 根据cookie 模拟登录教务系统，获取课表信息
     *
     * @param cookies
     * @param uri
     * @return
     */
    public static String parse(String cookies, String uri) {

        HttpClient httpClient = new HttpClient();
        GetMethod getMethod2 = new GetMethod(uri);
        getMethod2.setRequestHeader("referer", "http://jwb.fdzcxy.com/default.asp");
        getMethod2.setRequestHeader("host", "jwb.fdzcxy.com");
        getMethod2.setRequestHeader("cookie", cookies.toString());
        try {
            httpClient.executeMethod(getMethod2);
        } catch (IOException e) {
            LogUtils.getInstance().e("executeMethod异常");
        }
        try {
            byte[] b = getMethod2.getResponseBody();
            LogUtils.getInstance().d("获取课表信息成功");
            String timetable = new String(b, "utf-8");
            LogUtils.getInstance().d(timetable);
            return timetable;

        } catch (IOException e) {
            LogUtils.getInstance().e("getResponseBodyAsString异常");
        }
        return null;
    }

}
