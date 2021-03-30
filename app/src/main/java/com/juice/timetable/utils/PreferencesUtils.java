package com.juice.timetable.utils;

import android.content.Context;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/03
 *     desc   : Preferences存储工具类（使用前先调用init方法）
 *     version: 1.0
 * </pre>
 */
public class PreferencesUtils {
    public static Context context;
    public static String configFileName = "configFileName";

    public static void init(Context context) {
        PreferencesUtils.context = context.getApplicationContext();
        configFileName = context.getPackageName();
    }

    public static void putBoolean(String key, boolean value) {
        context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .getBoolean(key, defValue);
    }

    public static void putString(String key, String value) {
        context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .apply();
    }

    public static String getString(String key, String defValue) {
        return context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .getString(key, defValue);
    }


    public static void putFloat(String key, float value) {
        context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .edit()
                .putFloat(key, value)
                .apply();
    }

    public static float getFloat(String key, float defValue) {
        return context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .getFloat(key, defValue);
    }

    public static void putInt(String key, int value) {
        context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .edit()
                .putInt(key, value)
                .apply();
    }

    public static int getInt(String key, int defValue) {
        return context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .getInt(key, defValue);
    }

    public static void putLong(String key, long value) {
        context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .edit()
                .putLong(key, value)
                .apply();
    }

    public static long getLong(String key, long defValue) {
        return context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)
                .getLong(key, defValue);
    }

    public static void clear(String key) {
        context.getSharedPreferences(configFileName, Context.MODE_PRIVATE).edit().remove(key).apply();
    }
}
