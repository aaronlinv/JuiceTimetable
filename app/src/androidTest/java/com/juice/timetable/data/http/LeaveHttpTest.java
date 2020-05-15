package com.juice.timetable.data.http;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.utils.UserInfoUtils;

import org.junit.Test;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LeaveHttpTest {
    public Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void getCookie2() throws Exception {

        UserInfoUtils instant = UserInfoUtils.getINSTANT(getContext());

        LeaveHttp.getCookie(instant.getID(), instant.getLeavePasswd());

    }
}