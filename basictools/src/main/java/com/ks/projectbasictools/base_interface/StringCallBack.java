package com.ks.projectbasictools.base_interface;

import com.ks.projectbasictools.utils.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

public abstract class StringCallBack<T> {

    public Type getType() {
        Type mySuperClass = this.getClass().getGenericSuperclass();
        return ((ParameterizedType)mySuperClass).getActualTypeArguments()[0];
    }

    public abstract void onResponse(T bean, int id);

    public void onError(Call call, Exception e, int id){
        LogUtils.e(e);
    }
}