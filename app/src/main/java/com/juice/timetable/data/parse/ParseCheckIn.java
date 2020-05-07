package com.juice.timetable.data.parse;

import com.juice.timetable.data.bean.MyCheckIn;

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
public class ParseCheckIn {

    /**
     * 获取自己签到信息
     */
    public static MyCheckIn getMySigned(String parseStr) {

        MyCheckIn myCheckIn = new MyCheckIn();
        //抓取
        //String s = ReadFile.readToString("C:\\Users\\14989\\Desktop\\网页内容\\自己签到记录.html");
        //解析文档
        Document doc = Jsoup.parse(parseStr);
        //提取表格
        Elements elements = doc.getElementsByTag("tbody");
        //System.out.println(elements.html());
        for (Element element : elements) {
            //计算tr标签的数量
            Integer len_Tr = element.getElementsByTag("tr").size();

            Elements ele = element.getElementsByTag("tr").eq(0);
            for (Element el : ele) {
                //tr标签中td的数量
                Integer len_Td = el.getElementsByTag("td").size();

                String checkTime = el.getElementsByTag("td").eq(1).text();
                myCheckIn.setCheckTime(checkTime);
                if ("未签".equals(el.getElementsByTag("td").eq(2).text())) {
                    boolean isCheckIn = false;
                    myCheckIn.setCheckIn(isCheckIn);

                } else {
                    boolean isCheckIn = true;
                    myCheckIn.setCheckIn(isCheckIn);
                }


            }

        }
        return myCheckIn;
    }


}

