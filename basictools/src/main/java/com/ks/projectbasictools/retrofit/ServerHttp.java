package com.ks.projectbasictools.retrofit;

import android.content.Context;

import java.util.Map;
import retrofit2.Call;

public class ServerHttp {
    public ServerHttp() {
    }

    public static final void init(Context context, String httpBaseUrl) {
        HttpHelper.setBaseUrl(context, httpBaseUrl);
    }

    public static <T> Call getAsync(String apiUrl, HttpResponseListener<T> httpResponseListener) {
        return HttpHelper.getAsync(apiUrl, (Map)null, (Map)null, httpResponseListener);
    }

    public static <T> Call postAsync(String apiUrl, HttpResponseListener<T> httpResponseListener) {
        return HttpHelper.postAsync(apiUrl, (Map)null, (Map)null, httpResponseListener);
    }

    public static <T> Call send(Request request, HttpResponseListener<T> httpResponseListener) {
        return RequestMethod.GET.equals(request.getRequestMethod()) ? HttpHelper.getAsync(request.getApiUlr(), request.getHeaderMap(), request.getParamsMap(), httpResponseListener) : HttpHelper.postAsync(request.getApiUlr(), request.getHeaderMap(), request.getParamsMap(), httpResponseListener);
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
}
