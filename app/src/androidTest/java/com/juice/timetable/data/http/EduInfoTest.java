package com.juice.timetable.data.http;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.parse.ParseOneWeek;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.UserInfoUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    public String getUserInfo(String info) {
        return UserInfoUtils.getINSTANT(getContext()).getProperty(info);
    }

    public String getID() {
        return getUserInfo("id");
    }

    public String getEduPasswd() {
        return getUserInfo("eduPasswd");
    }

    public String getLeavePasswd() {
        return getUserInfo("leavePasswd");
    }


    @Test
    public void getTimeTable() {
        // 周课表
        String uri = "http://jwb.fdzcxy.com/kb/zkb_xs.asp";
        try {
            String timeTable = EduInfo.getTimeTable(getID(), getEduPasswd(), uri, getContext());
            LogUtils.getInstance().d("课表数据：" + timeTable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试获取第10周周课表并解析
     *
     * @throws Exception
     */
    @Test
    public void getOneWeekCourse() throws Exception {

        String oneWeekCourse = EduInfo.getOneWeekCourse(10, getContext());
        List<OneWeekCourse> oneWeekCourses = ParseOneWeek.parseCourse(oneWeekCourse);
        for (OneWeekCourse oneWeekCours : oneWeekCourses) {
            System.out.println(oneWeekCours);
        }
        System.out.println(oneWeekCourse);
    }

    @Test
    public void getWeekCourse() throws Exception {
        List<OneWeekCourse> tempList = new ArrayList<>();
        int week = 0;

//        oneWeekCourseDao.deleteCourse();
//        List<Integer> inWeek = oneWeekCourseDao.getInWeek();
        // 获取数据库中存了哪些周的周课表
        HashSet<Integer> set = new HashSet<>();
        LogUtils.getInstance().d("周课表set:" + set);
        // 数据库为空 需要爬取上两周课程
        if (set.isEmpty()) {
            week = -2;
        }
        // 模拟登录获取课表数据
        for (; week <= 2; week++) {
            String oneWeekCourse = EduInfo.getOneWeekCourse(Constant.CUR_WEEK + week, getContext());
            List<OneWeekCourse> oneWeekCourses = ParseOneWeek.parseCourse(oneWeekCourse);
            LogUtils.getInstance().d("获取第 <" + (Constant.CUR_WEEK + week) + "> 周课表");
            LogUtils.getInstance().d("获取第 <" + (Constant.CUR_WEEK + week) + "> 周课表 获取前List:" + oneWeekCourses);
            for (OneWeekCourse oneWeekCours : oneWeekCourses) {
                LogUtils.getInstance().d("获取第 <" + (Constant.CUR_WEEK + week) + "> 周课表:" + oneWeekCours);
            }
            couList.addAll(oneWeekCourses);
            // 删除该数据库中周的课表，避免冲突

        }
        for (OneWeekCourse oneWeekCourse : couList) {
            LogUtils.getInstance().d(oneWeekCourse.toString());
            // 插入数据库
//            oneWeekCourseDao.insertCourse(oneWeekCourse);

        }
        LogUtils.getInstance().d("解析本周、上两周、下两周的周课表 结束");
    }

    /**
     * 使用cookie登录教务系统获取周课表
     *
     * @throws Exception
     */
    @Test
    public void testParse() throws Exception {

        String cookie = EduHttp.getCookie(getID(), getEduPasswd(), getContext());
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