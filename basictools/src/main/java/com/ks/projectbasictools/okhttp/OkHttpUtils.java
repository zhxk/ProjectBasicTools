package com.ks.projectbasictools.okhttp;

import android.content.Context;

import com.ks.projectbasictools.base_interface.StringCallBack;
import com.ks.projectbasictools.utils.LogUtils;

import java.util.Map;

public class OkHttpUtils {

    public static final <T> void init(Context context, String httpBaseUrl, StringCallbackYu<T> mStringCallbackYu) {
        OkHttpHelper.setBaseUrl(context, httpBaseUrl);
        OkHttpHelper.setStringCallbackYu(mStringCallbackYu);
    }

    public static <T> void requestGet(final Context context, String url
            , Map<String, Object> map
            , StringCallBack<T> mStringCallBack) {
        OkHttpHelper.requestCallGet(context, url, map, mStringCallBack);
    }

    public static <T> void requestPost(final Context context, String url
            , Map<String, Object> map
            , StringCallBack<T> mStringCallBack) {
        OkHttpHelper.requestCallPost(context, url, map, mStringCallBack);
    }

    public static void setToken(Map<String, Object> token) {
        OkHttpHelper.setToken(token);
    }

    public static void setDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }
}
