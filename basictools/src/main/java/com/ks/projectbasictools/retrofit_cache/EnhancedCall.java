package com.ks.projectbasictools.retrofit_cache;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ks.projectbasictools.BaseApplication;
import com.ks.projectbasictools.utils.NetworkUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 2016/11/30.
 *
 * @author WangYi
 */

public class EnhancedCall<T> {
    private Context mContext;
    private Call<T> mCall;
    // 是否使用缓存 默认开启
    private boolean mUseCache = true;

    public EnhancedCall(Call<T> call) {
        this.mCall = call;
    }

    /**
     * 设置上下文
     */
    public EnhancedCall<T> setContext(Context context) {
        this.mContext = context;
        return this;
    }

    /**
     * 是否使用缓存 默认使用
     */
    public EnhancedCall<T> useCache(boolean useCache) {
        mUseCache = useCache;
        return this;
    }

    public void enqueue(final EnhancedCallback<T> enhancedCallback) {
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                enhancedCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (!mUseCache || NetworkUtils.isNetworkConnected(mContext)) {
                    //不使用缓存 或者网络可用 的情况下直接回调onFailure
                    enhancedCallback.onFailure(call, t);
                    return;
                }

                Request request = call.request();
                String url = request.url().toString();
                RequestBody requestBody = request.body();
                Charset charset = Charset.forName("UTF-8");
                StringBuilder sb = new StringBuilder();
                sb.append(url);
                if (request.method().equals("POST")) {
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(Charset.forName("UTF-8"));
                    }
                    Buffer buffer = new Buffer();
                    try {
                        requestBody.writeTo(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sb.append(buffer.readString(charset));
                    buffer.close();
                }

                String cache = CacheManager.getInstance(mContext).getCache(sb.toString());
                Log.d(CacheManager.TAG, "get cache->" + cache);

                if (!TextUtils.isEmpty(cache)) {
                    enhancedCallback.onGetCache(cache);
                    return;
                }
                enhancedCallback.onFailure(call, t);
                Log.d(CacheManager.TAG, "onError->" + t.getMessage());
            }
        });
    }
}
