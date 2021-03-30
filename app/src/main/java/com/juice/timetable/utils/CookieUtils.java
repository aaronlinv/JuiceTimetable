package com.juice.timetable.utils;

import java.security.GeneralSecurityException;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/25
 *     desc   : 读取SharedPreferences中的Cookie 并进行加解密
 *     version: 1.0
 * </pre>
 */
public class CookieUtils {
    // 测试环境密匙
    private static String secret = "G3YnGIaJ@A%4e945M2CxhIHwU*3@BC*o";

    /**
     * 存入Cookie
     *
     * @param key
     * @param cookieStr
     */
    public static void setCookie(String key, String cookieStr) {
        String encrypt = null;
        try {
            encrypt = AesCryptUtil.encrypt(secret, cookieStr);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        PreferencesUtils.putString(key, encrypt);
        LogUtils.getInstance().d("将Cookie " + key + " 存SharedPreferences -- > " + encrypt);
    }

    public static String getCookie(String key) {
        String cookie = PreferencesUtils.getString(key, null);
        if (cookie != null) {
            try {
                cookie = AesCryptUtil.decrypt(secret, cookie);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
        return cookie;
    }
}
