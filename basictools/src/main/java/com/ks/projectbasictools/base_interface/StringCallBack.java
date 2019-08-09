package com.ks.projectbasictools.base_interface;

import okhttp3.Call;

public interface StringCallBack {
    void onResponse(String response, int id);
    void onError(Call call, Exception e, int id);
}
