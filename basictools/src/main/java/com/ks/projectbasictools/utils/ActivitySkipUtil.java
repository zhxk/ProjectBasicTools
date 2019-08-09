package com.ks.projectbasictools.utils;

import android.app.Activity;
import android.content.Intent;

import com.ks.projectbasictools.publicView.NoNetworkActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * author：康少
 * date：2019/2/20
 * description：Activity跳转工具类
 */
public class ActivitySkipUtil {
    public ActivitySkipUtil() {
        throw new UnsupportedOperationException("ActivitySkipUtil不能实例化");
    }

    /**
     * 功能描述:简单地Activity的跳转(不携带任何数据)
     *
     * @param activity       发起跳转的Activity实例
     * @param targetActivity 目标Activity实例
     * @param isFinishActivity        是否关闭当前Activity活动
     */
    public static void skipActivity(Activity activity,
                                    Class<? extends Activity> targetActivity,
                                    boolean isFinishActivity) {
        if (isFinishActivity) {
            activity.finish();
        }
        Intent intent;
        if (NetworkUtils.isNetworkConnected(activity)) {
            intent = new Intent(activity, targetActivity);
        } else {
            intent = new Intent(activity, NoNetworkActivity.class);
        }
        activity.startActivity(intent);
    }

    /**
     * 功能描述：带数据的Activity之间的跳转
     *
     * @param activity       发起跳转的Activity实例
     * @param targetActivity 目标Activity实例
     * @param hashMap        携带HashMap参数，支持String、Boolean、Integer、Float、Double
     * @param isFinishActivity        是否关闭当前Activity活动
     */
    public static void skipActivity(Activity activity,
                                    Class<? extends Activity> targetActivity,
                                    HashMap<String, ? extends Object> hashMap,
                                    boolean isFinishActivity) {
        if (isFinishActivity) {
            activity.finish();
        }
        Intent intent;
        if (NetworkUtils.isNetworkConnected(activity)) {
            intent = new Intent(activity, targetActivity);
            Iterator<?> iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                @SuppressWarnings("unchecked")
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
                        .next();
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    intent.putExtra(key, (String) value);
                }
                if (value instanceof Boolean) {
                    intent.putExtra(key, (boolean) value);
                }
                if (value instanceof Integer) {
                    intent.putExtra(key, (int) value);
                }
                if (value instanceof Float) {
                    intent.putExtra(key, (float) value);
                }
                if (value instanceof Double) {
                    intent.putExtra(key, (double) value);
                }
            }
        } else {
            intent = new Intent(activity, NoNetworkActivity.class);
        }
        activity.startActivity(intent);
    }
}
