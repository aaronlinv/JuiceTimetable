package com.juice.timetable.data.parse;

import com.juice.timetable.data.bean.Course;

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
 *     time   : 2020/04/28
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */

public class ParseAllWeek {
    private static List<Course> couList = new ArrayList<>();
    private static List<Course> courseList = new ArrayList<>();
    private static Integer onlyID = 0;

    /**
     * 解析完整的课表
     */
    public static List<Course> parseAllCourse(String parseData) {
        Long couID = 0L;
        //从文档中导入完整课表txt
//        String s = getAllCourseStr2018();
        //String s = ReadFile.readToString("C:\\Users\\14989\\Desktop\\网页内容\\完整课表.html");
        //Jsoup解析
        Document document = Jsoup.parse(parseData);
        //从div中提取出文本，内容是课表与名字
        //String title = document.getElementsByTag("div").eq(0).text();
        //System.out.println(title);


        ///将table左边的表格标签里的内容提取（课程名，老师，起始结束周）
        Elements leftTable = document.getElementsByTag("td").eq(1);


        for (Element el : leftTable) {
            int len_Tr = el.getElementsByTag("tr").size();
            //System.out.println(len_Tr);
            for (int a = 1; a < len_Tr; a++) {
                Elements oneCourse = el.getElementsByTag("tr").eq(a);
                //System.out.println(oneCourse.html());
                for (Element ele : oneCourse) {
                    Course course = new Course();
                    if (ele.getElementsByTag("td").size() < 3) {
                        break;
                    }
                    String couName = ele.getElementsByTag("td").eq(0).text();

                    course.setCouName(couName);
                    //
                    if (!"".equals(ele.getElementsByTag("td").eq(2).text())) {
                        String couTeacher = ele.getElementsByTag("td").eq(2).text();
                        course.setCouTeacher(couTeacher);
                    }
                    // 解析 起讫时间周序(星期)
                    // bug
                    // 可能出现 <td align="center">01～01<br>03～03</td>
                    if (!"".equals(ele.getElementsByTag("td").eq(10).text())) {
                        // System.out.println(ele.getElementsByTag("td").eq(10).text());
                        // 01～01 03～03
                        String startEndNode = ele.getElementsByTag("td").eq(10).text();
//                        System.out.println(startEndNode.split("\\s+").length);
                        // 一般情况: 01～17
                        if ((startEndNode.split("\\s+").length) == 1) {
                            String[] split = startEndNode.split("～");

                            course.setCouStartWeek(Integer.valueOf(split[0]));
                            course.setCouEndWeek(Integer.valueOf(split[1]));
                        } else {
                            // 特殊情况：01～01 03～03
                            // 有几个这样的小节
                            String[] nodes = startEndNode.split("\\s+");
//                            System.out.println(node);
                            for (String node : nodes) {
                                // new一个id相同的新课来存放
                                Course repeatedCou = new Course();
                                repeatedCou.setCouName(course.getCouName());
                                repeatedCou.setCouTeacher(course.getCouTeacher());

                                String[] split = node.split("～");

                                repeatedCou.setCouStartWeek(Integer.valueOf(split[0]));
                                repeatedCou.setCouEndWeek(Integer.valueOf(split[1]));

                                repeatedCou.setCouID(couID);

                                couList.add(repeatedCou);
                            }
                            // 已经添加到List，跳过本轮外层的添加
                            couID++;
                            continue;
                        }

                    }
                    course.setCouID(couID);
                    couID++;
                    couList.add(course);
                }
            }
        }

        for (Course cou : couList) {
            System.out.println(cou);
        }
        //将table左边的表格标签里的内容提取（）
        Elements rightTable = document.getElementsByTag("tbody").eq(3);

        for (Element element1 : rightTable) {
            int len_Tr1 = element1.getElementsByTag("tr").size();
            for (int l = 1; l < len_Tr1; l++) {
                Elements ele1 = element1.getElementsByTag("tr").eq(l);
                //System.out.println(ele1.html());
                for (Element el1 : ele1) {

                    int len_Td1 = el1.getElementsByTag("td").size();
                    for (int j = 1; j < len_Td1; j++) {
                        //去除为空的课程
                        if (!"".equals(el1.getElementsByTag("td").eq(j).text())) {
                            String tr = el1.getElementsByTag("td").eq(j).html();
                            //tr标签中td的数量
                            //String tr = el1.getElementsByTag("td").html();
                            //System.out.println(e1.html());
                            int len_Br = tr.split("<br>").length;
                            //System.out.println(tr.split("<br>")[0]+"======");
                            for (int a = 0; a < len_Br; a++) {
                                Course course = null;
                                if (tr.split("<br>")[a].contains("班")) {
                                    String couname = tr.split("<br>")[a];
                                    //使用list对课程名字进行判断，相同的名字存储在同一个list

                                    // 循环List 找到 本轮解析中对应的课程对象
                                    for (Course cou : couList) {
                                        String parseName = couname.replace(" ", "");
                                        String listCouName = cou.getCouName().replace(" ", "");
                                        if (parseName.equals(listCouName) && cou.getOnlyID() == null) {
                                            course = new Course(cou.getCouID(), cou.getCouName(), cou.getCouTeacher(), cou.getCouStartWeek(), cou.getCouEndWeek());
                                            course.setOnlyID(onlyID);
                                            onlyID++;
                                        }
                                    }
                                    // 如果没找到 跳出本轮循环
                                    if (course == null) {
                                        continue;
                                    }

                                    if (tr.split("<br>")[a + 1].contains("[单]")) {
                                        course.setCouWeekType(1);
                                        //使用list后，对是否已经输入过教室进行判断，无则输入，有则重新开一个list存储
                                        String couRoom = tr.split("<br>")[a + 1].substring(4, tr.split("<br>")[a + 1].length() - 1);
                                        course.setCouRoom(couRoom);
                                    } else if (tr.split("<br>")[a + 1].contains("[双]")) {
                                        course.setCouWeekType(2);
                                        String couRoom = tr.split("<br>")[a + 1].substring(4, tr.split("<br>")[a + 1].length() - 1);
                                        course.setCouRoom(couRoom);
                                    } else {
                                        course.setCouWeekType(0);
                                        String couRoom = tr.split("<br>")[a + 1].substring(1, tr.split("<br>")[a + 1].length() - 1);
                                        course.setCouRoom(couRoom);
                                    }
                                    //判断是否在右边表格中有起始结束周
                                    String[] trArr = tr.split("<br>");
                                    if (trArr.length - 1 >= a + 2) {
                                        if (tr.split("<br>")[a + 2].contains("周")) {
                                            String newWeek = tr.split("<br>")[a + 2].substring(1, tr.split("<br>")[a + 2].length() - 2);
                                            //System.out.println(newWeek);
                                            course.setCouStartWeek(Integer.valueOf(newWeek.split("-")[0]));
                                            course.setCouEndWeek(Integer.valueOf(newWeek.split("-")[1]));
                                        }
                                    }
                                    String id = el1.getElementsByTag("td").eq(j).attr("id");
                                    Integer couWeek = Integer.valueOf(id.substring(id.length() - 1));
                                    course.setCouWeek(couWeek);
                                    Integer couStartNode = Integer.valueOf(id.substring(0, id.length() - 1));
                                    course.setCouStartNode(couStartNode);

                                    Integer time = Integer.valueOf(el1.getElementsByTag("td").eq(j).attr("rowspan"));
                                    Integer couEndNode = couStartNode + time - 1;
                                    course.setCouEndNode(couEndNode);
                                    courseList.add(course);
                                }
                            }

                        }

                    }


                }
            }
        }


        // 解析结束
        /*for (Course course : courseList) {
            System.out.println(course);
        }*/
        return courseList;
    }

}
