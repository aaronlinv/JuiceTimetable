package com.juice.timetable.data.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/6 10:20
 * </pre>
 */
//综合成绩
@Entity
public class SynGrade {
    @NonNull
    @PrimaryKey
    private String couYear;

    private String couName;
    private String couGrade;

    @Override
    public String toString() {
        return "SynGrade{" +
                "couYear='" + couYear + '\'' +
                ", couName='" + couName + '\'' +
                ", couGrade='" + couGrade + '\'' +
                '}';
    }

    public String getCouYear() {
        return couYear;
    }

    public void setCouYear(String couYear) {
        this.couYear = couYear;
    }

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName;
    }

    public String getCouGrade() {
        return couGrade;
    }

    public void setCouGrade(String couGrade) {
        this.couGrade = couGrade;
    }
}
