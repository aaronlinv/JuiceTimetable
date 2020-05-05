package com.juice.timetable.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   : 读取 app/src/main/assets/user.properties 中的账号和密码，用于测试开发
 *     version: 1.0
 * </pre>
 */
public class UserInfoUtils {
    private static UserInfoUtils INSTANT = new UserInfoUtils();
    private static Context context;

    private UserInfoUtils() {
    }

    public static UserInfoUtils getINSTANT(Context context) {
        UserInfoUtils.context = context;
        return INSTANT;
    }

    public String getProperty(String key) {
        Properties properties = new Properties();
        try {
            InputStream in = context.getAssets().open("user.properties");
            properties.load(in);
            String value = properties.getProperty(key);
            in.close();
            return value;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getID() {
        return getProperty("id");
    }

    public String getEduPasswd() {
        return getProperty("eduPasswd");
    }

    public String getLeavePasswd() {
        return getProperty("leavePasswd");
    }
}
