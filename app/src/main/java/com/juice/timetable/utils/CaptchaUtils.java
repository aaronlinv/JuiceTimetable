package com.juice.timetable.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CaptchaUtils {
    private static Map<Bitmap, String> trainMap = new HashMap<Bitmap, String>();


    /**
     * 识别验证码
     *
     * @param code
     * @param context
     * @return
     */
    public static String getAllOcr(Bitmap code, Context context) {
        // 分割
        List<Bitmap> listImg = split(code);
        // 加载识别样本
        Map<Bitmap, String> map = loadTrainData(context);
        String result = "";
        for (Bitmap bi : listImg) {
            // 识别单个字符
            result += getSingleCharOcr(bi, map);
        }
        return result;
    }

    /**
     * 识别验证码单个字符
     *
     * @param img
     * @param map
     * @return
     */
    private static String getSingleCharOcr(Bitmap img, Map<Bitmap, String> map) {
        String result = "#";
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height;
        for (Bitmap bi : map.keySet()) {

            int count = 0;

            int widthMin = bi.getWidth();
            int heightMin = bi.getHeight();
            int countPixel = 1;
            Label1:
            for (int x = 2; x < widthMin - 1; ++x) {
                for (int y = 0; y < heightMin; ++y) {
                    // 统计不相同的像素个数
                    countPixel++;
                    if (isWhite(img.getPixel(x, y)) != isWhite(bi.getPixel(x, y))) {
                        count++;
                        if (count >= min)
                            break Label1;
                    }
                }
            }
            if (count < min) {
                min = count;
                result = map.get(bi);
            }

        }
        return result;
    }

    /**
     * 分割验证码
     *
     * @param aftercode
     * @return
     */
    private static List<Bitmap> split(Bitmap aftercode) {
        List<Bitmap> subImgs = new ArrayList<Bitmap>();
        int width = aftercode.getWidth() / 4;
        int height = aftercode.getHeight();
        subImgs.add(Bitmap.createBitmap(aftercode, 0, 0, width, height));
        subImgs.add(Bitmap.createBitmap(aftercode, 10, 0, width, height));
        subImgs.add(Bitmap.createBitmap(aftercode, 20, 0, width, height));
        subImgs.add(Bitmap.createBitmap(aftercode, 30, 0, width, height));
        return subImgs;
    }

    /**
     * 加载识别样本
     *
     * @param context
     * @return
     */
    private static Map<Bitmap, String> loadTrainData(Context context) {
        try {
            String[] picsPtah = context.getAssets().list("match");
            InputStream inputStream;
            Bitmap bitmap;
            for (String picPath : picsPtah) {
                inputStream = context.getResources().getAssets().open("match/" + picPath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    trainMap.put(bitmap, picPath.charAt(0) + "");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trainMap;
    }

    public static int isWhite(int pixel) {
        if (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel) >= 705) {
            return 1;
        }
        return 0;
    }
}
