package com.juice.timetable.data.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/3/8 0:09
 * </pre>
 */
//统考成绩(四六级等)
@Entity
public class UniGrade {
    @PrimaryKey
    private Integer uniGradeId;

    public String uYear;
    public String uName;
    public String uGrade;
    public String uRemarks;

    public UniGrade(Integer uniGradeId, String uYear, String uName, String uGrade, String uRemarks) {
        this.uniGradeId = uniGradeId;
        this.uYear = uYear;
        this.uName = uName;
        this.uGrade = uGrade;
        this.uRemarks = uRemarks;
    }

    public Integer getUniGradeId() {
        return uniGradeId;
    }

    public void setUniGradeId(Integer uniGradeId) {
        this.uniGradeId = uniGradeId;
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
