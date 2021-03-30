package com.juice.timetable.data.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
@Entity
public class MyCheckIn {
    @PrimaryKey
    private Integer Cno;
    private boolean isCheckIn;
    private String checkTime;

    @Override
    public String toString() {
        return "MyCheckIn{" +
                "Cno=" + Cno +
                ", isCheckIn=" + isCheckIn +
                ", checkTime='" + checkTime + '\'' +
                '}';
    }

    public Integer getCno() {
        return Cno;
    }

    public void setCno(Integer cno) {
        Cno = cno;
    }

    public boolean isCheckIn() {
        return isCheckIn;
    }

    public void setCheckIn(boolean checkIn) {
        isCheckIn = checkIn;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }
}
