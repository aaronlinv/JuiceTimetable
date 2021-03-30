package com.juice.timetable.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.AllWeekCourseDao;
import com.juice.timetable.data.dao.ClassNoSignedItemDao;
import com.juice.timetable.data.dao.MyCheckInDao;
import com.juice.timetable.data.dao.OneWeekCourseDao;
import com.juice.timetable.data.dao.StuInfoDao;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/04
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
@Database(entities = {OneWeekCourse.class, ClassNoSignedItem.class, MyCheckIn.class, StuInfo.class, Course.class}, version = 1, exportSchema = false)
public abstract class JuiceDatabase extends RoomDatabase {
    private static JuiceDatabase INSTANCE;

    public static synchronized JuiceDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), JuiceDatabase.class, "juice_Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract OneWeekCourseDao getOneWeekCourseDao();

    public abstract ClassNoSignedItemDao getClassNoSignedItemDao();

    public abstract MyCheckInDao getMyCheckInDao();

    public abstract StuInfoDao getStuInfoDao();

    public abstract AllWeekCourseDao getAllWeekCourseDao();
}
