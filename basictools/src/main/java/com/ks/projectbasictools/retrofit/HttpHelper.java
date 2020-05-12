package com.ks.projectbasictools.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ks.projectbasictools.helper.OkHttpClientHelper;
import com.ks.projectbasictools.retrofit_cache.CacheManager;
import com.ks.projectbasictools.retrofit_cache.EnhancedCall;
import com.ks.projectbasictools.retrofit_cache.EnhancedCallback;
import com.ks.projectbasictools.utils.JSONUtil;
import com.ks.projectbasictools.utils.LogUtils;
import com.ks.projectbasictools.utils.NetworkUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public final class HttpHelper<T> {
    private static Context mContext;
    private static volatile WeakReference<HttpHelper> sInstance;
    private final Retrofit mRetrofit;
    private static String sBaseUrl;
    private static HttpResponseYu mHttpResponseYu;
    private static boolean mUseCache = true;//是否开启缓存

    public static void setBaseUrl(Context context, String baseUrl) {
        mContext = context;
        sBaseUrl = baseUrl;
    }

    public static void setOpenCache(boolean useCache) {
        mUseCache = useCache;
    }

    public static String getBaseUrl() {
        return sBaseUrl;
    }

    public static void setHttpResponseYu(HttpResponseYu mHttpResponseYu) {
        HttpHelper.mHttpResponseYu = mHttpResponseYu;
    }

    private HttpHelper() {
        Builder builder = new Builder();
        if (TextUtils.isEmpty(getBaseUrl()) || mHttpResponseYu == null) {
            throw new NullPointerException("init(Context,httpBaseUrl)：httpBaseUrl is not null; httpResponseYu is not null");
        } else {
            this.mRetrofit = builder
                    .baseUrl(getBaseUrl())
                    .client(OkHttpClientHelper.getInstance(mContext, mUseCache).getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static HttpHelper getInstance() {
        if (sInstance == null || sInstance.get() == null) {
            Class var0 = HttpHelper.class;
            synchronized (HttpHelper.class) {
                if (sInstance == null || sInstance.get() == null) {
                    sInstance = new WeakReference(new HttpHelper());
                }
            }
        }

        return sInstance.get();
    }

    public static <T> Call getAsync(String apiUrl, @HeaderMap Map<String, Object> headers, Map<String, Object> paramMap, HttpResponseListener<T> httpResponseListener) {
        if (paramMap == null) {
            paramMap = new HashMap();
        }

        if (headers == null) {
            headers = new HashMap();
        }

        HttpHelper.HttpService httpService = getInstance().mRetrofit.create(HttpHelper.HttpService.class);
        Call<ResponseBody> call = httpService.get(apiUrl, headers, paramMap);
        if (L.isDebug) {
            L.i("Get请求路径：" + sBaseUrl + apiUrl);
            for (String k : paramMap.keySet()) {
                L.i("请求参数：" + k + ":" + paramMap.get(k) + "\n");
            }
        }
        parseEnhancedCall(call, httpResponseListener, apiUrl);
        return call;
    }

    public static <T> Call postAsync(String apiUrl, @HeaderMap Map<String, Object> headers, Map<String, Object> paramMap
            , HttpResponseListener<T> httpResponseListener) {
        if (paramMap == null) {
            paramMap = new HashMap();
        }

        if (headers == null) {
            headers = new HashMap();
        }

        HttpHelper.HttpService httpService = getInstance().mRetrofit.create(HttpHelper.HttpService.class);
        Call<ResponseBody> call = httpService.post(apiUrl, headers, paramMap);
        if (L.isDebug) {
            L.i("Post请求路径：" + sBaseUrl + apiUrl);
            for (String k : paramMap.keySet()) {
                L.i("请求参数：" + k + ":" + paramMap.get(k) + "\n");
            }
        }
        parseEnhancedCall(call, httpResponseListener, apiUrl);
        return call;
    }

    public static <T> Call postAsync(String apiUrl, @HeaderMap Map<String, Object> headers, Object requestObj
            , HttpResponseListener<T> httpResponseListener) {
        if (headers == null) {
            headers = new HashMap();
        }
        HttpHelper.HttpService httpService = getInstance().mRetrofit.create(HttpHelper.HttpService.class);
        Call<ResponseBody> call = httpService.post(apiUrl, headers, requestObj);
        if (L.isDebug) {
            Gson gson = new Gson();
            String jsonString = JSONUtil.formatJSONString(gson.toJson(requestObj));
            L.i("Post请求路径：" + sBaseUrl + apiUrl + "；\n请求参数：" + jsonString);
        }
        parseEnhancedCall(call, httpResponseListener, apiUrl);
        return call;
    }

    public static Call upload(Request request, final UploadListener uploadListener) {
        if (request.getUploadFiles() == null || !((File) request.getUploadFiles().get(0)).exists()) {
            (new FileNotFoundException("file does not exist(文件不存在)")).printStackTrace();
        }

        Map<String, RequestBody> requestBodyMap = new HashMap();
        RequestBody requestBody = RequestBody.create(request.getMediaType(), (File) request.getUploadFiles().get(0));
        requestBodyMap.put("file[]\"; filename=\"" + ((File) request.getUploadFiles().get(0)).getName(), requestBody);
        String httpUrl = request.getApiUlr().trim();
        String tempUrl = httpUrl.substring(0, httpUrl.length() - 1);
        String baseUrl = tempUrl.substring(0, tempUrl.lastIndexOf(File.separator) + 1);
        if (L.isDebug) {
            L.i("httpUrl:" + httpUrl);
            L.i("tempUrl:" + tempUrl);
            L.i("baseUrl:" + baseUrl);
            L.i("apiUrl:" + httpUrl.substring(baseUrl.length()));
        }

        Retrofit retrofit = (new Builder()).addConverterFactory(new ChunkingConverterFactory(requestBody, uploadListener)).addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build();
        HttpHelper.HttpService service = (HttpHelper.HttpService) retrofit.create(HttpHelper.HttpService.class);
        Call<ResponseBody> model = null;
        model = service.postUpload(httpUrl.substring(baseUrl.length()), "uploadDes", requestBodyMap);
        model.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                uploadListener.onResponse(call, response);
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                uploadListener.onFailure(call, t);
            }
        });
        return model;
    }

    private static <T> void parseEnhancedCall(Call<ResponseBody> call, HttpResponseListener<T> httpResponseListener, String apiUrl) {
        //请求网络数据
        EnhancedCall<ResponseBody> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.setContext(mContext)
                .useCache(mUseCache)/*默认支持缓存*/
                .enqueue(new EnhancedCallback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                String json = response.body() != null ? (response.body()).string() : "";
                                if (L.isDebug) {
                                    L.i(call.request().method() + "返回路径：" + sBaseUrl + apiUrl + "，\nresponse data:" + JSONUtil.formatJSONString(json));
                                }
                                mHttpResponseYu.onResponse(mContext, response, json, httpResponseListener);
                            } else {
                                if (!mUseCache || NetworkUtils.isNetworkConnected(mContext)) {
                                    //不使用缓存 或者网络可用 的情况下直接回调onFailure
                                    if (L.isDebug) {
                                        L.e(call.request().method() + "返回路径：" + sBaseUrl + apiUrl + ",错误码：" + response.code() + "，错误信息：" + response.message());
                                    }
                                    mHttpResponseYu.onError(mContext, response.code(), response.message(), httpResponseListener);
                                    return;
                                }
                                requestCache(call, httpResponseListener);
                            }
                        } catch (Exception var6) {
                            if (L.isDebug) {
                                L.e("请求路径：" + sBaseUrl + apiUrl + " Http Exception:", var6.getMessage() + "");
                            }
                            mHttpResponseYu.onFailure(mContext, call, var6, httpResponseListener);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        LogUtils.d("onError->" + t.getMessage());
                        httpResponseListener.onFailure(call, t);
                    }

                    @Override
                    public void onGetCache(String cacheJson) {
                        mHttpResponseYu.onGetCache(mContext, cacheJson, httpResponseListener);
                        LogUtils.d("onGetCache" + cacheJson);
                    }
                });
    }

    /**
     * 说明：请求缓存
     */
    private static <T> void requestCache(Call<ResponseBody> call, HttpResponseListener<T> httpResponseListener) {
        okhttp3.Request request = call.request();
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
        if (L.isDebug) {
            L.i("get cache->" + cache);
        }
        if (!TextUtils.isEmpty(cache)) {
            mHttpResponseYu.onGetCache(mContext, cache, httpResponseListener);
            return;
        }
        mHttpResponseYu.onError(mContext, -1, "没有缓存数据", httpResponseListener);
    }

    public interface HttpService<T> {
        @GET
        Call<ResponseBody> get(@Url String var1, @HeaderMap Map<String, String> var2, @QueryMap Map<String, Object> var3);

        @FormUrlEncoded
        @POST
        Call<ResponseBody> post(@Url String var1, @HeaderMap Map<String, String> var2, @FieldMap Map<String, Object> var3);

        @POST
        Call<ResponseBody> post(@Url String var1, @HeaderMap Map<String, String> var2, @Body Object entity);

        @Multipart
        @POST
        Call<String> postUpload(@Url String var1, @Part("filedes") String var2, @PartMap Map<String, RequestBody> var3);

        @Multipart
        @GET
        Call<String> getUpload(@Url String var1, @Part("filedes") String var2, @PartMap Map<String, RequestBody> var3);
    }
}
