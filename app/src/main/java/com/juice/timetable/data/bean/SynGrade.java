package com.juice.timetable.data.bean;

import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/6 10:20
 * </pre>
 */
//综合成绩
public class SynGrade {
    @PrimaryKey(autoGenerate = true)
    private Integer couID;

    private String couName;
    private String couGrade;

    @Override
    public String toString() {
        return "SynGrade{" +
                "couID=" + couID +
                ", couName='" + couName + '\'' +
                ", couGrade='" + couGrade + '\'' +
                '}';
    }

    public Integer getCouID() {
        return couID;
    }

    public void setCouID(Integer couID) {
        this.couID = couID;
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
