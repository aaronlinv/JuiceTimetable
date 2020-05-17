package com.juice.timetable.data.http;

import android.content.Context;

import com.juice.timetable.app.Constant;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.PreferencesUtils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   : 通过请假系统Cookie，获取相应信息
 *     version: 1.0
 * </pre>
 */
public class LeaveInfo {
    // 保存Cookie，以减少获取Cookie的次数
    private static boolean enableSaveCookie = true;

    /**
     * 获取请假信息的主函数
     *
     * @param stuID
     * @param stuPassword
     * @return
     * @throws IOException
     */
    public static String getLeave(String stuID, String stuPassword, String uri, Context context) throws Exception {
        PreferencesUtils.init(context.getApplicationContext());
        String prefLeaveCookie = PreferencesUtils.getString(Constant.PREF_LEAVE_COOKIE, null);
        LogUtils.getInstance().d("PREF_LEAVE_COOKIE:" + prefLeaveCookie);
        // 本地存在Cookie先用本地Cookie 尝试登录
        if ((prefLeaveCookie != null) && enableSaveCookie) {
            LogUtils.getInstance().d("本地存在Cookie先用本地Cookie 尝试登录");
            try {
                // 成功直接返回
                return parse(prefLeaveCookie, uri);
            } catch (Exception e) {
                // 存在异常，重新获取cookie
                LogUtils.getInstance().d("本地Cookie不可用，开始模拟登录获取Cookie");
            }
        }
        // 本地Cookie 不可用，获取新的Cookie 更新Cookie 并返回得到的数据
        String cookie = LeaveHttp.getCookie(stuID, stuPassword);
        PreferencesUtils.putString(Constant.PREF_LEAVE_COOKIE, cookie);

        prefLeaveCookie = PreferencesUtils.getString(Constant.PREF_LEAVE_COOKIE, null);
        LogUtils.getInstance().d("PREF_LEAVE_COOKIE 设置后:" + prefLeaveCookie);

        // 开始根据Cookie 解析数据
        return LeaveInfo.parse(cookie, uri);

    }

    /**
     * 根据cookie 爬取请假系统信息
     *
     * @param cookies
     * @return
     */
    public static String parse(String cookies, String uri) throws Exception {

        OkHttpClient client = HttpUtils.getHttpClient();

        Request request = new Request.Builder()
                .addHeader("Referer", "http://mis.fdzcxy.com/index.php?n=login")
                .addHeader("Host", "mis.fdzcxy.com")
                .addHeader("Cookie", cookies)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
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
