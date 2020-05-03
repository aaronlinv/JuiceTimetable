package com.juice.timetable.data.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/02
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
@Entity
public class MyCheckInInfo {
    @PrimaryKey(autoGenerate = true)
    private Long No;
    //确认签到
    private boolean isCheckIn;
    //签到时间
    private String checkTime;

    @Override
    public String toString() {
        return "MyCheckInInfo{" +
                "No=" + No +
                ", isCheckIn=" + isCheckIn +
                ", checkTime='" + checkTime + '\'' +
                '}';
    }

    public Long getNo() {
        return No;
    }

    public void setNo(Long no) {
        No = no;
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
