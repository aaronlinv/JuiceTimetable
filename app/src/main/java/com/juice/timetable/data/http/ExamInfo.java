package com.juice.timetable.data.http;

import com.juice.timetable.app.Constant;
import com.juice.timetable.utils.CookieUtils;
import com.juice.timetable.utils.HttpUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/1/2 9:41
 * </pre>
 */
public class ExamInfo {
    public static String getExamSource(String uri) throws Exception {
        String prefCookie = CookieUtils.getCookie(Constant.PREF_EDU_COOKIE).replaceAll("path=/;", "");

        return source(prefCookie, uri);
    }

    //根据不同的url获取网页源代码
    public static String source(String cookies, String url) throws Exception {
        OkHttpClient client = HttpUtils.getHttpClient();
        Request request = new Request.Builder()
                .addHeader("Content-Type", "text/html")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.56")
                .addHeader("Cookie", cookies)
                .get()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

}
