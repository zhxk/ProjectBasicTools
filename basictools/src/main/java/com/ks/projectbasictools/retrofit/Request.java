package com.ks.projectbasictools.retrofit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;

public class Request {
    private Map<String, Object> mHeaderMap;
    private Map<String, Object> mParamsMap;
    private List<File> mUploadFiles;
    private String mApiUlr;
    private RequestMethod mRequestMethod;
    private MediaType mMediaType;

    public Request(String apiUlr, RequestMethod method) {
        this.mRequestMethod = RequestMethod.GET;
        this.mMediaType = MediaType.parse("application/otcet-stream");
        this.mApiUlr = apiUlr;
        this.mRequestMethod = method;
    }

    public Request putHeader(String key, Object value) {
        if (this.mHeaderMap == null) {
            this.mHeaderMap = new HashMap();
        }

        this.mHeaderMap.put(key, value);
        return this;
    }

    public void putHeaderMap(Map<String, Object> headerMap) {
        if (this.mHeaderMap != null) {
            this.mHeaderMap.putAll(headerMap);
        } else {
            this.mHeaderMap = headerMap;
        }

    }

    public Request putParams(String key, Object value) {
        if (this.mParamsMap == null) {
            this.mParamsMap = new HashMap();
        }

        this.mParamsMap.put(key, value);
        return this;
    }

    public void putParamsMap(Map<String, Object> paramMap) {
        if (this.mParamsMap != null) {
            this.mParamsMap.putAll(paramMap);
        } else {
            this.mParamsMap = paramMap;
        }

    }

    public Request putMediaType(MediaType mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    public MediaType getMediaType() {
        return this.mMediaType;
    }

    public Request putUploadFile(File uploadFile) {
        if (this.mUploadFiles == null) {
            this.mUploadFiles = new ArrayList(3);
        }

        this.mUploadFiles.add(uploadFile);
        return this;
    }

    public List<File> getUploadFiles() {
        return this.mUploadFiles;
    }

    public Map<String, Object> getHeaderMap() {
        return this.mHeaderMap;
    }

    public Map<String, Object> getParamsMap() {
        return this.mParamsMap;
    }

    public String getApiUlr() {
        return this.mApiUlr;
    }

    public RequestMethod getRequestMethod() {
        return this.mRequestMethod;
    }
}
