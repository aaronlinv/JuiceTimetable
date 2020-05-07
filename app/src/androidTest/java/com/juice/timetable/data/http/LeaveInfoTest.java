package com.juice.timetable.data.http;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.Dao.AllWeekCourseDao;
import com.juice.timetable.data.Dao.ClassNoSignedItemDao;
import com.juice.timetable.data.Dao.MyCheckInDao;
import com.juice.timetable.data.Dao.OneWeekCourseDao;
import com.juice.timetable.data.Dao.StuInfoDao;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.parse.ParseCheckIn;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.UserInfoUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LeaveInfoTest {

    private com.juice.timetable.data.Dao.OneWeekCourseDao CourseDao;
    private ClassNoSignedItemDao classNoSignedItemDao;
    private MyCheckInDao myCheckInDao;
    private StuInfoDao stuInfoDao;
    private AllWeekCourseDao allWeekCourseDao;
    private OneWeekCourseDao OneWeekCourseDao;

    @Before
    public void initDataBase() {
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
        OneWeekCourseDao = juiceDatabase.getOneWeekCourseDao();
    }

    public Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void getCheckIn() throws Exception {
        // 先写入账号密码信息
        StuInfo stu = new StuInfo();
        UserInfoUtils user = UserInfoUtils.getINSTANT(getContext());
        stu.setStuID(Integer.valueOf(user.getID()));
        stu.setEduPassword(user.getEduPasswd());
        stu.setLeavePassword(user.getLeavePasswd());

        // 更新数据库
        stuInfoDao.deleteStuInfo();
        stuInfoDao.insertStuInfo(stu);

        StuInfo stuInfo = stuInfoDao.getStuInfo();
        LogUtils.getInstance().d("User数据：" + stuInfo);
        String checkIn = LeaveInfo.getCheckIn(getContext());
        if (checkIn != null) {
            MyCheckIn mySigned = ParseCheckIn.getMySigned(checkIn);
            LogUtils.getInstance().d(mySigned.toString());

        }
    }
}