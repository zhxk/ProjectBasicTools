package com.ks.projectbasictools.utils;

import android.content.Context;

import com.ks.projectbasictools.helper.OkHttpClientHelper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author：康少
 * date：2019/6/18
 * description：带网络缓存 的retrofit请求
 * 使用方式：1、继承RetrofitParent；
 *           2、重写getBaseUtl()；
 *           3、获取对象实例后，调用对应的请求方法；
 */
public abstract class RetrofitParent {
    private static Retrofit.Builder mInstance;
    protected abstract String getBaseUrl();
    private Retrofit.Builder getInstance() {
        if (mInstance == null) {
            synchronized (Retrofit.Builder.class) {
                if (mInstance == null) {
                    mInstance = new Retrofit.Builder();
                }
            }
        }
        return mInstance;
    }

    public Retrofit getRetrofit(final Context context) {
        Retrofit retrofit = getInstance()
                .baseUrl(getBaseUrl())
                .client(OkHttpClientHelper.getInstance(context).getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
