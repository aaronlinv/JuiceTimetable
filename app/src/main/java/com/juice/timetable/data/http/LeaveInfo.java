package com.juice.timetable.data.http;

import com.juice.timetable.utils.LogUtils;

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
    /**
     * 获取请假信息的主函数
     *
     * @param stuID
     * @param stuPassword
     * @return
     * @throws IOException
     */
    public static String getLeave(String stuID, String stuPassword, String uri) throws IOException {

        String cookie = LeaveHttp.getCookie(stuID, stuPassword);
        String info = LeaveInfo.parse(cookie, uri);
        return info;

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
