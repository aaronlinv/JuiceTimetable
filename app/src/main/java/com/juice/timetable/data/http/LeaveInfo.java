package com.juice.timetable.data.http;

import android.content.Context;

import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.PreferencesUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   : 通过请假系统Cookie，获取相应信息
 *     version: 1.0
 * </pre>
 */
public class LeaveInfo {
    static final String PREF_LEAVE_COOKIE = "PREF_LEAVE_COOKIE";

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
        String prefLeaveCookie = PreferencesUtils.getString(PREF_LEAVE_COOKIE, null);
        LogUtils.getInstance().d("PREF_LEAVE_COOKIE:" + prefLeaveCookie);
        // 本地存在Cookie先用本地Cookie 尝试登录
        if (prefLeaveCookie != null) {
            LogUtils.getInstance().d("本地存在Cookie先用本地Cookie 尝试登录");
            try {
                // 成功直接返回
                return parse(prefLeaveCookie, uri);
            } catch (Exception e) {
                LogUtils.getInstance().d("本地Cookie不可用，开始模拟登录获取Cookie");
            }
        }
        // 本地Cookie 不可用，获取新的Cookie 更新Cookie 并返回得到的数据
        String cookie = LeaveHttp.getCookie(stuID, stuPassword);
        PreferencesUtils.putString(PREF_LEAVE_COOKIE, cookie);

        prefLeaveCookie = PreferencesUtils.getString(PREF_LEAVE_COOKIE, null);
        LogUtils.getInstance().d("PREF_LEAVE_COOKIE 设置后:" + prefLeaveCookie);

        // 开始根据Cookie 解析数据
        return LeaveInfo.parse(cookie, uri);

    }

    /**
     * 根据cookie 爬取请假系统信息
     *
     * @param tmpCookies
     * @return
     */
    public static String parse(String tmpCookies, String uri) {

        HttpClient httpClient = new HttpClient();
        GetMethod getMethod2 = new GetMethod(uri);
        getMethod2.setRequestHeader("referer", "http://mis.fdzcxy.com/index.php?n=login");
        getMethod2.setRequestHeader("host", "mis.fdzcxy.com");
        getMethod2.setRequestHeader("cookie", tmpCookies.toString());
        getMethod2.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        try {
            httpClient.executeMethod(getMethod2);
        } catch (IOException e) {
            LogUtils.getInstance().e("executeMethod异常");
        }
        try {
            byte[] b = getMethod2.getResponseBody();
            LogUtils.getInstance().d("获取请假系统信息成功");
            String leaveInfo = new String(b, "utf-8");
            LogUtils.getInstance().d(leaveInfo);
            return leaveInfo;
        } catch (IOException e) {
            LogUtils.getInstance().e("getResponseBodyAsString异常！！");
        }
        return null;
    }
}
