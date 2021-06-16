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
public class StuInfo {
    @PrimaryKey
    private Integer stuID;

    private String eduPassword;


    @Override
    public String toString() {
        return "StuInfo{" +
                "stuID=" + stuID +
                ", eduPassword='" + eduPassword + '\'' +
                '}';
    }

    public Integer getStuID() {
        return stuID;
    }

    public void setStuID(Integer stuID) {
        this.stuID = stuID;
    }

    public String getEduPassword() {
        return eduPassword;
    }

    public void setEduPassword(String eduPassword) {
        this.eduPassword = eduPassword;
    }

}
