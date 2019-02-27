package com.ks.basictools.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/27.
 */

public class ToastUtil {
    private static long lastTime = 0;
    private static long currentTime;

    public static void show(Context context, String msg) {
        currentTime = System.currentTimeMillis();
        if ((currentTime - lastTime) >= 2000) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            lastTime = currentTime;
        }
    }

    public static void showEmpty(Context context) {
        currentTime = System.currentTimeMillis();
        if ((currentTime - lastTime) >= 2000) {
            Toast.makeText(context, "功能尚未完成...", Toast.LENGTH_SHORT).show();
            lastTime = currentTime;
        }
    }
}
