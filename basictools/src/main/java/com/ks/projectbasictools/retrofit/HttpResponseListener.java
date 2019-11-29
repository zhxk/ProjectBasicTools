package com.ks.projectbasictools.retrofit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Call;

public abstract class HttpResponseListener<T> {
    public HttpResponseListener() {
    }

    public Type getType() {
        Type mySuperClass = this.getClazz().getGenericSuperclass();
        Type type = ((ParameterizedType)mySuperClass).getActualTypeArguments()[0];
        return type;
    }

    public Class getClazz() {
        return this.getClass();
    }

    public abstract void onResponse(T var1);

    public void onFailure(Call<ResponseBody> call, Throwable e) {
        L.e(e);
    }
}
