package com.juice.timetable.data.bean;

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
public class ClassNoSignrdItem {
    //未签名单学号
    private String sno;
    //未签名单名字
    private String sname;

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

    @Override
    public String toString() {
        return "ClassNoSignrdItem{" +
                "sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                '}';
    }
}
