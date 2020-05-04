package com.juice.timetable.data.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.Course;

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
public interface AllWeekCourseDao {
    @Insert
    void insertAllWeekCourse(Course... courses);

    @Query("Delete from Course")
    void deleteAllWeekCourse();

    @Query("Select * from Course")
    List<Course> getAllWeekCourse();
}
