package com.juice.timetable.data.parse;

import com.juice.timetable.data.bean.Exam;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/1/2 9:50
 * </pre>
 */
public class ParseExam {
    public static List<Exam> parseExam(String parseStr) {
        List<Exam> examArrayList = new ArrayList<>();
        //解析统考成绩网页源码
        Document doc = Jsoup.parse(parseStr);

        Elements rootSelect = doc.select("body > table > tbody > tr:nth-child(4) > td > table > tbody > tr");

        int examId = 0;

        for (Element ele : rootSelect) {
            Elements all = ele.select("td");

            Element semesterAll = all.get(0);               //开课学期
            String semester = semesterAll.text();
            if (semester.equals("开课 学期")) continue;

            Element courseNameAll = all.get(1);             //课程名称
            String courseName = courseNameAll.text();

            Element examTypeAll = all.get(2);               //考试类型
            String examType = examTypeAll.text();

            Element examCategoryAll = all.get(3);           //考试类别
            String examCategory = examCategoryAll.text();

            Element examTimeAll = all.get(4);               //考试时间
            String examTime = examTimeAll.text();

            Element arrangementAll = all.get(5);
            String arrangement = arrangementAll.text();     //考场安排

            Element classGradeAll = all.get(6);
            String classGrade = classGradeAll.text();       //班级

            Exam exam = new Exam(examId++, semester, courseName, examType, examTime, examCategory,
                    arrangement, classGrade);

            examArrayList.add(exam);
        }

        return examArrayList;
    }

}
