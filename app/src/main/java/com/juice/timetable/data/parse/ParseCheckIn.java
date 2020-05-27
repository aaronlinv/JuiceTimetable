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
        for (Element element : elements) {
            //ele->element中第一个tr标签
            Elements ele = element.getElementsByTag("tr").eq(0);
            for (Element el : ele) {
                String check_status = el.getElementsByTag("td").eq(2).text().trim();
                boolean isCheckIn = !"未签".equals(check_status);
                myCheckIn.setCheckIn(isCheckIn);
                myCheckIn.setCheckTime(check_status);

            }

        }
        return myCheckIn;
    }


}

