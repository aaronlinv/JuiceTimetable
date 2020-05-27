package com.juice.timetable.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.bean.Course;

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
public class AllWeekCourseRepositoryTest {

    @Test
    public void getAllWeekCourse() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AllWeekCourseRepository repository = new AllWeekCourseRepository(appContext);
        List<Course> courses = repository.getAllWeekCourse();
        for (Course cours : courses) {
            Log.d("allweekcourseTest", cours.toString());
        }
    }
}