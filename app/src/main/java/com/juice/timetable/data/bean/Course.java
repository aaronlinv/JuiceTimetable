package com.juice.timetable.data.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Entity
public class Course {
    @PrimaryKey
    private Long couID;
    private Long onlyID;
    private String couName;
    private String couRoom;
    private String couTeacher;

    // 这门课是在星期几上
    private Integer couWeek;

    // 这门课是从当天的第几节课开始
    private Integer couStartNode;
    // 这门课是从当天的第几节课结束
    private Integer couEndNode;

    // 单双周的判断
    private Integer couWeekType;
    // 这门课程开始于第几周
    private Integer couStartWeek;
    // 这门课结束于第几周
    private Integer couEndWeek;

    private Integer couColor;

    public Course() {
    }

    // 课程名，老师，起始结束周
    public Course(String couName, String couTeacher, Integer couStartWeek, Integer couEndWeek) {
        this.couName = couName;
        this.couTeacher = couTeacher;
        this.couStartWeek = couStartWeek;
        this.couEndWeek = couEndWeek;
    }

    public Long getCouID() {
        return couID;
    }

    public void setCouID(Long couID) {
        this.couID = couID;
    }

    public Long getOnlyID() {
        return onlyID;
    }

    public void setOnlyID(Long onlyID) {
        this.onlyID = onlyID;
    }

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName;
    }

    public String getCouRoom() {
        return couRoom;
    }

    public void setCouRoom(String couRoom) {
        this.couRoom = couRoom;
    }

    public String getCouTeacher() {
        return couTeacher;
    }

    public void setCouTeacher(String couTeacher) {
        this.couTeacher = couTeacher;
    }

    public Integer getCouWeek() {
        return couWeek;
    }

    public void setCouWeek(Integer couWeek) {
        this.couWeek = couWeek;
    }

    public Integer getCouStartNode() {
        return couStartNode;
    }

    public void setCouStartNode(Integer couStartNode) {
        this.couStartNode = couStartNode;
    }

    public Integer getCouEndNode() {
        return couEndNode;
    }

    public void setCouEndNode(Integer couEndNode) {
        this.couEndNode = couEndNode;
    }

    public Integer getCouWeekType() {
        return couWeekType;
    }

    public void setCouWeekType(Integer couWeekType) {
        this.couWeekType = couWeekType;
    }

    public Integer getCouStartWeek() {
        return couStartWeek;
    }

    public void setCouStartWeek(Integer couStartWeek) {
        this.couStartWeek = couStartWeek;
    }

    public Integer getCouEndWeek() {
        return couEndWeek;
    }

    public void setCouEndWeek(Integer couEndWeek) {
        this.couEndWeek = couEndWeek;
    }

    public Integer getCouColor() {
        return couColor;
    }

    public void setCouColor(Integer couColor) {
        this.couColor = couColor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "couID=" + couID +
                ", onlyID=" + onlyID +
                ", couName='" + couName + '\'' +
                ", couRoom='" + couRoom + '\'' +
                ", couTeacher='" + couTeacher + '\'' +
                ", couWeek=" + couWeek +
                ", couStartNode=" + couStartNode +
                ", couEndNode=" + couEndNode +
                ", couWeekType=" + couWeekType +
                ", couStartWeek=" + couStartWeek +
                ", couEndWeek=" + couEndWeek +
                ", couColor=" + couColor +
                '}';
    }
}
