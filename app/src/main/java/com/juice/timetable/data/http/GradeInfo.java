package com.juice.timetable.data.http;


import com.juice.timetable.app.Constant;
import com.juice.timetable.utils.CookieUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/20 13:49
 * </pre>
 */
public class GradeInfo {
    //直接获取PREF_EDU_COOKIE的cookies，不判断是否存在cookie(必存在)
    public static String getGradeSource(String uri) throws Exception {
        String prefCookie = CookieUtils.getCookie(Constant.PREF_EDU_COOKIE);
        //去除"path=/;"
        String prefCookie_del = prefCookie.replaceAll("path=/;","");

        return source(prefCookie_del, uri);
    }

    //根据不同的url获取网页源代码
    public static String source(String cookies, String uri) throws Exception {
        OkHttpClient client = HttpUtils.getHttpClient();
        Request request = new Request.Builder()
                .addHeader("Content-Type", "text/html")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.56")
                .addHeader("Cookie", cookies)
                .get()
                .url(uri)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();

        if (result.contains("欢迎登录至诚信息管理系统")) {
            throw new Exception("Cookie无效,登录失败");
        }
        return result;
    }
}

