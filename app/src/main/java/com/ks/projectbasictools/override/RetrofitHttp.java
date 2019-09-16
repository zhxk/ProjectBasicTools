package com.ks.projectbasictools.override;

import com.ks.projectbasictools.constants.AppConstants;
import com.ks.projectbasictools.utils.RetrofitParent;

public class RetrofitHttp extends RetrofitParent {
    private static RetrofitHttp mInstance;

    public RetrofitHttp() {
        try {
            throw new Exception("该类不能实例化");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RetrofitHttp getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitHttp.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitHttp();
                }
            }
        }
        return mInstance;
    }
    @Override
    protected String getBaseUrl() {
        return AppConstants.HTTP.BASE_URL;
    }
}
