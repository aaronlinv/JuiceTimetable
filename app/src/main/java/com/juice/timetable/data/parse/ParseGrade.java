package com.juice.timetable.data.parse;

import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.bean.UniGrade;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParseGrade {
    //爬取成绩，一个是统考成绩爬虫，一个是综合成绩的爬虫

    public static List<UniGrade> parseUniGrade(String parseStr) {
        List<UniGrade> uniGradeArrayList = new ArrayList<>();
        //解析统考成绩网页源码
        Document doc = Jsoup.parse(parseStr);
        //爬虫
        Elements rootSelect = doc.select("tbody > tr");

        for (Element ele : rootSelect) {
            Elements all = ele.select("td");
            Element yearAll = all.get(0);           //学年
            String year = yearAll.text();
            if (year.equals("学年")) continue;

            Element courseAll = all.get(1);         //考试项目
            String course = courseAll.text();

            Element gradeAll = all.get(2);          //成绩
            String grade = gradeAll.text();

            Element remarks = all.get(3);           //备注
            String remark = remarks.text();

            UniGrade uniGrade = new UniGrade();
            uniGrade.setuYear(year);
            uniGrade.setuName(course);
            uniGrade.setuGrade(grade);
            uniGrade.setuRemarks(remark);

            uniGradeArrayList.add(uniGrade);
        }

        return uniGradeArrayList;
    }

    public static List<SynGrade> parseSynGrade(String parseStr) {
        List<SynGrade> synGradeArrayList = new ArrayList<>();
        //解析综合成绩网页源码
        Document doc = Jsoup.parse(parseStr);
        //如果存在成绩评测，就直接返回空列表

        Elements rootselect = doc.select("body > table > tbody > tr:nth-child(2) > td > table:nth-child(4) > tbody > tr");

        for (Element ele : rootselect) {
            //然后获得标签里面具体的内容
            Elements all = ele.select("td");

            Element yearAll = all.get(0);           //学年
            String year = yearAll.text();
            if (year.equals("选课 时间")) continue;

            Element courseAll = all.get(1);         // 课程
            String course = courseAll.text();

            Element courseCreditAll = all.get(2);   //课程学分
            String courseCredit = courseCreditAll.text();

            Element gradeAll = all.get(3);          //成绩
            String grade = gradeAll.text();
            String gradePoint = "无";
            String obtainCredit = "无";
            String examType;
            String optionalCourseType;
            if (grade.equals("暂无成绩")) {        //有成绩
                Element examTypeAll = all.get(4);       //考试类型
                examType = examTypeAll.text();

                Element optionalCourseTypeAll = all.get(5); //选修类型
                optionalCourseType = optionalCourseTypeAll.text();
            } else {
                Element gradePointAll = all.get(4);     //绩点
                gradePoint = gradePointAll.text();

                Element obtainCreditAll = all.get(5);   //获得学分
                obtainCredit = obtainCreditAll.text();

                Element examTypeAll = all.get(6);       //考试类型
                examType = examTypeAll.text();

                Element optionalCourseTypeAll = all.get(7); //选修类型
                optionalCourseType = optionalCourseTypeAll.text();
            }


            SynGrade synGrade = new SynGrade();
            synGrade.setCouName(course);
            synGrade.setCouGrade(grade);
            synGrade.setCourseCredit(courseCredit);
            synGrade.setGradePoint(gradePoint);
            synGrade.setCouYear(year);
            synGrade.setGradePoint(gradePoint);
            synGrade.setObtainCredit(obtainCredit);
            synGrade.setExamType(examType);
            synGrade.setOptionalCourseType(optionalCourseType);

            synGradeArrayList.add(synGrade);
        }

        return synGradeArrayList;
    }
}
