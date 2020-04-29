package com.juice.timetable.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FileUtils {
    /**
     * hex_md5加密
     *
     * @param plainText
     * @return
     */
    public static String hex_md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            md.reset();
            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取保存的验证码图
     * 数据区：checkCode.jpg
     *
     * @param context
     * @return
     */
    public static InputStream getFile(Context context) {
        File file = new File(context.getFilesDir(), "checkCode.jpg");
        try {
            return new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写入网络获取的验证码
     * 数据区：checkCode.jpg
     *
     * @param context
     * @param is
     * @return
     */
    public static boolean saveFile(Context context, InputStream is, String fileName) {
        File file = new File(context.getFilesDir(), fileName + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int n;
            while ((n = is.read(b)) != -1) {
                fos.write(b, 0, n);
            }
            is.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
