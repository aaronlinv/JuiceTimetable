package com.juice.timetable.data.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/01
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
@Entity
public class ClassNoSignedItem {
    @PrimaryKey(autoGenerate = true)
    private Integer No;
    //未签名单学号
    private String sno;
    //未签名单名字
    private String sname;

    @Override
    public String toString() {
        return "ClassNoSignrdItem{" +
                "No=" + No +
                ", sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                '}';
    }

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
