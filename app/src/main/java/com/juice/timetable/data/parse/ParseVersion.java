package com.juice.timetable.data.parse;

import com.juice.timetable.utils.HttpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ParseVersion {


    public static String getSource(String url) throws Exception {
        OkHttpClient client = HttpUtils.getHttpClient();
        Request request = new Request.Builder()
                .addHeader("Content-Type", "text/html")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.56")
                .get()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();

        return result;
    }

    public static String getVersion(String Source) {
        Document doc = Jsoup.parse(Source);
        Elements rootSelect = doc.select("div.apk_left_one > div > div > div.apk_topbar_mss > p.detail_app_title > span");

        return rootSelect.text();
    }

    public static String getVersionInfo(String Source) {
        Document doc = Jsoup.parse(Source);
        Elements rootSelect = doc.select("div.apk_left_two > div > div:nth-child(2) > p.apk_left_title_info");

        return rootSelect.text();
    }

}
