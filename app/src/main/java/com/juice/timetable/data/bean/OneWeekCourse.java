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
    //课程对应的ID（与完整的课表相同ID的课表）
    private Long couID;
    //课程名字
    private String couName;
    //教室
    private String couRoom;
    //星期几
    private Integer dayOfWeek;
    //单双周
    private Integer typeOfWeek;
    //第几节开始
    private Integer startNode;
    //第几节结束
    private Integer endNode;

    @Override
    public String toString() {
        return "OneWeekCourse{" +
                "couID=" + couID +
                ", couName='" + couName + '\'' +
                ", couRoom='" + couRoom + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", typeOfWeek=" + typeOfWeek +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                '}';
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

    public Integer getTypeOfWeek() {
        return typeOfWeek;
    }

    public void setTypeOfWeek(Integer typeOfWeek) {
        this.typeOfWeek = typeOfWeek;
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
}
