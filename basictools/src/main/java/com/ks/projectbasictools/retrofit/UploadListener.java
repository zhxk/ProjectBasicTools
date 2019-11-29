package com.ks.projectbasictools.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public abstract class UploadListener {
    public UploadListener() {
    }

    public abstract void onResponse(Call<ResponseBody> var1, Response<ResponseBody> var2);

    public abstract void onProgress(long var1, long var3, boolean var5);

    public void onFailure(Call<ResponseBody> call, Throwable t) {
        L.e(t);
    }
}
