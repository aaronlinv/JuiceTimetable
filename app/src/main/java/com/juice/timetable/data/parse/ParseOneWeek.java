package com.juice.timetable.data.parse;


import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.Utils;

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
    /**
     * 周课表解析
     */
    public static List<OneWeekCourse> parseCourse(String parseStr) {
        List<OneWeekCourse> couList = new ArrayList<>();

        Document doc = Jsoup.parse(parseStr);

        //课表内容
        Element table = doc.getElementsByTag("table").eq(2).get(0);

        //遍历表格
        //获取当前周
        String title = doc.getElementsByClass("td3").text().trim();
        Integer week = null;
        try {
            week = Integer.valueOf(title.substring(title.indexOf("第") + 1, title.indexOf("周")));
        } catch (Exception e) {
            LogUtils.getInstance().e("周课表解析当前周失败---->" + e.getMessage());
            return couList;
        }

        LogUtils.getInstance().e("开始解析当前周课表--->" + week);
        //table中tr标签的数量
        int trSize = table.getElementsByTag("tr").size();
        //跳过 课表中的显示周几的 第一行
        for (int a = 1; a < trSize; a++) {
            Elements tr = table.getElementsByTag("tr").eq(a);
            for (Element el : tr) {
                //tr标签中td的数量
                int tdSize = el.getElementsByTag("td").size();
                for (int b = 1; b < tdSize; b++) {
                    try {
                        //获取课表单格
                        Elements td = el.getElementsByTag("td").eq(b);
                        //判断当前单元格不为空
                        if (!"".equals(td.text())) {
                            String[] tdText = td.html().split("<br>");
                            int brSize = tdText.length;
                            // 累加 2 是因为 它的规则是：一行课程 一行教室
                            // 例如：大学物理（上）(15)班<br>[网络教学]<br>形势与政策（六）(6)班<br>[网络教学]</td>
                            for (int c = 0; c < brSize; c += 2) {
                                // if (tdText[c].contains("班") || tdText[c].contains("调课")) {
                                // 原来的逻辑是根据 关键词判断，但是这也会缺少判断： 形势与政策（三） 和 考试：工程制图(10:00-12:00)
                                // 具体课程解析逻辑
                                if (tdText[c].length() > 0) {
                                    //创建一个新的对象
                                    OneWeekCourse cou = new OneWeekCourse();
                                    cou.setInWeek(week);

                                    String couName = tdText[c];
                                    // 停课判断逻辑
                                    // 马克思主义基本原理概论(11)班<br>[机北407](14:00)<br>停课：马克思主义基本原理概论<br>[机北407]</td>

                                    // 22.5.14 自带停课提示，所以移除下面逻辑
                                    // if (brSize >= c+1) {
                                    //     boolean isSuspend = tdText[c+1].contains("停课");
                                    //     if (isSuspend) {
                                    //         couName = "[停课] " + couName;
                                    //     }
                                    // }
                                    cou.setCouName(couName);

                                    String couRoom = tdText[c + 1];
                                    // 去除 [网络教室] 左右的 []
                                    // String couRoom = s1.substring(1, s1.length() - 1);
                                    // 20.10.27 增加了时间 故去掉上面功能，直接显示 [网络教室](19:00)
                                    LogUtils.getInstance().i("couRoom --> " + couRoom);
                                    cou.setCouRoom(couRoom);

                                    String id = td.attr("id");
                                    Integer dayOfWeek = Integer.valueOf(id.substring(id.length() - 1));
                                    cou.setDayOfWeek(dayOfWeek);
                                    Integer startNode = Integer.valueOf(id.substring(0, id.length() - 1));
                                    cou.setStartNode(startNode);

                                    Integer time = Integer.valueOf(td.attr("rowspan"));
                                    Integer endNode = startNode + time - 1;
                                    cou.setEndNode(endNode);
                                    cou.setCourseType(0);
                                    //新增一个属性CourseType判断 重课
                                    for (OneWeekCourse oneWeekCourse : couList) {
                                        if (cou.getDayOfWeek().equals(oneWeekCourse.getDayOfWeek()) && cou.getStartNode().equals(oneWeekCourse.getStartNode()) && cou.getEndNode().equals(oneWeekCourse.getEndNode())) {
                                            cou.setCourseType(4);
                                        }
                                    }

                                    couList.add(cou);
                                }
                            }

                        }
                    } catch (Exception e) {
                        LogUtils.getInstance().e("解析当前周课表课程异常--->" + e.getMessage());
                        //解析当前一门课程失败，跳过此课程
                    }
                }
            }
        }
        // 解析结束
        LogUtils.getInstance().e("结束解析当前周课表--->" + couList);
        return couList;
    }

    // 获取当前周
    public static int getCurWeek(String parseStr) {
        Document doc = Jsoup.parse(parseStr);
        String title = doc.getElementsByClass("td3").text().trim();

        try {
            int curWeek = Integer.parseInt(title.substring(title.indexOf("第") + 1, title.indexOf("周")));
            LogUtils.getInstance().e("开始解析当前周课表--->" + curWeek);
            return curWeek;
        } catch (Exception e) {
            LogUtils.getInstance().e("周课表解析当前周失败---->" + e.getMessage());
            return Utils.UNSET_CUR_WEEK;
        }
    }
}