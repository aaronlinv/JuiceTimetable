package com.juice.timetable.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.bean.StuInfo;

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
public class StuInfoRepositoryTest {

    @Test
    public void getStuInfo() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        StuInfoRepository repository = new StuInfoRepository(appContext);
        StuInfo stuInfo = repository.getStuInfo();
        Log.d("StuInfoTest", stuInfo.toString());
    }
}