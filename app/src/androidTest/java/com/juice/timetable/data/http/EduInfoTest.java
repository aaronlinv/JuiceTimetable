package com.juice.timetable.data.http;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.UserInfoUtils;

import org.junit.Test;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class EduInfoTest {
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

    @Test
    public void parse() {
    }
}