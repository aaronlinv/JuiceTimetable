package com.juice.timetable.data.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/6 10:24
 * </pre>
 */
//统考成绩
@Entity
public class UniGrade {

    @PrimaryKey(autoGenerate = true)
    public Integer unigradeId;
    public String uYear;
    public String uName;
    public String uGrade;
    public String uRemarks;

    @Override
    public String toString() {
        return "UniGrade{" +
                "unigradeId=" + unigradeId +
                ", uYear='" + uYear + '\'' +
                ", uName='" + uName + '\'' +
                ", uGrade='" + uGrade + '\'' +
                ", uRemarks='" + uRemarks + '\'' +
                '}';
    }

    public Integer getUnigradeId() {
        return unigradeId;
    }

    public void setUnigradeId(Integer unigradeId) {
        this.unigradeId = unigradeId;
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
