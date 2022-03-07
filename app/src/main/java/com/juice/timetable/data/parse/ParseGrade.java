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

        int uniGradeId = 0;

        for (Element ele : rootSelect) {
            Elements all = ele.select("td");
            Element uYearAll = all.get(0);           //学年
            String uYear = uYearAll.text();
            if (uYear.equals("学年")) continue;

            Element uNameAll = all.get(1);         //考试项目
            String uName = uNameAll.text();

            Element uGradeAll = all.get(2);          //成绩
            String uGrade = uGradeAll.text();

            Element uRemarkAll = all.get(3);           //备注
            String uRemark = uRemarkAll.text();

            UniGrade uniGrade = new UniGrade(uniGradeId++, uYear, uName, uGrade, uRemark);

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

        int synGradeId = 0;

        for (Element ele : rootselect) {
            //然后获得标签里面具体的内容
            Elements all = ele.select("td");

            Element couYearAll = all.get(0);           //学年
            String couYear = couYearAll.text();
            if (couYear.equals("选课 时间")) continue;

            Element courseNameAll = all.get(1);         // 课程
            String courseName = courseNameAll.text();

            Element courseCreditAll = all.get(2);   //课程学分
            String courseCredit = courseCreditAll.text();

            Element cougradeAll = all.get(3);          //成绩
            String couGrade = cougradeAll.text();
            String gradePoint = "无";
            String obtainCredit = "无";
            String examType;
            String optionalCourseType;
            if (couGrade.equals("暂无成绩")) {        //有成绩
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

            SynGrade synGrade = new SynGrade(synGradeId++, couYear, courseName, couGrade, courseCredit,
                    gradePoint, obtainCredit, examType, optionalCourseType);

            synGradeArrayList.add(synGrade);
        }

        return synGradeArrayList;
    }
}
