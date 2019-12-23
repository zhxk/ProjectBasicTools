package com.ks.projectbasictools.okhttp;

import android.content.Context;

import com.ks.projectbasictools.base_interface.StringCallBack;
import com.ks.projectbasictools.utils.LogUtils;

import okhttp3.Call;

/**
 * 作者：康少
 * 时间：2019/12/23 0023
 * 说明：OkHttp网络请求response预处理
 */
public abstract class StringCallbackYu<T> {

    public abstract void onResponse(Context mContext, String response, int id
            , StringCallBack<T> mStringCallBack, String requestType);

    public void onError(Context mContext, Call call, Exception e, int id
            , StringCallBack<T> mStringCallBack, String requestType) {
        LogUtils.e(e.getMessage());
    }
}
