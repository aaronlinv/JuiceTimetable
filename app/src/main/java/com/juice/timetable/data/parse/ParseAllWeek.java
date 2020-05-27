package com.juice.timetable.data.parse;

import com.juice.timetable.data.bean.Course;
import com.juice.timetable.utils.LogUtils;

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

    /**
     * 解析完整的课表
     */
    public static List<Course> parseAllCourse(String parseData) {
        List<Course> couList = new ArrayList<>();
        List<Course> courseList = new ArrayList<>();
        Integer onlyID = 0;

        Long couID = 0L;
        Document document = Jsoup.parse(parseData);
        ///将table左边的表格标签里的内容提取（课程名，老师，起始结束周）
        Element leftTable = document.getElementsByTag("td").eq(1).get(0);



        int trSize = leftTable.getElementsByTag("tr").size();
        //循环tr标签内容
        for (int a = 1; a < trSize; a++) {
            Elements tr = leftTable.getElementsByTag("tr").eq(a);
            //这里的for不算是循环，可以理解为是遍历
            for (Element ele : tr) {
                Elements td = ele.getElementsByTag("td");
                //跳过非课程的tr标签
                if (td.size() < 3) {
                    break;
                }
                try {
                    Course course = new Course();
                    String couName = td.eq(0).text().trim();
                    course.setCouName(couName);
                    if (td.eq(2).text().isEmpty() && "院选课".equals(td.eq(3).text())) {
                        String couTeacher = td.eq(5).text().substring(3);
                        course.setCouTeacher(couTeacher);
                        course.setCouWeekType(3);
                        course.setCouID(couID);
                        couID++;
                        course.setOnlyID(onlyID);
                        onlyID++;
                        courseList.add(course);
                    }

                    //老师不为空，设置
                    if (!td.eq(2).text().isEmpty()) {
                        String couTeacher = td.eq(2).text().trim();
                        course.setCouTeacher(couTeacher);
                    }
                    // 解析 起讫时间周序(星期)
                    // bug
                    // 可能出现 <td align="center">01～01<br>03～03</td>
                    if (!"".equals(td.eq(10).text())) {
                        // 01～01 03～03
                        String startEndNode = td.eq(10).text().trim();
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
                } catch (Exception e) {
                    LogUtils.getInstance().e("完整课表leftTable解析错误--->" + e.getMessage());
                }
            }
        }

        //将table右边的表格标签里的内容提取（）
        Element rightTable = document.getElementsByTag("tbody").eq(3).get(0);

        trSize = rightTable.getElementsByTag("tr").size();
        for (int i = 1; i < trSize; i++) {
            Elements tr = rightTable.getElementsByTag("tr").eq(i);
            //System.out.println(ele1.html());
            for (Element el1 : tr) {

                int tdSize = el1.getElementsByTag("td").size();
                for (int j = 1; j < tdSize; j++) {
                    //去除为空的课程
                    if (!"".equals(el1.getElementsByTag("td").eq(j).text())) {
                        String td = el1.getElementsByTag("td").eq(j).html();
                        //tr标签中td的数量
                        int len_Br = td.split("<br>").length;
                        //System.out.println(tr.split("<br>")[0]+"======");
                        for (int a = 0; a < len_Br; a++) {
                            try {
                                Course course = null;
                                if (td.split("<br>")[a].contains("班")) {
                                    String couName = td.split("<br>")[a];
                                    //在leftTable解析完的List中寻找对应course
                                    for (Course cou : couList) {
                                        String parseName = couName.replace(" ", "");
                                        String listCouName = cou.getCouName().replace(" ", "");
                                        //通过课程名字与OnlyID来做判断   Only->单个课程独有的属性 ,即使课程名字相同，OnlyID也不同
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

                                    if (td.split("<br>")[a + 1].contains("[单]")) {
                                        course.setCouWeekType(1);
                                        //使用list后，对是否已经输入过教室进行判断，无则输入，有则重新开一个list存储
                                        String couRoom = td.split("<br>")[a + 1].substring(4, td.split("<br>")[a + 1].length() - 1).trim();
                                        course.setCouRoom(couRoom);
                                    } else if (td.split("<br>")[a + 1].contains("[双]")) {
                                        course.setCouWeekType(2);
                                        String couRoom = td.split("<br>")[a + 1].substring(4, td.split("<br>")[a + 1].length() - 1).trim();
                                        course.setCouRoom(couRoom);
                                    } else {
                                        course.setCouWeekType(0);
                                        String couRoom = td.split("<br>")[a + 1].substring(1, td.split("<br>")[a + 1].length() - 1).trim();
                                        course.setCouRoom(couRoom);
                                    }
                                    //判断是否在右边表格中有起始结束周
                                    String[] trArr = td.split("<br>");
                                    if (trArr.length - 1 >= a + 2) {
                                        if (td.split("<br>")[a + 2].contains("周")) {
                                            String newWeek = td.split("<br>")[a + 2].substring(1, td.split("<br>")[a + 2].length() - 2).trim();
                                            //System.out.println(newWeek);
                                            course.setCouStartWeek(Integer.valueOf(newWeek.split("-")[0]));
                                            course.setCouEndWeek(Integer.valueOf(newWeek.split("-")[1]));
                                        }
                                    }
                                    String id = el1.getElementsByTag("td").eq(j).attr("id").trim();
                                    Integer couWeek = Integer.valueOf(id.substring(id.length() - 1));
                                    course.setCouWeek(couWeek);
                                    Integer couStartNode = Integer.valueOf(id.substring(0, id.length() - 1));
                                    course.setCouStartNode(couStartNode);

                                    Integer time = Integer.valueOf(el1.getElementsByTag("td").eq(j).attr("rowspan").trim());
                                    Integer couEndNode = couStartNode + time - 1;
                                    course.setCouEndNode(couEndNode);
                                    courseList.add(course);
                                }
                            } catch (Exception e) {
                                LogUtils.getInstance().e("完整课程rightTable解析错误--->" + e.getMessage());
                            }
                        }

                    }

                }


            }
        }


        // 解析结束
        LogUtils.getInstance().e("完整课表解析成功--->" + courseList);
        return courseList;
    }

}
