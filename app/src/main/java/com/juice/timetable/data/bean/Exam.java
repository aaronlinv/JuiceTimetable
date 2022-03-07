package com.juice.timetable.data.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/1/1 19:33
 * </pre>
 */
@Entity
public class Exam {
    @PrimaryKey
    private Integer examId;

    private String semester;        //开课学期
    private String courseName;      //课程名称
    private String examType;        //考试类型
    private String examTime;        //考试时间
    private String examCategory;    //考试类别
    private String arrangement;     //考场安排
    private String classGrade;      //班级


    public Exam(Integer examId, String semester, String courseName, String examType, String examTime, String examCategory, String arrangement, String classGrade) {
        this.examId = examId;
        this.semester = semester;
        this.courseName = courseName;
        this.examType = examType;
        this.examTime = examTime;
        this.examCategory = examCategory;
        this.arrangement = arrangement;
        this.classGrade = classGrade;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    public String getArrangement() {
        return arrangement;
    }

    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    public String getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }
}
