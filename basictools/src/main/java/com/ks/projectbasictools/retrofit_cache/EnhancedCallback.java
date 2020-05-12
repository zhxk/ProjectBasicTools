package com.ks.projectbasictools.retrofit_cache;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 作者：康少
 * 时间：2020/5/12 0012
 * 说明：高级CallBack
 */
public interface EnhancedCallback<T> {
    void onResponse(Call<T> call, Response<T> response);

    void onFailure(Call<T> call, Throwable t);

    void onGetCache(String cacheJson);
}
