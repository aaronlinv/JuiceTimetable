package com.juice.timetable;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.Dao.AllWeekCourseDao;
import com.juice.timetable.data.Dao.ClassNoSignedItemDao;
import com.juice.timetable.data.Dao.MyCheckInDao;
import com.juice.timetable.data.Dao.OneWeekCourseDao;
import com.juice.timetable.data.Dao.StuInfoDao;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.parse.ParseAllWeek;
import com.juice.timetable.data.parse.ParseCheckIn;
import com.juice.timetable.data.parse.ParseClassNoSignedItem;
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
    private LiveData<List<OneWeekCourse>> courseLive;
    private List<ClassNoSignedItem> classNoSignedItems;
    private MyCheckIn myCheckIn;
    private StuInfo stuInfo;
    private LiveData<List<Course>> allWeekCourseLive;

    private OneWeekCourseDao CourseDao;
    private ClassNoSignedItemDao classNoSignedItemDao;
    private MyCheckInDao myCheckInDao;
    private StuInfoDao stuInfoDao;
    private AllWeekCourseDao allWeekCourseDao;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.juice.timetable", appContext.getPackageName());
        //生成数据库，如果数据库已有，则调用
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(appContext);
        //生成对应的Dao
        CourseDao = juiceDatabase.getOneWeekCourseDao();
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        myCheckInDao = juiceDatabase.getMyCheckInDao();
        stuInfoDao = juiceDatabase.getStuInfoDao();
        allWeekCourseDao = juiceDatabase.getAllWeekCourseDao();
        //插入数据
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
        List<Course> c = ParseAllWeek.parseAllCourse();
        for (Course course : c) {
            allWeekCourseDao.insertAllWeekCourse(course);
        }
    }

    // 获取数据
    @Test
    public void Query() {
        // 初始化
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //生成数据库，如果数据库已有，则调用
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(appContext);
        //生成对应的Dao
        CourseDao = juiceDatabase.getOneWeekCourseDao();
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        myCheckInDao = juiceDatabase.getMyCheckInDao();
        stuInfoDao = juiceDatabase.getStuInfoDao();
        allWeekCourseDao = juiceDatabase.getAllWeekCourseDao();

        //输出数据


        // 调用observe方法来获取ViewModel里的数据(请在UI线程里使用，在这里没有用，只是注释给你看)
        /*OneWeekCourseViewModel oneWeekCourseViewModel = new ViewModelProvider(this).get(OneWeekCourseViewModel.class);
        LiveData<List<OneWeekCourse>> oneWeekCourseLive = oneWeekCourseViewModel.getOneWeekCourseLive();
        oneWeekCourseLive.observe(requireActivity(), new Observer<List<OneWeekCourse>>() {
            @Override
            public void onChanged(List<OneWeekCourse> oneWeekCourses) {
                if(oneWeekCourses!=null){
                    LogUtils.getInstance().d("读取数据"+oneWeekCourses);
                }
            }
        });*/


        //非ViewModel的调用在这里↓
        LogUtils.getInstance().d("myCheckIn数据：");
        myCheckIn = myCheckInDao.getMyCheckIn();
        LogUtils.getInstance().d(myCheckIn.toString());

        LogUtils.getInstance().d("stuInfo数据：");
        stuInfo = stuInfoDao.getStuInfo();
        LogUtils.getInstance().d(stuInfo.toString());


    }
}
