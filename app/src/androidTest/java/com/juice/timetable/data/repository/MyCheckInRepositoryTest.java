package com.juice.timetable.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.bean.MyCheckIn;

import org.junit.Test;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/24
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class MyCheckInRepositoryTest {

    @Test
    public void getMyCheckIn() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        MyCheckInRepository repository = new MyCheckInRepository(appContext);
        MyCheckIn myCheckIn = repository.getMyCheckIn();
        Log.d("MyCheckInTest", myCheckIn.toString());
    }
}