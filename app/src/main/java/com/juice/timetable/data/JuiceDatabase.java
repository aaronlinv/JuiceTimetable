package com.juice.timetable.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.bean.Credit;
import com.juice.timetable.data.bean.Exam;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.bean.UniGrade;
import com.juice.timetable.data.dao.AllWeekCourseDao;
import com.juice.timetable.data.dao.CreditsDao;
import com.juice.timetable.data.dao.ExamDao;
import com.juice.timetable.data.dao.OneWeekCourseDao;
import com.juice.timetable.data.dao.StuInfoDao;
import com.juice.timetable.data.dao.SynGradeDao;
import com.juice.timetable.data.dao.UniGradeDao;

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
@Database(entities = {OneWeekCourse.class, StuInfo.class, Course.class, SynGrade.class, UniGrade.class, Exam.class, Credit.class}, version = 4, exportSchema = false)
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

    public abstract StuInfoDao getStuInfoDao();

    public abstract AllWeekCourseDao getAllWeekCourseDao();

    public abstract SynGradeDao getSynGradeDao();

    public abstract UniGradeDao getUniGradeDao();

    public abstract ExamDao getExamDao();

    public abstract CreditsDao getCreditsDao();
}
