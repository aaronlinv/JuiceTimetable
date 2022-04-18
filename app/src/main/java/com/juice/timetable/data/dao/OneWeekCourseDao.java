package com.juice.timetable.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.OneWeekCourse;

import java.util.ArrayList;
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
    LiveData<List<OneWeekCourse>> getOneWeekCourseLive();

    @Query("Select * From OneWeekCourse")
    List<OneWeekCourse> getOneWeekCourse();

    @Query("Select InWeek from OneWeekCourse")
    LiveData<List<Integer>> getInWeekLive();

    @Query("Select InWeek from OneWeekCourse")
    List<Integer> getInWeek();

    // 删除指定周的课程信息
    @Query("DELETE FROM OneWeekCourse  WHERE InWeek IN (:week)")
    void deleteCourseByWeek(ArrayList<Integer> week);

    // 获取第几周，第几天的课程
    @Query("SELECT * FROM OneWeekCourse WHERE dayOfWeek = :dayOfWeek AND InWeek = :week AND couName NOT LIKE '%停课%'")
    List<OneWeekCourse> getSomeWeek(int dayOfWeek, int week);
}
