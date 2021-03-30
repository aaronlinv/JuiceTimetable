package com.juice.timetable.data.parse;

import com.juice.timetable.data.bean.ClassNoSignedItem;

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
 *     time   : 2020/05/04
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class ParseClassNoSignedItem {
    /**
     * 班级未签
     * @param parseStr
     * @return
     */
    public static List<ClassNoSignedItem> getClassUnSigned(String parseStr) {
        List<ClassNoSignedItem> noSignedList = new ArrayList<>();
        Document doc = Jsoup.parse(parseStr);
        //提取表格
        Elements elements = doc.getElementsByTag("tbody");
        //先清空容器，防止堆积
        for (Element element : elements) {
            //计算tr标签的数量
            int trSize = element.getElementsByTag("tr").size();
            //循环
            for (int i = 0; i < trSize; i++) {
                ClassNoSignedItem item = new ClassNoSignedItem();
                Elements ele = element.getElementsByTag("tr").eq(i);
                for (Element el : ele) {
                    //tr标签中td的数量
                    String sno = el.getElementsByTag("td").eq(2).text().trim();
                    item.setSno(sno);
                    String name = el.getElementsByTag("td").eq(3).text().trim();
                    item.setSname(name);
                    noSignedList.add(item);
                }
            }
        }
        return noSignedList;
    }
}
