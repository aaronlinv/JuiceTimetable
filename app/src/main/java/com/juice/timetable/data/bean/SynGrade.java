package com.juice.timetable.data.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/6 10:20
 * </pre>
 */
//综合成绩(学校考试的成绩)
@Entity
public class SynGrade {
    @PrimaryKey(autoGenerate = true)
    private Integer syngradeId;

    private String couYear;             //选课时间
    private String couName;             //课程名
    private String couGrade;            //课程成绩
    private String courseCredit;        //课程学分
    private String gradePoint;          //绩点
    private String obtainCredit;        //获得学分
    private String examType;            //考试类型
    private String optionalCourseType;  //选修类型

    @Override
    public String toString() {
        return "SynGrade{" +
                "syngradeId=" + syngradeId +
                ", couYear='" + couYear + '\'' +
                ", couName='" + couName + '\'' +
                ", couGrade='" + couGrade + '\'' +
                ", courseCredit='" + courseCredit + '\'' +
                ", gradePoint='" + gradePoint + '\'' +
                ", obtainCredit='" + obtainCredit + '\'' +
                ", examType='" + examType + '\'' +
                ", optionalCourseType='" + optionalCourseType + '\'' +
                '}';
    }


    @NonNull
    public Integer getSyngradeId() {
        return syngradeId;
    }

    public void setSyngradeId(@NonNull Integer syngradeId) {
        this.syngradeId = syngradeId;
    }

    public String getCouYear() {
        return couYear;
    }

    public void setCouYear(String couYear) {
        this.couYear = couYear;
    }

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName;
    }

    public String getCouGrade() {
        return couGrade;
    }

    public void setCouGrade(String couGrade) {
        this.couGrade = couGrade;
    }



    //以下是新加的

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(String gradePoint) {
        this.gradePoint = gradePoint;
    }

    public String getObtainCredit() {
        return obtainCredit;
    }

    public void setObtainCredit(String obtainCredit) {
        this.obtainCredit = obtainCredit;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getOptionalCourseType() {
        return optionalCourseType;
    }

    public void setOptionalCourseType(String optionalCourseType) {
        this.optionalCourseType = optionalCourseType;
    }
}
