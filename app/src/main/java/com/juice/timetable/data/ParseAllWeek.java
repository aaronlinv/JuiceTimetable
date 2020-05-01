package com.juice.timetable.data;

import com.juice.timetable.utils.ReadFile;

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

        ///将table左边的表格标签里的内容提取（课程名，老师，起始结束周）
        Elements leftTable = document.getElementsByTag("td").eq(1);
        //System.out.println(leftElements.html());
        /*for (Element el : leftTable) {
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
                        System.out.println("授课老师：" + couTeacher);
                    }
                    if (!"".equals(ele.getElementsByTag("td").eq(10).text())) {
                        //System.out.println(ele.getElementsByTag("td").eq(10).text());
                        couStartWeek = Integer.valueOf(ele.getElementsByTag("td").eq(10).text().split("～")[0]);
                        System.out.println("从第" + couStartWeek + "周开始");
                        couEndWeek = Integer.valueOf(ele.getElementsByTag("td").eq(10).text().split("～")[1]);
                        System.out.println("从第" + couEndWeek + "周结束");
                    }

                }
            }
        }*/


        //将table左边的表格标签里的内容提取（）
        Elements rightTable = document.getElementsByTag("tbody").eq(3);
        //System.out.println(rightTable);
        for (Element element1 : rightTable) {
            Integer len_Tr1 = element1.getElementsByTag("tr").size();
            for (int l = 1; l < len_Tr1; l++) {
                Elements ele1 = element1.getElementsByTag("tr").eq(l);
                //System.out.println(ele1.html());
                for (Element el1 : ele1) {

                    Integer len_Td1 = el1.getElementsByTag("td").size();
                    for (int j = 1; j < len_Td1; j++) {
                        //去除为空的课程
                        if (!"".equals(el1.getElementsByTag("td").eq(j).text())) {
                            String tr = el1.getElementsByTag("td").eq(j).html();
                            //tr标签中td的数量
                            //String tr = el1.getElementsByTag("td").html();
                            //System.out.println(e1.html());
                            Integer len_Br = tr.split("<br>").length;
                            //System.out.println(tr.split("<br>")[0]+"======");
                            for (int a = 0; a < len_Br; a++) {
                                if (tr.split("<br>")[a].contains("班")) {
                                    String couname = tr.split("<br>")[a];
                                    /*//使用list对课程名字进行判断，相同的名字存储在同一个list
                                    if(couname.equals(couName)){}*/
                                    System.out.println("课程名字：" + couname + "===");
                                    if (tr.split("<br>")[a + 1].contains("[单]")) {
                                        couWeekType = 1;
                                        System.out.println("单双周:" + couWeekType + "===");
                                        //使用list后，对是否已经输入过教室进行判断，无则输入，有则重新开一个list存储
                                        couRoom = tr.split("<br>")[a + 1].substring(4, tr.split("<br>")[a + 1].length() - 1);
                                        System.out.println("教室:" + couRoom + "===");
                                    } else if (tr.split("<br>")[a + 1].contains("[双]")) {
                                        couWeekType = 2;
                                        System.out.println("单双周:" + couWeekType + "===");
                                        //使用list后，对是否已经输入过教室进行判断，无则输入，有则重新开一个list存储
                                        couRoom = tr.split("<br>")[a + 1].substring(4, tr.split("<br>")[a + 1].length() - 1);
                                        System.out.println("教室:" + couRoom + "===");
                                    } else {
                                        couWeekType = 0;
                                        System.out.println("单双周:" + couWeekType + "===");
                                        //使用list后，对是否已经输入过教室进行判断，无则输入，有则重新开一个list存储
                                        couRoom = tr.split("<br>")[a + 1].substring(1, tr.split("<br>")[a + 1].length() - 1);
                                        System.out.println("教室:" + couRoom + "===");
                                    }
                                    String id = el1.getElementsByTag("td").eq(j).attr("id");
                                    couWeek = Integer.valueOf(id.substring(id.length() - 1, id.length()));
                                    System.out.println("星期" + couWeek);
                                    couStartNode = Integer.valueOf(id.substring(0, id.length() - 1));
                                    System.out.println("从第" + couStartNode + "节课开始");
                                    Integer time = Integer.valueOf(el1.getElementsByTag("td").eq(j).attr("rowspan"));
                                    couEndNode = couStartNode + time - 1;
                                    System.out.println("从第" + couEndNode + "节课结束");
                                }


                            }

                        }


                    }
                }
            }
        }
    }

    public Long getCouID() {
        return couID;
    }

    public void setCouID(Long couID) {
        this.couID = couID;
    }

    public static String getCouName() {
        return couName;
    }

    public static void setCouName(String couName) {
        ParseAllWeek.couName = couName;
    }

    public static String getCouRoom() {
        return couRoom;
    }

    public static void setCouRoom(String couRoom) {
        ParseAllWeek.couRoom = couRoom;
    }

    public static String getCouTeacher() {
        return couTeacher;
    }

    public static void setCouTeacher(String couTeacher) {
        ParseAllWeek.couTeacher = couTeacher;
    }

    public static Integer getCouWeek() {
        return couWeek;
    }

    public static void setCouWeek(Integer couWeek) {
        ParseAllWeek.couWeek = couWeek;
    }

    public static Integer getCouStartNode() {
        return couStartNode;
    }

    public static void setCouStartNode(Integer couStartNode) {
        ParseAllWeek.couStartNode = couStartNode;
    }

    public static Integer getCouEndNode() {
        return couEndNode;
    }

    public static void setCouEndNode(Integer couEndNode) {
        ParseAllWeek.couEndNode = couEndNode;
    }

    public static Integer getCouWeekType() {
        return couWeekType;
    }

    public static void setCouWeekType(Integer couWeekType) {
        ParseAllWeek.couWeekType = couWeekType;
    }

    public static Integer getCouStartWeek() {
        return couStartWeek;
    }

    public static void setCouStartWeek(Integer couStartWeek) {
        ParseAllWeek.couStartWeek = couStartWeek;
    }

    public static Integer getCouEndWeek() {
        return couEndWeek;
    }

    public static void setCouEndWeek(Integer couEndWeek) {
        ParseAllWeek.couEndWeek = couEndWeek;
    }

    public Integer getCouColor() {
        return couColor;
    }

    public void setCouColor(Integer couColor) {
        this.couColor = couColor;
    }
}
