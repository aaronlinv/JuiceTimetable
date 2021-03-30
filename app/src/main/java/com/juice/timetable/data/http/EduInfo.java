package com.juice.timetable.data.http;

import android.content.Context;

import com.juice.timetable.app.Constant;
import com.juice.timetable.utils.CookieUtils;
import com.juice.timetable.utils.LogUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   : 通过Cookie获取教务系统信息
 *     version: 1.0
 * </pre>
 */
public class EduInfo {
    private static OkHttpClient client = HttpUtils.getHttpClient();

    // 保存Cookie，以减少获取Cookie的次数
    static boolean enableSaveCookie = true;

    public static String getTimeTable(String stuID, String stuPassword, String uri, Context context) throws Exception {
//        String prefEduCookie = PreferencesUtils.getString(Constant.PREF_EDU_COOKIE, null);
        // 加解密Cookie
        String prefEduCookie = CookieUtils.getCookie(Constant.PREF_EDU_COOKIE);

        LogUtils.getInstance().d("PREF_EDU_COOKIE:" + prefEduCookie);
        // 本地存在Cookie先用本地Cookie 尝试登录
        if ((prefEduCookie != null) && enableSaveCookie) {
            LogUtils.getInstance().d("本地存在Cookie先用本地Cookie 尝试登录");
            try {
                // 成功直接返回
                return parse(prefEduCookie, uri);
            } catch (Exception e) {
                LogUtils.getInstance().d("本地Cookie不可用，开始模拟登录获取Cookie");
            }
        }
        // 本地Cookie 不可用，获取新的Cookie 更新Cookie 并返回得到的数据
        String cookie = EduHttp.getCookie(stuID, stuPassword, context);
//        PreferencesUtils.putString(Constant.PREF_EDU_COOKIE, cookie);
        CookieUtils.setCookie(Constant.PREF_EDU_COOKIE, cookie);
        LogUtils.getInstance().d("重新获取教务Cookie结束 Cookie -- > " + cookie);
//
//        prefEduCookie = PreferencesUtils.getString(Constant.PREF_EDU_COOKIE, null);
//        LogUtils.getInstance().d("PREF_EDU_COOKIE 设置后:" + prefEduCookie);

        // 开始根据Cookie 解析数据
        return EduInfo.parse(cookie, uri);
    }

    /**
     * 根据cookie 模拟登录教务系统，获取课表信息
     *
     * @param cookies
     * @param uri
     * @return
     */
    public static String parse(String cookies, String uri) throws Exception {
        Request request = new Request.Builder()
                .addHeader("Referer", "https://jwc.fdzcxy.edu.cn/default.asp")
                .addHeader("Host", "jwc.fdzcxy.edu.cn")
                .addHeader("Cookie", cookies)
                .get()
                .url(uri)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        LogUtils.getInstance().d("根据Cookie获取到的教务网信息：" + result);
        if (result.contains("出错提示")) {
            throw new Exception("登录失败，需要重新获取Cookie");
        } else if (result.contains("New Document")) {
            throw new Exception("登录失败，需要重新获取Cookie");
        }

        return result;
    }

}
