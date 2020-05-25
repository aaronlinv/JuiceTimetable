package com.juice.timetable.utils;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CookieUtilsTest {
    public Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testCookie() {
        PreferencesUtils.init(getContext());
        String secret = "测试待加密数据";
        CookieUtils.setCookie("testCookie", secret);
        String testCookie = PreferencesUtils.getString("testCookie", null);
        // 加密后数据和 原数据不同
        LogUtils.getInstance().d("加密后 -- > " + testCookie);
        Assert.assertFalse(secret == testCookie);
        // 解密后相同

        String decrypt = CookieUtils.getCookie("testCookie");
        LogUtils.getInstance().d("解密后 -- > " + decrypt);
        Assert.assertTrue(secret.equals(decrypt));

    }
}