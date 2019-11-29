package com.ks.projectbasictools;

import android.app.Application;

import com.ks.projectbasictools.constants.AppConstants;
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

        ServerHttp.init(this, AppConstants.HTTP.BASE_URL);
        ServerHttp.setDebug(true);
    }
}
