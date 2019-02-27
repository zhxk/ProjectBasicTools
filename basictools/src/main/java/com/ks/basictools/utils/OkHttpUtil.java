package com.ks.basictools.utils;

import android.content.Context;

import com.ks.basictools.base_interface.StringCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/4/2.
 */
public class OkHttpUtil {
    private StringCallBack stringCallBack;

    public OkHttpUtil(StringCallBack stringCallBack) {
        this.stringCallBack = stringCallBack;
    }

    public void requestCall2(final Context context, String url, List<Map<String, Object>> list) {
        RequestCall requestCall;
        PostFormBuilder postFormBuilder;
        postFormBuilder = OkHttpUtils.post()
                .url(url);
        //循环添加参数
        if (list != null) {
            for (Map<String, Object> map : list) {
                for (String k : map.keySet()) {
                    postFormBuilder.addParams(k, map.get(k).toString());
                }
            }
        }
        requestCall = postFormBuilder
                .tag(context)
                .build();
        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                stringCallBack.onError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                stringCallBack.onResponse(response, id);
            }
        });
    }
}
