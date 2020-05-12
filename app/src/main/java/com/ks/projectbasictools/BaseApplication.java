package com.ks.projectbasictools;

import android.app.Application;
import android.content.Context;

import com.ks.projectbasictools.constants.AppConstants;
import com.ks.projectbasictools.okhttp.OkHttpUtils;
import com.ks.projectbasictools.override.HttpCacheResponse;
import com.ks.projectbasictools.override.StringCallBackYu;
import com.ks.projectbasictools.retrofit.ServerHttp;

/**
 * author：康少
 * date：2019/2/20
 * description：基础Application  网络监听
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /*缓存网络请求 初始化*/
        ServerHttp.init(this, AppConstants.HTTP.BASE_URL, new HttpCacheResponse());
        ServerHttp.setUseCache(true);//开启使用 无网络时，加载缓存数据
        ServerHttp.setDebug(true);

        /*无缓存网络请求 初始化*/
        OkHttpUtils.init(this, AppConstants.HTTP.BASE_URL, new StringCallBackYu());
        OkHttpUtils.setDebug(true);
    }
}
