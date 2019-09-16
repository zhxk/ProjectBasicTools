package com.ks.projectbasictools.api;

import com.ks.projectbasictools.bean.NewsEntity;
import com.ks.projectbasictools.constants.AppConstants;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @作者JTL.
 * @日期2018/3/15.
 * @说明：
 */

public interface NewsApi {
    @GET(AppConstants.HTTP.NEWS)
    Call<NewsEntity> getNews();
}
