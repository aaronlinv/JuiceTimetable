package com.juice.timetable.data.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.juice.timetable.utils.CaptchaUtils;
import com.juice.timetable.utils.HttpUtils;
import com.juice.timetable.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   : 模拟登录获取教务系统Cookie
 *     version: 1.0
 * </pre>
 */
public class EduHttp {
    private static OkHttpClient client = HttpUtils.getHttpClient();

    public static String getCookie(String stuID, String stuPassword, Context context) throws Exception {
        String firstCookie = firstLogin();
        Bitmap captcha = getCaptcha(firstCookie);
        String code = CaptchaUtils.getAllOcr(captcha, context);
        LogUtils.getInstance().e("自动识别验证结果：" + code);

        String cookies = login(stuID, stuPassword, firstCookie, code);

        String muser = extractValueFromCookie(cookies, "muser");
        String ssid = extractValueFromCookie(cookies, "ssid");

        return createCookieString(muser, ssid);
    }

    /**
     * 初次登录，获取Cookie
     *
     * @return
     */
    private static String firstLogin() throws IOException {
        // 教务网主页url
        String mainURL = "https://jwc.fdzcxy.edu.cn";

        Request request = new Request.Builder()
                .get()
                .url(mainURL)
                .build();
        Response response = client.newCall(request).execute();
        // cookie持久化
        List<String> cookies = response.headers("Set-Cookie");
        LogUtils.getInstance().d("cookie：" + cookies);

        StringBuffer cookieStr = new StringBuffer();
        for (String cookie : cookies) {
            cookieStr.append(cookie);
            cookieStr.append(";");
        }
        LogUtils.getInstance().d("Cookie String:" + cookieStr);
        return cookieStr.toString();
    }

    /**
     * 获取验证码
     *
     * @return
     */
    private static Bitmap getCaptcha(String firstCookie) throws IOException {
        // 验证码获取url
        String checkCodeURL = "https://jwc.fdzcxy.edu.cn/ValidateCookie.asp";
        LogUtils.getInstance().d("获取验证码的cookie：" + firstCookie);
        Request request = new Request.Builder()
                .addHeader("Cookie", firstCookie)
                .get()
                .url(checkCodeURL)
                .build();
        Response response = client.newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        return BitmapFactory.decodeStream(inputStream);
    }

    private static String login(String stuID, String stuPassword, String firstCookie, String code) throws Exception {
        // 登录入口url
        String loginURL = "https://jwc.fdzcxy.edu.cn/loginchk.asp?id=.447517";

        // okHttp
        FormBody formBody = new FormBody.Builder()
                .add("muser", stuID)
                .add("passwd", stuPassword)
                .add("code", code)
                .build();
        Request request = new Request.Builder()
                .addHeader("cookie", firstCookie)
                .post(formBody)
                .url(loginURL)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        LogUtils.getInstance().d("result == >" + result);
        if (TextUtils.isEmpty(result)) {
            throw new Exception("responseBody为空");
        } else if (result.contains("您输入的用户名或是密码出错")) {
            throw new Exception("您输入的教务网用户名或是密码有误");
        } else if (result.contains("您输入的验证码不正确")) {
            throw new Exception("验证码识别失败，请再尝试一次");
        } else if (result.contains("您已连续输错密码3次，请过5分钟再尝试")) {
            throw new Exception("您已连续输错教务网密码3次，请过5分钟再尝试");
        } else if (result.contains("用户密码修改")) {
            throw new Exception("密码太简单，请登陆教务网重新设置密码");
        } else if (!result.contains("网络办公系统")) {
            LogUtils.getInstance().d("模拟登录成功");
        }

        StringBuffer cookie = new StringBuffer(firstCookie);
        // 需要添加Cookie：muser
        cookie.append("muser=").append(stuID);
        return cookie.toString();
    }

    private static String extractValueFromCookie(String cookieString, String key) {
        String[] cookies = cookieString.split(";");

        for (String cookie : cookies) {
            String[] keyValue = cookie.trim().split("=");

            if (keyValue.length == 2 && keyValue[0].equals(key)) {
                return keyValue[1];
            }
        }

        return null;
    }

    private static String createCookieString(String muser, String ssid) {
        return "muser=" + muser + "; " +
                "ssid=" + ssid + "; ";
    }
}
