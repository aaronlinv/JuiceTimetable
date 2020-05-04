package com.juice.timetable.data.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.AllWeekCourse;

import java.util.List;

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
@Dao
public interface AllWeekCourseDao {
    @Insert
    void insertAllWeekCourse(AllWeekCourse... owc);

    @Query("Delete From AllWeekCourse")
    void deleteAllWeekCourse();

    @Query("Select * From AllWeekCourse ORDER BY couID Desc")
    LiveData<List<AllWeekCourse>> getAllWeekCourseLive();
}
