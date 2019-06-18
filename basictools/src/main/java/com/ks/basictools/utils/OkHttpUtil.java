package com.ks.basictools.utils;

import android.content.Context;

import com.ks.basictools.base.BaseActivity;
import com.ks.basictools.base_interface.StringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 使用方式：1、继承OkHttpUtil；
 *           2、重写getBaseUtl()；
 *           3、重写getStringCallBack();
 *           4、获取对象实例后，调用对应的请求方法；
 */
public abstract class OkHttpUtil {
    private StringBuffer log = new StringBuffer();

    protected abstract String getBaseUrl();
    protected abstract StringCallBack getStringCallBack();

    /**
     * Description：post请求
     */
    public void requestCallPost(final Context context, String url, List<Map<String, Object>> list) {
        RequestCall requestCall;
        PostFormBuilder postFormBuilder;
        postFormBuilder = OkHttpUtils.post()
                .url(getBaseUrl() + url);
        log.append("\nOkHttpPost请求路径：").append(getBaseUrl()).append(url).append("\n");
        //循环添加参数
        if (list != null) {
            for (Map<String, Object> map : list) {
                for (String k : map.keySet()) {
                    postFormBuilder.addParams(k, map.get(k).toString());
                    log.append("OkHttpPost请求参数：").append(k).append(":").append(map.get(k).toString()).append("\n");
                }
            }
        }
        LogUtils.d(String.valueOf(log));
        requestCall = postFormBuilder
                .tag(context)
                .build();
        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.d("OkHttpPost访问ErrorMessage:" + e.getMessage());
                if (e.getMessage().equals("timeout")) {
                    ToastKs.show((BaseActivity) context, "网络走丢了，请稍后再试");
                } else {
                    ToastUtil.show(context, "error:" + e.getMessage());
                }
                getStringCallBack().onError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.d("OkHttpPost访问response:" + response);
                getStringCallBack().onResponse(response, id);
            }
        });
    }

    /**
     * Description：get请求
     */
    public void requestCallGet(final Context context, String url, List<Map<String, Object>> list) {
        RequestCall requestCall;
        GetBuilder getFormBuilder;
        getFormBuilder = OkHttpUtils.get()
                .url(getBaseUrl() + url);
        log.append("\nOkHttpGet请求路径：").append(getBaseUrl()).append(url).append("\n");
        //循环添加参数
        if (list != null) {
            for (Map<String, Object> map : list) {
                for (String k : map.keySet()) {
                    getFormBuilder.addParams(k, map.get(k).toString());
                    log.append("OkHttpGet请求参数：").append(k).append(":").append(map.get(k).toString()).append("\n");
                }
            }
        }
        LogUtils.d(String.valueOf(log));
        requestCall = getFormBuilder
                .tag(context)
                .build();
        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.d("OkHttpGet访问ErrorMessage:" + e.getMessage());
                if (e.getMessage().equals("timeout")) {
                    ToastKs.show((BaseActivity) context, "网络走丢了，请稍后再试");
                } else {
                    ToastUtil.show(context, "error:" + e.getMessage());
                }
                getStringCallBack().onError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.d("OkHttpGet访问response:" + response);
                getStringCallBack().onResponse(response, id);
            }
        });
    }
}
