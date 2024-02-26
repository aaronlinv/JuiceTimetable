package com.juice.timetable.data.parse;

import com.juice.timetable.data.parse.dto.VersionDTO;
import com.juice.timetable.utils.HttpUtils;
import com.juice.timetable.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static VersionDTO getVersion(String source) {
        try {
            JSONObject json = new JSONObject(source);
            String latestVersion = json.getString("tag_name");
            String downloadUrl = json.getString("html_url");

            latestVersion = latestVersion.replace("v","");
            return new VersionDTO(latestVersion, downloadUrl);

        } catch (JSONException e) {
            LogUtils.getInstance().e("获取版本错误：" + e.getMessage());
        }
        return new VersionDTO();
    }

}
