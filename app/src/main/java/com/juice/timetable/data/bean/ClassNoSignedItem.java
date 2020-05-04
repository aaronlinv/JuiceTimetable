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
    private Integer CNo;
    //未签名单学号
    private String sno;
    //未签名单名字
    private String sname;

    @Override
    public String toString() {
        return "ClassNoSignrdItem{" +
                ", sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                '}';
    }

    public String getSno() {
        return sno;
    }

    public Integer getCNo() {
        return CNo;
    }

    public void setCNo(Integer CNo) {
        this.CNo = CNo;
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
