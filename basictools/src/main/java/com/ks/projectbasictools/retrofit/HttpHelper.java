package com.ks.projectbasictools.retrofit;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.ks.projectbasictools.helper.OkHttpClientHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
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

public final class HttpHelper {
    private static Context mContext;
    private static volatile WeakReference<HttpHelper> sInstance;
    private final Retrofit mRetrofit;
    private static String sBaseUrl;

    public static void setBaseUrl(Context context, String baseUrl) {
        mContext = context;
        sBaseUrl = baseUrl;
    }

    public static String getBaseUrl() {
        return sBaseUrl;
    }

    private HttpHelper() {
        Builder builder = new Builder();
        if (TextUtils.isEmpty(getBaseUrl())) {
            throw new NullPointerException("init(Context,httpBaseUrl)：httpBaseUrl is not null");
        } else {
            this.mRetrofit = builder
                    .baseUrl(getBaseUrl())
                    .client(OkHttpClientHelper.getInstance(mContext).getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static HttpHelper getInstance() {
        if (sInstance == null || sInstance.get() == null) {
            Class var0 = HttpHelper.class;
            synchronized(HttpHelper.class) {
                if (sInstance == null || sInstance.get() == null) {
                    sInstance = new WeakReference(new HttpHelper());
                }
            }
        }

        return (HttpHelper)sInstance.get();
    }

    public static <T> Call getAsync(String apiUrl, @HeaderMap Map<String, Object> headers, Map<String, Object> paramMap, HttpResponseListener<T> httpResponseListener) {
        if (paramMap == null) {
            paramMap = new HashMap();
        }

        if (headers == null) {
            headers = new HashMap();
        }

        HttpHelper.HttpService httpService = (HttpHelper.HttpService)getInstance().mRetrofit.create(HttpHelper.HttpService.class);
        Call<ResponseBody> call = httpService.get(apiUrl, (Map)headers, (Map)paramMap);
        parseNetData(call, httpResponseListener);
        return call;
    }

    public static <T> Call postAsync(String apiUrl, @HeaderMap Map<String, Object> headers, Map<String, Object> paramMap, HttpResponseListener<T> httpResponseListener) {
        if (paramMap == null) {
            paramMap = new HashMap();
        }

        if (headers == null) {
            headers = new HashMap();
        }

        HttpHelper.HttpService httpService = (HttpHelper.HttpService)getInstance().mRetrofit.create(HttpHelper.HttpService.class);
        Call<ResponseBody> call = httpService.post(apiUrl, (Map)headers, (Map)paramMap);
        parseNetData(call, httpResponseListener);
        return call;
    }

    public static Call upload(Request request, final UploadListener uploadListener) {
        if (request.getUploadFiles() == null || !((File)request.getUploadFiles().get(0)).exists()) {
            (new FileNotFoundException("file does not exist(文件不存在)")).printStackTrace();
        }

        Map<String, RequestBody> requestBodyMap = new HashMap();
        RequestBody requestBody = RequestBody.create(request.getMediaType(), (File)request.getUploadFiles().get(0));
        requestBodyMap.put("file[]\"; filename=\"" + ((File)request.getUploadFiles().get(0)).getName(), requestBody);
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
        HttpHelper.HttpService service = (HttpHelper.HttpService)retrofit.create(HttpHelper.HttpService.class);
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

    private static <T> void parseNetData(Call<ResponseBody> call, final HttpResponseListener<T> httpResponseListener) {
        call.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = ((ResponseBody)response.body()).string();
                    if (L.isDebug) {
                        L.i("response data:" + json);
                    }

                    if (!String.class.equals(httpResponseListener.getType())) {
                        Gson gson = new Gson();
                        T t = gson.fromJson(json, httpResponseListener.getType());
                        httpResponseListener.onResponse(t);
                    } else {
                        httpResponseListener.onResponse((T) json);
                    }
                } catch (Exception var6) {
                    if (L.isDebug) {
                        L.e("Http Exception:", var6);
                    }

                    httpResponseListener.onFailure(call, var6);
                }

            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                httpResponseListener.onFailure(call, t);
            }
        });
    }

    public interface HttpService<T> {
        @GET
        Call<ResponseBody> get(@Url String var1, @HeaderMap Map<String, String> var2, @QueryMap Map<String, Object> var3);

        @FormUrlEncoded
        @POST
        Call<ResponseBody> post(@Url String var1, @HeaderMap Map<String, String> var2, @FieldMap Map<String, Object> var3);

        @Multipart
        @POST
        Call<String> postUpload(@Url String var1, @Part("filedes") String var2, @PartMap Map<String, RequestBody> var3);

        @Multipart
        @GET
        Call<String> getUpload(@Url String var1, @Part("filedes") String var2, @PartMap Map<String, RequestBody> var3);
    }
}
