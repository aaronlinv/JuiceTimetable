package com.juice.timetable;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.bean.AllWeekCourse;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.bean.MyCheckInInfo;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.database.Dao.AllWeekCourseDao;
import com.juice.timetable.data.database.Dao.ClassNoSignedItemDao;
import com.juice.timetable.data.database.Dao.MyCheckInInfoDao;
import com.juice.timetable.data.database.Dao.OneWeekCourseDao;
import com.juice.timetable.data.database.Dao.StuInfoDao;
import com.juice.timetable.data.database.JuiceDatabase;
import com.juice.timetable.data.parse.ParseAllWeek;
import com.juice.timetable.data.parse.ParseCheckIn;
import com.juice.timetable.data.parse.ParseOneWeek;
import com.juice.timetable.utils.LogUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    static OneWeekCourseDao oneWeekCourseDao;
    static AllWeekCourseDao allWeekCoursesDao;
    static ClassNoSignedItemDao classNoSignedItemDao;
    static MyCheckInInfoDao myCheckInInfoDao;
    static StuInfoDao stuInfoDao;

    static JuiceDatabase juiceDatabase;

    static LiveData<List<OneWeekCourse>> OneWeekCourseLive;
    static LiveData<List<AllWeekCourse>> AllWeekCourseLive;
    static LiveData<List<ClassNoSignedItem>> ClassNoSignedItemLive;
    static LiveData<MyCheckInInfo> MyCheckInInfoLive;
    static LiveData<StuInfo> StuInfoLive;

    @Test
    public void useAppContext() throws ParseException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

//        assertEquals("com.juice.timetable", appContext.getPackageName());
        //存储数据
        List<OneWeekCourse> oneWeekCoursesList = ParseOneWeek.parseCourse();
        List<AllWeekCourse> allWeekCoursesList = ParseAllWeek.parseAllCourse();
        MyCheckInInfo myCheckInInfo = ParseCheckIn.getMySigned();
        List<ClassNoSignedItem> classNoSignedItem = ParseCheckIn.getClassNoSigned();
        StuInfo stuInfo = new StuInfo();
        stuInfo.setStuID(211706162);
        stuInfo.setEduPassword("123456");
        stuInfo.setLeavePassword("123456");
        //生成数据库
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(appContext);

        oneWeekCourseDao = juiceDatabase.getOneCourseDao();
        allWeekCoursesDao = juiceDatabase.getAllCourseDao();
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        myCheckInInfoDao = juiceDatabase.getMyCheckInInfoDao();
        stuInfoDao = juiceDatabase.getStuInfoDao();

        OneWeekCourseLive = oneWeekCourseDao.getOneWeekCourseLive();
        AllWeekCourseLive = allWeekCoursesDao.getAllWeekCourseLive();
        ClassNoSignedItemLive = classNoSignedItemDao.getClassNoSignedItemLive();
        MyCheckInInfoLive = myCheckInInfoDao.getMyCheckInInfoLive();
        StuInfoLive = stuInfoDao.getStuInfoLive();

        for (OneWeekCourse oneWeekCourse : oneWeekCoursesList) {
            LogUtils.getInstance().d("oneWeekCourse"+oneWeekCourse.toString());
            oneWeekCourseDao.insertOneWeekCourse(oneWeekCourse);
        }
        //oneWeekCourseDao.deleteOneWeekCourse();

        for (AllWeekCourse allWeekCourse : allWeekCoursesList) {
            LogUtils.getInstance().d("allWeekCourse" + allWeekCourse.toString());
            allWeekCoursesDao.insertAllWeekCourse(allWeekCourse);
        }
        for (ClassNoSignedItem noSignedItem : classNoSignedItem) {
            LogUtils.getInstance().d("classNoSignedItem" + classNoSignedItem.toString());
            classNoSignedItemDao.insertClassNoSignedItem(noSignedItem);
        }
        myCheckInInfoDao.insertMyCheckInInfo(myCheckInInfo);

        stuInfoDao.insertStuInfo(stuInfo);



    }
}
