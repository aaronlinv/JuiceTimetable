package com.juice.timetable.data.bean;

import java.util.HashSet;
import java.util.List;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CourseViewBean {
    private List<Course> allWeekCourse;
    private HashSet<Integer> weekSet;
    private List<OneWeekCourse> oneWeekCourse;
    private int currentIndex;

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<Course> getAllWeekCourse() {
        return allWeekCourse;
    }

    public void setAllWeekCourse(List<Course> allWeekCourse) {
        this.allWeekCourse = allWeekCourse;
    }

    public HashSet<Integer> getWeekSet() {
        return weekSet;
    }

    public void setWeekSet(HashSet<Integer> weekSet) {
        this.weekSet = weekSet;
    }

    public List<OneWeekCourse> getOneWeekCourse() {
        return oneWeekCourse;
    }

    public void setOneWeekCourse(List<OneWeekCourse> oneWeekCourse) {
        this.oneWeekCourse = oneWeekCourse;
    }

    @Override
    public String toString() {
        return "CourseViewBean{" +
                "allWeekCourse=" + allWeekCourse +
                ", weekSet=" + weekSet +
                ", oneWeekCourse=" + oneWeekCourse +
                ", currentIndex=" + currentIndex +
                '}';
    }
}
