package com.juice.timetable.data.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/3/15 12:10
 * </pre>
 */
@Entity
public class Credit {
    @PrimaryKey
    private int creditID;

    private String optionalCourseTypeCredits; //学分选修类型
    private String coursesCompletedCredits; //已修课程学分总计
    private String takeHomeCredits; //实得学分总计

    public Credit(int creditID, String optionalCourseTypeCredits, String coursesCompletedCredits, String takeHomeCredits) {
        this.creditID = creditID;
        this.optionalCourseTypeCredits = optionalCourseTypeCredits;
        this.coursesCompletedCredits = coursesCompletedCredits;
        this.takeHomeCredits = takeHomeCredits;
    }

    public int getCreditID() {
        return creditID;
    }

    public void setCreditID(int creditID) {
        this.creditID = creditID;
    }

    public String getOptionalCourseTypeCredits() {
        return optionalCourseTypeCredits;
    }

    public void setOptionalCourseTypeCredits(String optionalCourseTypeCredits) {
        this.optionalCourseTypeCredits = optionalCourseTypeCredits;
    }

    public String getCoursesCompletedCredits() {
        return coursesCompletedCredits;
    }

    public void setCoursesCompletedCredits(String coursesCompletedCredits) {
        this.coursesCompletedCredits = coursesCompletedCredits;
    }

    public String getTakeHomeCredits() {
        return takeHomeCredits;
    }

    public void setTakeHomeCredits(String takeHomeCredits) {
        this.takeHomeCredits = takeHomeCredits;
    }
}
