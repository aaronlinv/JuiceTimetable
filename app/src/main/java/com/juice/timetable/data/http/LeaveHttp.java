package com.juice.timetable.data.http;

import com.juice.timetable.utils.FileUtils;
import com.juice.timetable.utils.LogUtils;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LeaveHttp {

    public static String getCookie(String stuID, String stuPassword) throws Exception {

        String userId = FileUtils.hex_md5(stuID);
        String passWd = FileUtils.hex_md5(stuPassword);

        // 登录入口url
        String loginURL = "http://mis.fdzcxy.com/index.php?n=login&s=1001";

        // okHttp
        FormBody formBody = new FormBody.Builder()
                .add("user", userId)
                .add("passwd", passWd)
                .build();
        OkHttpClient client = HttpUtils.getHttpClient();
        Request request = new Request.Builder()
                .post(formBody)
                .url(loginURL)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();

        if (result.contains("{\"result\":false}")) {
            throw new Exception("您输入的请假系统用户名或是密码有误");
        }

        // cookie持久化
        List<String> cookies = response.headers("Set-Cookie");
        LogUtils.getInstance().d("响应：" + result);
        LogUtils.getInstance().d("cookie：" + cookies);

        StringBuffer cookieStr = new StringBuffer();
        for (String cookie : cookies) {
            cookieStr.append(cookieStr);
            cookieStr.append(";");
        }
        return cookieStr.toString();
    }

}
