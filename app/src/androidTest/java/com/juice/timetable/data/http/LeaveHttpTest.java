package com.juice.timetable.data.http;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.utils.UserInfoUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/15
 *     desc   : 自动化测试 模拟登录请假系统
 *     version: 1.0
 * </pre>
 */
public class LeaveHttpTest {
    public Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    /**
     * 测试获取Cookie
     * 信息正确，获取Cookie不为空
     *
     * @throws Exception
     */
    @Test
    public void getCookieSuccess() throws Exception {
        UserInfoUtils instant = UserInfoUtils.getINSTANT(getContext());
        String cookie = LeaveHttp.getCookie(instant.getID(), instant.getLeavePasswd());
        Assert.assertNotEquals("", cookie);

    }

    /**
     * 测试获取Cookie
     * 信息错误，应该抛出异常
     *
     * @throws Exception
     */
    @Test
    public void getCookieFail() throws Exception {


        try {
            UserInfoUtils instant = UserInfoUtils.getINSTANT(getContext());
            LeaveHttp.getCookie("211706162", "1314520");
        } catch (Exception e) {
            Assert.assertEquals(null, "您输入的请假系统用户名或是密码有误", e.getMessage());
        }

    }
}