package com.juice.timetable.data.parse;

import com.juice.timetable.utils.ReadFile;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    //课程对应的ID（与完整的课表相同ID的课表）
    private static Long couID;
    //课程名字
    private static String couName;
    //教室
    private static String couRoom;
    //第几个星期
    private static Integer week;
    //星期几
    private static Integer dayOfWeek;
    //单双周
    private static Integer typeOfWeek;
    //第几节开始
    private static Integer startNode;
    //第几节结束
    private static Integer endNode;

    /**
     * 解析周课表
     */
    public static void parseCourse() {
        //导入
        String s = ReadFile.readToString("C:\\Users\\14989\\Desktop\\网页内容\\2020网络方向.html");
        //解析
        Document doc = Jsoup.parse(s);
        //标题
        String title = doc.getElementsByClass("td3").text();
        week = Integer.valueOf(String.valueOf(title.substring(title.indexOf("第") + 1, title.indexOf("周"))));
        System.out.println("第" + week + "周");
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

                        //去除为空的课程
                        if (!"".equals(el.getElementsByTag("td").eq(b).text())) {
                            //couName,couRoom,单双周的判断
                            if (el.getElementsByTag("td").eq(b).text().contains("[单]")) {
                                couName = el.getElementsByTag("td").eq(b).html().split("<br>")[0];
                                System.out.println(couName);
                                typeOfWeek = 1;
                                System.out.println(typeOfWeek);
                                String s1 = el.getElementsByTag("td").eq(b).html().split("<br>")[1];
                                couRoom = s1.substring(4, s1.length() - 1);
                                System.out.println("教室：" + couRoom);
                            } else if (el.getElementsByTag("td").eq(b).text().contains("[双]")) {
                                couName = el.getElementsByTag("td").eq(b).html().split("<br>")[0];
                                System.out.println(couName);
                                typeOfWeek = 2;
                                System.out.println(typeOfWeek);
                                String s1 = el.getElementsByTag("td").eq(b).html().split("<br>")[1];
                                couRoom = s1.substring(4, s1.length() - 1);
                                System.out.println("教室：" + couRoom);
                            } else {
                                couName = el.getElementsByTag("td").eq(b).html().split("<br>")[0];
                                System.out.println(couName);
                                typeOfWeek = 0;
                                System.out.println(typeOfWeek);
                                String s1 = el.getElementsByTag("td").eq(b).html().split("<br>")[1];
                                couRoom = s1.substring(1, s1.length() - 1);
                                System.out.println("教室：" + couRoom);
                            }
                            //标签中的id，例如id为11，第一个1是指从第1节课开始上课，第二个1为星期一
                            String id = el.getElementsByTag("td").eq(b).attr("id");
                            dayOfWeek = Integer.valueOf(id.substring(id.length() - 1, id.length()));
                            System.out.println("星期" + dayOfWeek);
                            startNode = Integer.valueOf(id.substring(0, id.length() - 1));
                            System.out.println("从第" + startNode + "节课开始");
                            Integer time = Integer.valueOf(el.getElementsByTag("td").eq(b).attr("rowspan"));
                            endNode = startNode + time - 1;
                            System.out.println("从第" + endNode + "节课结束");
                        }


                    }
                }
            }

        }
    }

    public static Long getCouID() {
        return couID;
    }

    public static void setCouID(Long couID) {
        ParseOneWeek.couID = couID;
    }

    public static String getCouName() {
        return couName;
    }

    public static void setCouName(String couName) {
        ParseOneWeek.couName = couName;
    }

    public static String getCouRoom() {
        return couRoom;
    }

    public static void setCouRoom(String couRoom) {
        ParseOneWeek.couRoom = couRoom;
    }

    public static Integer getWeek() {
        return week;
    }

    public static void setWeek(Integer week) {
        ParseOneWeek.week = week;
    }

    public static Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public static void setDayOfWeek(Integer dayOfWeek) {
        ParseOneWeek.dayOfWeek = dayOfWeek;
    }

    public static Integer getTypeOfWeek() {
        return typeOfWeek;
    }

    public static void setTypeOfWeek(Integer typeOfWeek) {
        ParseOneWeek.typeOfWeek = typeOfWeek;
    }

    public static Integer getStartNode() {
        return startNode;
    }

    public static void setStartNode(Integer startNode) {
        ParseOneWeek.startNode = startNode;
    }

    public static Integer getEndNode() {
        return endNode;
    }

    public static void setEndNode(Integer endNode) {
        ParseOneWeek.endNode = endNode;
    }
}