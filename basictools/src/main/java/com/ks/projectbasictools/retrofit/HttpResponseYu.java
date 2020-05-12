package com.ks.projectbasictools.retrofit;

import android.content.Context;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 作者：康少
 * 时间：2019/12/23 0023
 * 说明：带缓存的网络请求response——预执行
 */
public abstract class HttpResponseYu<T> {
    public abstract void onResponse(Context mContext, Response<ResponseBody> response
            , String json, HttpResponseListener<T> listener);

    public void onError(Context mContext, int errCode, String errMsg, HttpResponseListener<T> httpResponseListener) {}

    public void onGetCache(Context mContext, String json, HttpResponseListener<T> listener) {}

    public void onFailure(Context mContext, Call<ResponseBody> call, Throwable e, HttpResponseListener<T> listener) {}
}
