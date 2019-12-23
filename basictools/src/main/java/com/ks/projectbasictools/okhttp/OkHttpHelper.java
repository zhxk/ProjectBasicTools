package com.ks.projectbasictools.okhttp;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ks.projectbasictools.base.BaseActivity;
import com.ks.projectbasictools.base_interface.StringCallBack;
import com.ks.projectbasictools.helper.OkHttpClientHelper;
import com.ks.projectbasictools.retrofit.HttpResponseListener;
import com.ks.projectbasictools.retrofit.Request;
import com.ks.projectbasictools.retrofit.ServerHttp;
import com.ks.projectbasictools.utils.LogUtils;
import com.ks.projectbasictools.utils.ToastKs;
import com.ks.projectbasictools.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import retrofit2.converter.gson.GsonConverterFactory;

public final class OkHttpHelper {
    private static StringBuffer log = new StringBuffer();

    private static Context mContext;
    private static StringCallbackYu mStringCallbackYu;
    private static String sBaseUrl;
    private static Map<String, Object> token;

    public static void setBaseUrl(Context context, String baseUrl) {
        mContext = context;
        sBaseUrl = baseUrl;
    }
    public static String getBaseUrl() {
        return sBaseUrl;
    }
    public static void setToken(Map<String, Object> token) {
        if (token == null) {
            token = new HashMap<>();
        } else {
            OkHttpHelper.token.clear();
        }
        OkHttpHelper.token.putAll(token);
    }
    public static <T> void setStringCallbackYu(StringCallbackYu<T> mStringCallbackYu) {
        OkHttpHelper.mStringCallbackYu = mStringCallbackYu;
    }

    /**
     * Description：post请求
     */
    public static <T> void requestCallPost(final Context context, String url, Map<String, Object> map
            , StringCallBack<T> mStringCallBack) {
        if (auth()) {
            RequestCall requestCall;
            PostFormBuilder postFormBuilder;
            String url1 = sBaseUrl + url;
            postFormBuilder = OkHttpUtils.post()
                    .url(url1);
            log.append("\nOkHttpPost请求路径：").append(url1).append("\n");
            //添加token
            if (token != null && !token.isEmpty()) {
                for (String k : token.keySet()) {
                    postFormBuilder.addParams(k, String.valueOf(map.get(k)));
                    log.append("OkHttpPost请求参数：").append(k).append(":").append(map.get(k)).append("\n");
                }
            }
            //循环添加参数
            if (map != null) {
                for (String k : map.keySet()) {
                    if (map.get(k) instanceof File) {
                        postFormBuilder.addFile(k, ((File) map.get(k)).getName(), (File) map.get(k));
                        log.append("请求参数(文件)：").append(k).append(":").append(map.get(k)).append("\n");
                    } else {
                        postFormBuilder.addParams(k, String.valueOf(map.get(k)));
                        log.append("OkHttpPost请求参数：").append(k).append(":").append(map.get(k)).append("\n");
                    }
                }
            }
            if (LogUtils.isDebug)
                LogUtils.d(String.valueOf(log));
            requestCall = postFormBuilder
                    .tag(context)
                    .build();
            requestCall.execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    mStringCallbackYu.onError(mContext, call, e, id, mStringCallBack, url1+"；post");
                }

                @Override
                public void onResponse(String response, int id) {
                    mStringCallbackYu.onResponse(mContext, response, id, mStringCallBack, url1+"；post");
                }
            });
        }
    }

    /**
     * 说明：验证初始化
     */
    private static boolean auth() {
        if (TextUtils.isEmpty(getBaseUrl()) || mStringCallbackYu == null) {
            throw new NullPointerException("init(Context,httpBaseUrl,mStringCallbackYu)：httpBaseUrl和StringCallbackYu都不能为空！" +
                    "请先在Application中初始化：OkHttpUtils.init();");
        } else {
            return true;
        }
    }

    /**
     * Description：get请求
     */
    public static <T> void requestCallGet(final Context context, String url
            , Map<String, Object> map
            , StringCallBack<T> mStringCallBack) {
        if (auth()) {
            RequestCall requestCall;
            GetBuilder getFormBuilder;
            String url1 = sBaseUrl + url;
            getFormBuilder = OkHttpUtils.get()
                    .url(url1);
            log.append("\nOkHttpGet请求路径：").append(url1).append("\n");
            //添加token
            if (token != null && !token.isEmpty()) {
                for (String k : token.keySet()) {
                    getFormBuilder.addParams(k, String.valueOf(map.get(k)));
                    log.append("OkHttpPost请求参数：").append(k).append(":").append(map.get(k)).append("\n");
                }
            }
            //循环添加参数
            if (map != null) {
                for (String k : map.keySet()) {
                    getFormBuilder.addParams(k, String.valueOf(map.get(k)));
                    log.append("OkHttpGet请求参数：").append(k).append(":").append(map.get(k)).append("\n");
                }
            }
            if (LogUtils.isDebug)
                LogUtils.d(String.valueOf(log));
            requestCall = getFormBuilder
                    .tag(context)
                    .build();
            requestCall.execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    mStringCallbackYu.onError(mContext, call, e, id, mStringCallBack, url1 + "；get");
                }

                @Override
                public void onResponse(String response, int id) {
                    mStringCallbackYu.onResponse(mContext, response, id, mStringCallBack, url1 + "；get");
                }
            });
        }
    }
}
