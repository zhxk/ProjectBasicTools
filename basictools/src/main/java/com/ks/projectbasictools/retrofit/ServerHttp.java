package com.ks.projectbasictools.retrofit;

import android.content.Context;

import java.util.Map;

import retrofit2.Call;

public class ServerHttp {
    public ServerHttp() {
    }

    /**
     * 初始化
     *
     * @param context
     * @param httpBaseUrl    基础请求链接
     * @param httpResponseYu 请求返回预处理类——需重写
     * @param useCache       是否开启网络缓存，默认开启。支持get、post请求方式
     */
    public static <T> void init(Context context, String httpBaseUrl, HttpResponseYu<T> httpResponseYu, boolean useCache) {
        HttpHelper.setBaseUrl(context, httpBaseUrl);
        HttpHelper.setHttpResponseYu(httpResponseYu);
        HttpHelper.setOpenCache(useCache);
    }

    public static <T> void init(Context context, String httpBaseUrl, HttpResponseYu<T> httpResponseYu) {
        init(context, httpBaseUrl, httpResponseYu, false);
    }

    public static <T> Call getAsync(String apiUrl, HttpResponseListener<T> httpResponseListener) {
        return HttpHelper.getAsync(apiUrl, (Map) null, (Map) null, httpResponseListener);
    }

    public static <T> Call postAsync(String apiUrl, HttpResponseListener<T> httpResponseListener) {
        return HttpHelper.postAsync(apiUrl, (Map) null, (Map) null, httpResponseListener);
    }

    public static <T> Call send(Request request, HttpResponseListener<T> httpResponseListener) {
        return RequestMethod.GET.equals(request.getRequestMethod()) ?
                HttpHelper.getAsync(request.getApiUlr(), request.getHeaderMap(), request.getParamsMap(), httpResponseListener)
                : HttpHelper.postAsync(request.getApiUlr(), request.getHeaderMap(), request.getParamsMap(), httpResponseListener);
    }

    public static <T> Call sendJson(Request request, Object requestObj, HttpResponseListener<T> httpResponseListener) {
        return RequestMethod.GET.equals(request.getRequestMethod()) ?
                HttpHelper.getAsync(request.getApiUlr(), request.getHeaderMap(), request.getParamsMap(), httpResponseListener)
                : HttpHelper.postAsync(request.getApiUlr(), request.getHeaderMap(), requestObj, httpResponseListener);
    }

    public static <T> Call upload(Request request, UploadListener uploadListener) {
        return HttpHelper.upload(request, uploadListener);
    }

    public static Request newRequest(String apiUlr, RequestMethod method) {
        return new Request(apiUlr, method);
    }

    public static Request newPostRequest(String apiUlr) {
        return new Request(apiUlr, RequestMethod.POST);
    }

    public static Request newUploadRequest(String uploadFileUrl, RequestMethod method) {
        return new Request(uploadFileUrl, method);
    }

    public static Request newGetRequest(String apiUlr) {
        return new Request(apiUlr, RequestMethod.GET);
    }

    public static void setDebug(boolean isDebug) {
        L.isDebug = isDebug;
    }

    public static void setUseCache(boolean useCache) {
        HttpHelper.setOpenCache(useCache);
    }
}
