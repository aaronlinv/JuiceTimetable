package com.juice.timetable.data.bean;

import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/6 10:24
 * </pre>
 */
//统考成绩
public class UniGrade {
    @PrimaryKey
    private String uYear;

    private String uName;
    private String uGrade;
    private String uRemarks;

    @Override
    public String toString() {
        return "UniGrade{" +
                "uYear='" + uYear + '\'' +
                ", uName='" + uName + '\'' +
                ", uGrade='" + uGrade + '\'' +
                ", uRemarks='" + uRemarks + '\'' +
                '}';
    }

    public String getuYear() {
        return uYear;
    }

    public void setuYear(String uYear) {
        this.uYear = uYear;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuGrade() {
        return uGrade;
    }

    public void setuGrade(String uGrade) {
        this.uGrade = uGrade;
    }

    public String getuRemarks() {
        return uRemarks;
    }

    public void setuRemarks(String uRemarks) {
        this.uRemarks = uRemarks;
    }
}
