package com.juice.timetable.data.http;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

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
public class EduHttpTest {


    @Test
    public void getCookie() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String cookie = EduHttp.getCookie(getID(), getEduPasswd(), getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

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
}