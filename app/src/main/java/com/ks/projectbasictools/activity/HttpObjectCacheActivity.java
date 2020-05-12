package com.ks.projectbasictools.activity;

import android.os.Bundle;

import com.ks.projectbasictools.R;
import com.ks.projectbasictools.base.BaseActivity;
import com.ks.projectbasictools.bean.BaseEntity;
import com.ks.projectbasictools.bean.requestObj;
import com.ks.projectbasictools.retrofit.HttpResponseListener;
import com.ks.projectbasictools.retrofit.Request;
import com.ks.projectbasictools.retrofit.ServerHttp;
import com.ks.projectbasictools.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class HttpObjectCacheActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_cache);

        request();
    }

    private void request() {
        Request request = ServerHttp.newPostRequest("main-screen/list/conditions");
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("content-type", "application/json");
        request.putHeaderMap(headerMap);
        ServerHttp.sendJson(request, new requestObj(), new HttpResponseListener<BaseEntity>() {
            @Override
            public void onResponse(BaseEntity var1, boolean isCache) {
                LogUtils.e("sllslslso   onResponse，isCache：" + isCache);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                super.onFailure(call, e);
                LogUtils.e("sllslslso   onError");
            }

            @Override
            public void onError(int code, String msg) {
                super.onError(code, msg);
                LogUtils.e("sllslslso   onError");
            }
        });

    }
}
