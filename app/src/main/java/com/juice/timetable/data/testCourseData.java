package com.juice.timetable.data;

import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.parse.ParseAllWeek;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class testCourseData {
    // 测试课程数据
    private static List<Course> courses = new ArrayList<>();

    public static List<Course> getCourses() {
        List<Course> courses = ParseAllWeek.parseAllCourse();
        return courses;
    }


}
