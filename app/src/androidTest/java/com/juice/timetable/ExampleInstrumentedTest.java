package com.juice.timetable;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.ClassNoSignedItemDao;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.OneWeekCourseDao;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.parse.ParseCheckIn;
import com.juice.timetable.data.parse.ParseOneWeek;

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
    private List<OneWeekCourse> CourseList;
    private List<ClassNoSignedItem> classNoSignedItems;
    private OneWeekCourseDao CourseDao;
    private ClassNoSignedItemDao classNoSignedItemDao;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.juice.timetable", appContext.getPackageName());
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(appContext);
        CourseDao = juiceDatabase.getOneWeekCourseDao();
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        List<OneWeekCourse> a = ParseOneWeek.parseCourse();
        for (OneWeekCourse oneWeekCourse : a) {
            CourseDao.insertCourse(oneWeekCourse);
        }
        List<ClassNoSignedItem> b = ParseCheckIn.getClassUnSigned();
        for (ClassNoSignedItem classNoSignedItem : b) {
            classNoSignedItemDao.insertNoSignedItem(classNoSignedItem);
        }

        CourseList = CourseDao.getOneWeekCourseLive();
        for (OneWeekCourse course : CourseList) {
            String a1 = course.getCouName();
            String a2 = course.getCouRoom();
            String a3 = "nihao===============";
            Log.d("a1", a1);
            Log.d("a2", a2);
            Log.d("a3", a3);

        }
        classNoSignedItems = classNoSignedItemDao.getNoSignedItem();
        for (ClassNoSignedItem classNoSignedItem : classNoSignedItems) {
            String a1 = classNoSignedItem.getSno();
            String a2 = classNoSignedItem.getSname();
            String a3 = "nihao===============";
            Log.d("a1", a1);
            Log.d("a2", a2);
            Log.d("a3", a3);
        }
    }
}
