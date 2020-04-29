package com.juice.timetable.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
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
    //第几节开始
    private static Integer startNode;
    //第几节结束
    private static Integer endNode;

    /**
     * 解析周课表
     */
    public static void parseCourse() {
        //导入
        String s = ReadFile.readToString("C:\\Users\\14989\\Desktop\\网页内容\\课表.html");
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
                        Elements ele1 = el.getElementsByTag("td").eq(b);
                        //System.out.println(ele1.html());

                        //去除为空的课程
                        if (!"".equals(el.getElementsByTag("td").eq(b).text())) {
                            //couName
                            couName = el.getElementsByTag("td").eq(b).html().split("<br>")[0];
                            System.out.println(couName);
                            couRoom = el.getElementsByTag("td").eq(b).html().split("<br>")[1];
                            System.out.println("教室：" + couRoom);
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
}