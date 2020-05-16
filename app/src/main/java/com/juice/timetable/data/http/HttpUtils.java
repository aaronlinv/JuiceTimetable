package com.juice.timetable.data.http;

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

    public static OkHttpClient getHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        return client;
    }
}
