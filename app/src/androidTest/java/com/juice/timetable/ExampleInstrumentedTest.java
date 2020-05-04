package com.juice.timetable;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.ClassNoSignedItemDao;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.MyCheckInDao;
import com.juice.timetable.data.OneWeekCourseDao;
import com.juice.timetable.data.StuInfoDao;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.parse.ParseCheckIn;
import com.juice.timetable.data.parse.ParseClassNoSignedItem;
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
    private MyCheckIn myCheckIn;
    private StuInfo stuInfo;

    private OneWeekCourseDao CourseDao;
    private ClassNoSignedItemDao classNoSignedItemDao;
    private MyCheckInDao myCheckInDao;
    private StuInfoDao stuInfoDao;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.juice.timetable", appContext.getPackageName());

        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(appContext);

        CourseDao = juiceDatabase.getOneWeekCourseDao();
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        myCheckInDao = juiceDatabase.getMyCheckInDao();
        stuInfoDao = juiceDatabase.getStuInfoDao();

        List<OneWeekCourse> a = ParseOneWeek.parseCourse();
        for (OneWeekCourse oneWeekCourse : a) {
            CourseDao.insertCourse(oneWeekCourse);
        }
        List<ClassNoSignedItem> b = ParseClassNoSignedItem.getClassUnSigned();
        for (ClassNoSignedItem classNoSignedItem : b) {
            classNoSignedItemDao.insertNoSignedItem(classNoSignedItem);
        }
        MyCheckIn myCheckIn1 = ParseCheckIn.getMySigned();
        myCheckInDao.insertMyCheckIn(myCheckIn1);
        StuInfo stuInfo1 = new StuInfo();
        stuInfo1.setStuID(211706162);
        stuInfo1.setEduPassword("123456");
        stuInfo1.setLeavePassword("123456");
        stuInfoDao.insertStuInfo(stuInfo1);

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
            Log.d("b1", a1);
            Log.d("b2", a2);
            Log.d("b3", a3);
        }
        myCheckIn = myCheckInDao.getMyCheckIn();
        Log.d("c1", myCheckIn.getCheckTime());

        stuInfo = stuInfoDao.getStuInfo();
        Log.d("d1", String.valueOf(stuInfo.getStuID()));
        Log.d("d2", stuInfo.getEduPassword());
        Log.d("d3", stuInfo.getLeavePassword());
    }
}
