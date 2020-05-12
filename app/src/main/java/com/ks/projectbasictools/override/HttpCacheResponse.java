package com.ks.projectbasictools.override;

import android.content.Context;

import com.google.gson.Gson;
import com.ks.projectbasictools.retrofit.HttpResponseListener;
import com.ks.projectbasictools.retrofit.HttpResponseYu;
import com.ks.projectbasictools.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Response;

public class HttpCacheResponse<T> extends HttpResponseYu {
    @Override
    public void onResponse(Context mContext, Response response, String json, HttpResponseListener listener) {
        if (response.code() == 200) {
            if (!String.class.equals(listener.getType())) {
                Gson gson = new Gson();
                T t = gson.fromJson(json, listener.getType());
                listener.onResponse(t,false);
            } else {
                listener.onResponse(response,false);
            }
        } else {
            ToastUtil.show(mContext, "错误码：" + response.code() + "；错误信息：" + response.message());
            listener.onError(response.code(), response.message());
        }
    }

    @Override
    public void onGetCache(Context mContext, String json, HttpResponseListener listener) {
        if (!String.class.equals(listener.getType())) {
            Gson gson = new Gson();
            T t = gson.fromJson(json, listener.getType());
            listener.onResponse(t,true);
        }
    }

    @Override
    public void onError(Context mContext, int errCode, String errMsg, HttpResponseListener httpResponseListener) {
        ToastUtil.show(mContext, "错误码：" + errCode + "；错误信息：" + errMsg);
        httpResponseListener.onError(errCode, errMsg);
    }

    @Override
    public void onFailure(Context mContext, Call call, Throwable e, HttpResponseListener httpResponseListener) {
        ToastUtil.show(mContext, "错误信息：" + e.getMessage());
        httpResponseListener.onFailure(call, e);
    }
}
