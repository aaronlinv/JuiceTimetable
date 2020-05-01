package com.juice.timetable.data.http;

import com.juice.timetable.utils.FileUtils;
import com.juice.timetable.utils.LogUtils;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LeaveHttp {
    public static String getCookie(String stuID, String stuPassword) throws IOException {

        String userId = FileUtils.hex_md5(stuID);
        String passWd = FileUtils.hex_md5(stuPassword);
        String cookies = "";

        // 请假系统主页url
        // 使用：http://mis.fdzcxy.com/index.php 报错：Circular redirect
        String mainURL = "http://mis.fdzcxy.com";

        // 登录入口url
        String loginURL = "http://mis.fdzcxy.com/index.php?n=login&s=1001";

        //创建浏览器对象
        HttpClient httpClient = new HttpClient();

        // 访问主页获取PHPSESSID
        GetMethod getMethod = new GetMethod(mainURL);

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

        LogUtils.getInstance().d("从请假系统登录页获取的cookie:" + cookies);

        //模拟登录
        PostMethod postMethod = new PostMethod(loginURL);
        //设置cookie
        postMethod.setRequestHeader("cookie", cookies);
        //设置登录时需要的信息，用户名和密码
        NameValuePair[] data = {
                new NameValuePair("user", userId),
                new NameValuePair("passwd", passWd),
        };
        postMethod.setRequestBody(data);

        int statusCode = httpClient.executeMethod(postMethod);
        LogUtils.getInstance().d("发送账号密码返回的状态码：" + statusCode);

        byte[] b = postMethod.getResponseBody();
        LogUtils.getInstance().d("响应体：");
        LogUtils.getInstance().d(new String(b, "utf-8"));


        // 登录成功后当前cookie将能够访问系统
        return cookies;

    }

}
