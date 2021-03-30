package com.juice.timetable.data.http;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.PreferencesUtils;
import com.juice.timetable.utils.UserInfoUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class EduInfoTest {
    ArrayList<OneWeekCourse> couList = new ArrayList<>();

    public Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }


    /**
     * 测试获取周课表
     * 包括 有cookie和无cookie情况
     */
    @Test
    public void testGetTimeTable() {
        // 清除Cookie缓存
        PreferencesUtils.init(getContext());
        PreferencesUtils.clear(Constant.PREF_EDU_COOKIE);
        // 1.无cookie获取课表
        getTimetable();
        // 判断是否缓存cookie
        Assert.assertNotNull(PreferencesUtils.getString(Constant.PREF_EDU_COOKIE, null));

        // 1.有cookie获取课表
        getTimetable();

    }

    public void getTimetable() {
        UserInfoUtils instant = UserInfoUtils.getINSTANT(getContext());
        try {
            String timeTable = EduInfo.getTimeTable(instant.getID(), instant.getEduPasswd(), Constant.URI_ONE_WEEK, getContext());
            LogUtils.getInstance().d("课表数据：" + timeTable);
            Assert.assertTrue(timeTable.contains("完整课表"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用cookie登录教务系统获取周课表
     *
     * @throws Exception
     */
    @Test
    public void testParse() throws Exception {
        UserInfoUtils instant = UserInfoUtils.getINSTANT(getContext());
        String cookie = EduHttp.getCookie(instant.getID(), instant.getEduPasswd(), getContext());
        String parse = EduInfo.parse(cookie, Constant.URI_ONE_WEEK);
        LogUtils.getInstance().d("周课表：" + parse);

    }

    /**
     * 使用失效cookie登录教务系统获取周课表
     * 抛出异常
     *
     * @throws Exception
     */
    @Test
    public void testParseFail() {

        String parse = null;
        try {
            parse = EduInfo.parse("", Constant.URI_ONE_WEEK);
        } catch (Exception e) {
            Assert.assertEquals(null, "登录失败，需要重新获取Cookie", e.getMessage());
        }

    }
}