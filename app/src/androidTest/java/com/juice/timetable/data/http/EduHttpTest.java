package com.juice.timetable.data.http;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.UserInfoUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class EduHttpTest {
    public Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    /**
     * 测试模拟登录教务系统
     *
     * @throws Exception
     */
    @Test
    public void testGetCookie() throws Exception {
        UserInfoUtils instant = UserInfoUtils.getINSTANT(getContext());
        String cookie = EduHttp.getCookie(instant.getID(), instant.getEduPasswd(), getContext());
        LogUtils.getInstance().e("获取教务系统Cookie：" + cookie);
    }

    /**
     * 测试模拟登录教务系统
     * 信息错误情况
     */
    @Test
    public void testGetCookieWrongInfo() {
        try {
            String cookie = EduHttp.getCookie("211706162", "1314520cc", getContext());
        } catch (Exception e) {
            Assert.assertEquals(null, "您输入的教务网用户名或是密码有误", e.getMessage());
        }

    }
}