package com.juice.timetable;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.database.Dao.OneWeekCourseDao;
import com.juice.timetable.data.database.JuiceDatabase;
import com.juice.timetable.data.parse.ParseOneWeek;
import com.juice.timetable.utils.LogUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    static OneWeekCourseDao oneWeekCourseDao;
    static JuiceDatabase juiceDatabase;
    static LiveData<List<OneWeekCourse>> OneWeekCourseLive;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

//        assertEquals("com.juice.timetable", appContext.getPackageName());
        List<OneWeekCourse> oneWeekCoursesList = ParseOneWeek.parseCourse();
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(appContext);
        oneWeekCourseDao = juiceDatabase.getCourseDao();
        OneWeekCourseLive = oneWeekCourseDao.getOneWeekCourseLive();
        for (OneWeekCourse oneWeekCourse : oneWeekCoursesList) {
            LogUtils.getInstance().d("oneWeekCourse"+oneWeekCourse.toString());
            oneWeekCourseDao.insertOneWeekCourse(oneWeekCourse);
        }

        LogUtils.getInstance().d("数据库内容：");
        for (OneWeekCourse oneWeekCourse : oneWeekCoursesList) {

            LogUtils.getInstance().d(oneWeekCourse.toString());
        }

    }
}
