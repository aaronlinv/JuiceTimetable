package com.juice.timetable.utils;

import android.provider.ContactsContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    //确认签到
    public static boolean isCheckIn;
    //签到时间
    public static Date checkTime;

    /**
     * 获取签到信息
     */
    public static void getMySignIn() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String s = ReadFile.readToString("C:\\Users\\14989\\Desktop\\网页内容\\自己签到记录.html");
        Document doc = Jsoup.parse(s);
        Elements elements = doc.getElementsByTag("tbody");
        //System.out.println(elements.html());
        for (Element element : elements) {
            Integer len_Tr = element.getElementsByTag("tr").size();
            for(int a=0;a<len_Tr;a++){
                Elements el = element.getElementsByTag("tr").eq(a);
                checkTime =sdf.parse(element.getElementsByTag("tr").eq(a).text());
                if("未签".equals(element.getElementsByTag("td").eq(2).text())){
                    isCheckIn=false;
                }else{isCheckIn=true;}
                System.out.println(checkTime);
                System.out.println(isCheckIn);
            }
        }
    }
}
