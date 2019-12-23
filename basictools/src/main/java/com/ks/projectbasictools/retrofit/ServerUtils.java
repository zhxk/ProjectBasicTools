package com.ks.projectbasictools.retrofit;

import java.util.Map;

public class ServerUtils {

    public static <T> void requestGet(String url, Map<String, Object> map, HttpResponseListener<T> listener) {
        Request request = ServerHttp.newGetRequest(url);
        request.putParamsMap(map);
        ServerHttp.send(request, listener);
    }

    public static <T> void requestPost(String url, Map<String, Object> map, HttpResponseListener<T> listener) {
        Request request = ServerHttp.newPostRequest(url);
        request.putParamsMap(map);
        ServerHttp.send(request, listener);
    }
}
