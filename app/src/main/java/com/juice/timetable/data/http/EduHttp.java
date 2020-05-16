package com.juice.timetable.data.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.juice.timetable.utils.CaptchaUtils;
import com.juice.timetable.utils.FileUtils;
import com.juice.timetable.utils.LogUtils;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import kotlin.Pair;
import okhttp3.FormBody;
import okhttp3.Headers;
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
        return cookies;
    }

    /**
     * 初次登录，获取Cookie
     *
     * @return
     */
    private static String firstLogin() throws IOException {
        // 教务网主页url
        String mainURL = "http://jwb.fdzcxy.com/default.asp";

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
        String checkCodeURL = "http://jwb.fdzcxy.com/ValidateCookie.asp";
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
        String loginURL = "http://jwb.fdzcxy.com/loginchk.asp?id=.447517";

        // okHttp
        FormBody formBody = new FormBody.Builder()
                .add("muser", stuID)
                .add("passWd", stuPassword)
                .add("code", code)
                .build();
        Request request = new Request.Builder()
                .addHeader("cookie", firstCookie)
                .post(formBody)
                .url(loginURL)
                .build();

        Response response = client.newCall(request).execute();
        Headers headers = response.headers();
        for (Pair<? extends String, ? extends String> header : headers) {
            LogUtils.getInstance().d("header:" + header.toString());

        }
//        okHttp似乎不会返回302，直接返回登录成功的200
//        int statusCode = response.code();
//        LogUtils.getInstance().d("模拟登录状态码：" + statusCode);
        String result = response.body().string();
        LogUtils.getInstance().d(result);
        if (result == null) {
            throw new Exception("responseBody为空");
        } else if (result.contains("您输入的用户名或是密码出错")) {
            throw new Exception("您输入的教务网用户名或是密码有误");
        } else if (result.contains("您输入的验证码不正确")) {
            throw new Exception("验证码识别失败，请再尝试一次");
        } else if (result.contains("您已连续输错密码3次，请过5分钟再尝试")) {
            throw new Exception("您已连续输错教务网密码3次，请过5分钟再尝试");
        } else if (!result.contains("网络办公系统")) {
            LogUtils.getInstance().d("模拟登录失败");
        }

        StringBuffer cookie = new StringBuffer(firstCookie);
        // 需要添加Cookie：muser
        cookie.append("muser=").append(stuID);
        return cookie.toString();
    }

    public static String getCookieOld(String stuID, String stuPassword, Context context) throws
            Exception {

        String cookies = "";
        String code = "";


        // 登录入口url
        String loginURL = "http://jwb.fdzcxy.com/loginchk.asp?id=.447517";
        // 验证码获取url
        String checkCodeURL = "http://jwb.fdzcxy.com/ValidateCookie.asp";


        // 创建浏览器对象
        HttpClient httpClient = new HttpClient();
        // 访问主页获取ssid
        GetMethod getMethod = new GetMethod("mainURL");

        //设置HttpClient接收Cookie
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        httpClient.executeMethod(getMethod);

        //获取访问后得到的Cookie
        Cookie[] cookiesArr = httpClient.getState().getCookies();
        StringBuffer tmpCookies = new StringBuffer();
        for (Cookie c1 : cookiesArr) {
            tmpCookies.append(c1.toString() + ";");
        }
        cookies = tmpCookies.toString();

        LogUtils.getInstance().d("首次获取cookie：" + cookies);
        // 访问验证码页面，获取验证码
        GetMethod getMethod1 = new GetMethod(checkCodeURL);

        // 加上刚才获取到的cookie
        getMethod1.setRequestHeader("cookie", tmpCookies.toString());
        try {

            //设置HttpClient接收Cookie
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(getMethod1);
            // 获取验证码 写入验证码到数据区
            InputStream is = getMethod1.getResponseBodyAsStream();
            FileUtils.saveFile(context, is, "checkCode");
        } catch (Exception e) {
            e.printStackTrace();

        }

        Bitmap codeImg = null;
        try {
            // 读取数据区验证码
            InputStream inputStream = FileUtils.getFile(context);
            codeImg = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 自动识别验证码
        code = CaptchaUtils.getAllOcr(codeImg, context);
        LogUtils.getInstance().d("自动识别验证码:" + code);


        //模拟登录，Post请求方式
        PostMethod postMethod = new PostMethod(loginURL);
        //设置获取到的cookie
        postMethod.setRequestHeader("cookie", cookies);
        //设置登录时需要的信息，用户名和密码
        NameValuePair[] data = {
                new NameValuePair("muser", stuID),
                new NameValuePair("passWd", stuPassword),
                new NameValuePair("code", code)
        };
        postMethod.setRequestBody(data);

        int statusCode = httpClient.executeMethod(postMethod);
        LogUtils.getInstance().d("发送账号密码接收的状态码： " + statusCode);

        byte[] b = postMethod.getResponseBody();
        String responseBody = new String(b, "utf-8");
        LogUtils.getInstance().d("ResponseBody:" + responseBody);


        //重定向 获取可用于免登录的cookie
        StringBuffer finallyCookies = new StringBuffer();
        if (statusCode == 302) {
            LogUtils.getInstance().d("模拟登录成功");

            //设置HttpClient接收Cookie，用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            //获取登录后的Cookie
            Cookie[] cookies3 = httpClient.getState().getCookies();

            for (Cookie c : cookies3) {
                finallyCookies.append(c.toString() + ";");
            }
            LogUtils.getInstance().d("登录页面cookies：" + finallyCookies.toString());

        } else if (responseBody == null) {
            throw new Exception("responseBody为空");
        } else if (responseBody.contains("您输入的用户名或是密码出错")) {
            throw new Exception("您输入的教务网用户名或是密码有误");
        } else if (responseBody.contains("您输入的验证码不正确")) {
            throw new Exception("验证码识别失败，请再尝试一次");
//            LogUtils.getInstance().d("登录页面cookies：" + finallyCookies.toString());
        } else if (responseBody.contains("您已连续输错密码3次，请过5分钟再尝试")) {
            throw new Exception("您已连续输错教务网密码3次，请过5分钟再尝试");
        } else {
            throw new Exception("非302 跳转");
        }

        return finallyCookies.toString();
    }
}
