package com.ks.projectbasictools.override;

import android.content.Context;

import com.google.gson.Gson;
import com.ks.projectbasictools.retrofit.HttpResponseListener;
import com.ks.projectbasictools.retrofit.HttpResponseYu;
import com.ks.projectbasictools.utils.ToastUtil;

import retrofit2.Response;

public class HttpCacheResponse<T> extends HttpResponseYu {
    @Override
    public void onResponse(Context mContext, Response response, String json, HttpResponseListener listener) {
        if (response.code() == 200) {
            if (!String.class.equals(listener.getType())) {
                Gson gson = new Gson();
                T t = gson.fromJson(json, listener.getType());
                listener.onResponse(t);
            } else {
                listener.onResponse(response);
            }
        } else {
            ToastUtil.show(mContext, "错误码：" + response.code() + "；错误信息：" + response.message());
        }
    }
}
