package com.juice.timetable.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/02
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
@Database(entities = {OneWeekCourse.class, AllWeekCourse.class, ClassNoSignedItem.class, MyCheckInInfo.class, StuInfo.class}, version = 1, exportSchema = false)
public abstract class JuiceDatabase extends RoomDatabase {
    private static JuiceDatabase INSTANCE;

    public static JuiceDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), JuiceDatabase.class,"Juice_Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract OneWeekCourseDao getOneCourseDao();

    public abstract AllWeekCourseDao getAllCourseDao();

    public abstract ClassNoSignedItemDao getClassNoSignedItemDao();

    public abstract MyCheckInInfoDao getMyCheckInInfoDao();

    public abstract StuInfoDao getStuInfoDao();
}
