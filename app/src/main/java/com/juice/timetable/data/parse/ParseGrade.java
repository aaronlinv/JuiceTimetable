package com.juice.timetable.data.parse;


import com.juice.timetable.data.bean.SynGrade;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class ParseGrade {
    public static List<SynGrade>  parseGrade(String parseStr){
        List<SynGrade> synGradeArrayList = new ArrayList<>();

        Document doc = Jsoup.parse(parseStr);
        System.out.println(doc.getElementsByTag("title").first());


        //临时return，为了不报错
        return synGradeArrayList;
    }
}
