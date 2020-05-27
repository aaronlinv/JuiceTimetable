package com.juice.timetable.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.bean.OneWeekCourse;

import org.junit.Test;

import java.util.List;

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
public class OneWeekCourseRepositoryTest {

    @Test
    public void getOneWeekCourse() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        OneWeekCourseRepository repository = new OneWeekCourseRepository(appContext);
        List<OneWeekCourse> oneWeekCourses = repository.getOneWeekCourse();
        for (OneWeekCourse oneWeekCours : oneWeekCourses) {
            Log.d("oneweekcourseTest", oneWeekCours.toString());
        }
    }

    @Test
    public void getWeek() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        OneWeekCourseRepository repository = new OneWeekCourseRepository(appContext);
        List<Integer> week = repository.getWeek();
        for (Integer integer : week) {
            Log.d("oneweekcourseTest", integer.toString());
        }


    }
}