package com.juice.timetable.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/04/28
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */

public class ParseAllWeek {
    //课程的对应ID
    private Long couID;
    //课程名
    private static String couName;
    //教室
    private static String couRoom;
    //授课老师
    private static String couTeacher;
    // 这门课是在星期几上
    private static Integer couWeek;
    // 这门课是从当天的第几节课开始
    private static Integer couStartNode;
    // 这门课是从当天的第几节课结束
    private static Integer couEndNode;
    // 单双周的判断
    private static Integer couWeekType;
    // 这门课程开始于第几周
    private static Integer couStartWeek;
    // 这门课结束于第几周
    private static Integer couEndWeek;
    // 课程使用的颜色
    private Integer couColor;
    /**
     * 解析完整的课表
     */
    public static void parseAllCourse() {
        //从文档中导入完整课表txt
        String i = ReadFile.readToString("C:\\Users\\14989\\Desktop\\网页内容\\完整课表.html");
        //Jsoup解析
        Document document = Jsoup.parse(i);
        //从div中提取出文本，内容是课表与名字
        String title = document.getElementsByTag("div").eq(0).text();
        System.out.println(title);

        //将table左边的表格标签里的内容提取（课程名，老师，起始结束周）
        Elements leftElements = document.getElementsByTag("td").eq(1);
        //System.out.println(leftElements.html());
        for (Element el : leftElements) {
            Integer len_Tr = el.getElementsByTag("tr").size();
            //System.out.println(len_Tr);
            for (int a = 2; a < len_Tr; a++) {
                Elements oneCourse = el.getElementsByTag("tr").eq(a);
                //System.out.println(oneCourse.html());
                for (Element ele : oneCourse) {
                    if (ele.getElementsByTag("td").size() < 3) {
                        break;
                    }
                    couName = ele.getElementsByTag("td").eq(0).text();
                    System.out.println(couName);
                    //
                    if (!"".equals(ele.getElementsByTag("td").eq(2).text())) {
                        couTeacher = ele.getElementsByTag("td").eq(2).text();
                        System.out.println(couTeacher);
                    }
                    if (!"".equals(ele.getElementsByTag("td").eq(10).text())) {
                        System.out.println(ele.getElementsByTag("td").eq(10).text());
                        couStartWeek = Integer.valueOf(ele.getElementsByTag("td").eq(10).text().split("～")[0]);
                        System.out.println(couStartWeek);
                        couEndWeek = Integer.valueOf(ele.getElementsByTag("td").eq(10).text().split("～")[1]);
                        System.out.println(couEndWeek);
                    }

                }
            }
        }


        //将table左边的表格标签里的内容提取（）
        Elements rightElements = document.getElementsByTag("td").eq(2);
        for (Element el : rightElements) {

        }
    }


}
