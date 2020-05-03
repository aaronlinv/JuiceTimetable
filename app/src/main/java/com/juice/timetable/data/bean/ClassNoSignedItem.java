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
    @PrimaryKey
    //未签名单学号
    private Integer Sno;
    //未签名单名字
    private String Sname;

    public Integer getSno() {
        return Sno;
    }

    public void setSno(Integer Sno) {
        this.Sno = Sno;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String Sname) {
        this.Sname = Sname;
    }

    @Override
    public String toString() {
        return "ClassNoSignrdItem{" +
                "sno='" + Sno + '\'' +
                ", sname='" + Sname + '\'' +
                '}';
    }
}
