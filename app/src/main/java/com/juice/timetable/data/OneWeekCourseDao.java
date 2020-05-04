package com.juice.timetable.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.OneWeekCourse;

import java.util.List;

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
@Dao
public interface OneWeekCourseDao {
    @Insert
    void insertCourse(OneWeekCourse... owc);

    @Query("Delete From OneWeekCourse")
    void deleteCourse();

    @Query("Select * From OneWeekCourse")
    List<OneWeekCourse> getOneWeekCourseLive();
}
