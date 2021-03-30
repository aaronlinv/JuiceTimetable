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
public class OneWeekCourse {
    @PrimaryKey(autoGenerate = true)
    private Integer onlyID;

    //课程对应的ID（与完整的课表相同ID的课表）
    private Long couID;

    private String couName;

    private String couRoom;

    private Integer dayOfWeek;

    private Integer startNode;

    private Integer endNode;

    private Integer InWeek;

    private Integer CourseType;

    private Integer Color;

    public Integer getOnlyID() {
        return onlyID;
    }

    public void setOnlyID(Integer onlyID) {
        this.onlyID = onlyID;
    }

    public Long getCouID() {
        return couID;
    }

    public void setCouID(Long couID) {
        this.couID = couID;
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

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getStartNode() {
        return startNode;
    }

    public void setStartNode(Integer startNode) {
        this.startNode = startNode;
    }

    public Integer getEndNode() {
        return endNode;
    }

    public void setEndNode(Integer endNode) {
        this.endNode = endNode;
    }

    public Integer getInWeek() {
        return InWeek;
    }

    public void setInWeek(Integer inWeek) {
        InWeek = inWeek;
    }

    public Integer getCourseType() {
        return CourseType;
    }

    public void setCourseType(Integer courseType) {
        CourseType = courseType;
    }

    public Integer getColor() {
        return Color;
    }

    public void setColor(Integer color) {
        Color = color;
    }

    @Override
    public String toString() {
        return "OneWeekCourse{" +
                "onlyID=" + onlyID +
                ", couID=" + couID +
                ", couName='" + couName + '\'' +
                ", couRoom='" + couRoom + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                ", InWeek=" + InWeek +
                ", CourseType=" + CourseType +
                ", Color=" + Color +
                '}';
    }
}

