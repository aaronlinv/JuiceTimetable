package com.juice.timetable.utils;

import android.content.Context;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.dao.OneWeekCourseDao;

import java.util.List;

/**
 * Created by yzune on 2018/3/24.
 */

public class DaoUtils {
    //private  static WidgetDaoManager widgetDaoManager;
    public static Context context;
    JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
    OneWeekCourseDao oneWeekCourseDao = juiceDatabase.getOneWeekCourseDao();
    List<OneWeekCourse> oneWeekCourse = oneWeekCourseDao.getOneWeekCourse();

    public static void init(Context context) {
        DaoUtils.context = context.getApplicationContext();
    }

    public static void Table() {

    }

}