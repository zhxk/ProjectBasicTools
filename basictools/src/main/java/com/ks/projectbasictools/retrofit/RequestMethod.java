package com.ks.projectbasictools.retrofit;

public enum RequestMethod {
    GET("GET"),
    POST("POST");

    private final String value;

    private RequestMethod(String value) {
        this.value = value;
    }
}
