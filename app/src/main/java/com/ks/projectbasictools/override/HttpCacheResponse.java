package com.ks.projectbasictools.override;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.ks.projectbasictools.retrofit.HttpResponseListener;
import com.ks.projectbasictools.retrofit.HttpResponseYu;
import com.ks.projectbasictools.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Response;

public class HttpCacheResponse<T> extends HttpResponseYu {
    @Override
    public void onResponse(Context mContext, Response response, String json, HttpResponseListener listener) {
        if (response.isSuccessful()) {
            if (!String.class.equals(listener.getType())) {
                Gson gson = new Gson();
                T t = gson.fromJson(json, listener.getType());
                new Handler(Looper.getMainLooper()).post(() -> listener.onResponse(t,false));
            } else {
                new Handler(Looper.getMainLooper()).post(() -> listener.onResponse(response,false));
            }
        } else {
            new Handler(Looper.getMainLooper()).post(() -> {
                ToastUtil.show(mContext, "错误码：" + response.code() + "；错误信息：" + response.message());
                listener.onError(response.code(), response.message());
            });
        }
    }

    @Override
    public void onGetCache(Context mContext, String json, HttpResponseListener listener) {
        if (!String.class.equals(listener.getType())) {
            Gson gson = new Gson();
            T t = gson.fromJson(json, listener.getType());
            new Handler(Looper.getMainLooper()).post(() -> listener.onResponse(t,true));
        }
    }

    @Override
    public void onError(Context mContext, int errCode, String errMsg, HttpResponseListener httpResponseListener) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (errCode != -1) {//筛选掉 无缓存数据的提示信息
                ToastUtil.show(mContext, "错误码：" + errCode + "；错误信息：" + errMsg);
            }
            httpResponseListener.onError(errCode, errMsg);
        });
    }

    @Override
    public void onFailure(Context mContext, Call call, Throwable e, HttpResponseListener httpResponseListener) {
        new Handler(Looper.getMainLooper()).post(() -> {
            ToastUtil.show(mContext, "错误信息：" + e.getMessage());
            httpResponseListener.onFailure(call, e);
        });
    }
}
