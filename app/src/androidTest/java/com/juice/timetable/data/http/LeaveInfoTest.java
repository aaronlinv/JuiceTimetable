package com.juice.timetable.data.http;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.app.Constant;
import com.juice.timetable.utils.UserInfoUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/07
 *     desc   : 自动化测试 获取请假系统信息
 *     version: 1.0
 * </pre>
 */
public class LeaveInfoTest {

    public Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    /**
     * 测试签到
     */
    @Test
    public void getLeave() throws Exception {
        UserInfoUtils instant = UserInfoUtils.getINSTANT(getContext());

        String leave = LeaveInfo.getLeave(instant.getID(), instant.getLeavePasswd(), Constant.URI_CHECK_IN, getContext());

        System.out.println(leave);
        Assert.assertTrue(leave.contains("签到记录"));
    }

    /**
     * Cookie有效情况测试
     * 得到签到详情界面
     *
     * @throws Exception
     */
    @Test
    public void parseSuccess() throws Exception {
        UserInfoUtils instant = UserInfoUtils.getINSTANT(getContext());

        // 获取Cookie
        String cookie = LeaveHttp.getCookie(instant.getID(), instant.getLeavePasswd());
        // 测试使用Cookie登录
        String parse = LeaveInfo.parse(cookie, Constant.URI_CHECK_IN);
        System.out.println(parse);
        Assert.assertTrue(parse.contains("签到记录"));

    }

    /**
     * Cookie无效情况测试
     * 跳转到主页
     *
     * @throws Exception
     */
    @Test
    public void parseFail() throws IOException {
        try {
            String parse = LeaveInfo.parse("", Constant.URI_CHECK_IN);
            System.out.println(parse);
        } catch (Exception e) {
            Assert.assertEquals(null, "Cookie无效,登录失败", e.getMessage());
        }
    }


}