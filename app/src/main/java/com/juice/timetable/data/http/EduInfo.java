package com.juice.timetable.data.http;

import android.content.Context;

import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.PreferencesUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   : 通过Cookie获取教务系统信息
 *     version: 1.0
 * </pre>
 */
public class EduInfo {
    static final String PREF_EDU_COOKIE = "PREF_EDU_COOKIE";
    // 保存Cookie，以减少获取Cookie的次数
    static boolean enableSaveCookie = false;

    public static String getTimeTable(String stuID, String stuPassword, String uri, Context context) throws Exception {
        PreferencesUtils.init(context.getApplicationContext());
        String prefEduCookie = PreferencesUtils.getString(PREF_EDU_COOKIE, null);
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
        PreferencesUtils.putString(PREF_EDU_COOKIE, cookie);

        prefEduCookie = PreferencesUtils.getString(PREF_EDU_COOKIE, null);
        LogUtils.getInstance().d("PREF_EDU_COOKIE 设置后:" + prefEduCookie);

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

        HttpClient httpClient = new HttpClient();
        GetMethod getMethod2 = new GetMethod(uri);
        getMethod2.setRequestHeader("referer", "http://jwb.fdzcxy.com/default.asp");
        getMethod2.setRequestHeader("host", "jwb.fdzcxy.com");
        getMethod2.setRequestHeader("cookie", cookies.toString());

        httpClient.executeMethod(getMethod2);

        byte[] b = getMethod2.getResponseBody();
        String info = new String(b, "utf-8");
        LogUtils.getInstance().d("根据Cookie获取到的教务网信息：" + info);

        if (info.contains("出错提示")) {
            throw new Exception("登录失败，需要重新获取Cookie");
        } else if (info.contains("New Document")) {
            throw new Exception("登录失败，需要重新获取Cookie");
        }
        return info;
    }

}
