package com.juice.timetable.data.http;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HttpUtils {
    private static List<okhttp3.Cookie> cookieStore = new ArrayList<>();

    public static OkHttpClient getHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                        cookieStore.addAll(list);
                    }

                    @NotNull
                    @Override
                    public List<okhttp3.Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {

                        return cookieStore;
                    }
                }).build();
        return client;
    }
}
