package com.juice.timetable.data.parse;


import com.juice.timetable.data.bean.OneWeekCourse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/04/29
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */

public class ParseOneWeek {
    private static List<OneWeekCourse> couList = new ArrayList<>();
    private static Integer week;


    /**
     * 解析周课表
     */
    public static List<OneWeekCourse> parseCourse(String parseStr) {
        //导入
        //String s = ReadFile.readToString("C:\\Users\\14989\\Desktop\\网页内容\\2020网络方向.html");
        //解析
        Document doc = Jsoup.parse(parseStr);

        //表格主干
        Elements elements = doc.getElementsByTag("table").eq(2);
        //System.out.println(elements.html());

        //遍历表格
        for (Element element : elements) {
            //table中tr标签的数量
            Integer len_Tr = element.getElementsByTag("tr").size();
            for (int a = 1; a < len_Tr; a++) {
                Elements ele = element.getElementsByTag("tr").eq(a);
                //System.out.println(ele.html());
                for (Element el : ele) {
                    //tr标签中td的数量
                    Integer len_Td = el.getElementsByTag("td").size();
                    for (int b = 1; b < len_Td; b++) {
                        //Elements ele1 = el.getElementsByTag("td").eq(b);
                        //System.out.println(ele1.html());
                        OneWeekCourse owc = new OneWeekCourse();
                        //去除为空的课程
                        String title = doc.getElementsByClass("td3").text();
                        Integer week = Integer.valueOf(String.valueOf(title.substring(title.indexOf("第") + 1, title.indexOf("周"))));
                        owc.setInWeek(week);
                        if (!"".equals(el.getElementsByTag("td").eq(b).text())) {
                            //couName,couRoom,单双周的判断
                            if (el.getElementsByTag("td").eq(b).text().contains("[单]")) {
                                String couName = el.getElementsByTag("td").eq(b).html().split("<br>")[0];
                                owc.setCouName(couName);
                                owc.setTypeOfWeek(1);
                                String s1 = el.getElementsByTag("td").eq(b).html().split("<br>")[1];
                                String couRoom = s1.substring(4, s1.length() - 1);
                                owc.setCouRoom(couRoom);
                            } else if (el.getElementsByTag("td").eq(b).text().contains("[双]")) {
                                String couName = el.getElementsByTag("td").eq(b).html().split("<br>")[0];
                                owc.setCouName(couName);
                                owc.setTypeOfWeek(2);
                                String s1 = el.getElementsByTag("td").eq(b).html().split("<br>")[1];
                                String couRoom = s1.substring(4, s1.length() - 1);
                                owc.setCouRoom(couRoom);
                            } else {
                                String couName = el.getElementsByTag("td").eq(b).html().split("<br>")[0];
                                owc.setCouName(couName);
                                owc.setTypeOfWeek(0);
                                String s1 = el.getElementsByTag("td").eq(b).html().split("<br>")[1];
                                String couRoom = s1.substring(1, s1.length() - 1);
                                owc.setCouRoom(couRoom);
                            }
                            //标签中的id，例如id为11，第一个1是指从第1节课开始上课，第二个1为星期一
                            String id = el.getElementsByTag("td").eq(b).attr("id");
                            Integer dayOfWeek = Integer.valueOf(id.substring(id.length() - 1, id.length()));
                            owc.setDayOfWeek(dayOfWeek);

                            Integer startNode = Integer.valueOf(id.substring(0, id.length() - 1));
                            owc.setStartNode(startNode);
                            Integer time = Integer.valueOf(el.getElementsByTag("td").eq(b).attr("rowspan"));
                            Integer endNode = startNode + time - 1;
                            owc.setEndNode(endNode);
                            couList.add(owc);
                        }


                    }
                }
            }
            // 解析结束
            /*for (OneWeekCourse course : couList) {
                System.out.println(course);
            }*/

        }
        return couList;
    }

    public static Integer getWeek() {
        return week;
    }

    public static void setWeek(Integer week) {
        ParseOneWeek.week = week;
    }


}