package com.juice.timetable.utils;

import org.junit.Test;

import java.security.GeneralSecurityException;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class AesCryptUtilTest {

    @Test
    public void encrypt() throws GeneralSecurityException {
        String str = "abcdefghijklmn";
        String secret = AesCryptUtil.encrypt("橙子app", "abcdefghijklmn");
        LogUtils.getInstance().d("加密 -- > " + secret);

        String decode = AesCryptUtil.decrypt("橙子app", secret);
        LogUtils.getInstance().d("解密 -- > " + decode);

    }
}