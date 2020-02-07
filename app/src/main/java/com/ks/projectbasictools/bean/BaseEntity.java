package com.ks.projectbasictools.bean;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    /**
     * code : 000000
     * mesg : success
     * timestamp : 2020-01-20T01:56:29.701742300Z
     */

    private String code;
    private String mesg;
    private String timestamp;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMesg() {
        return mesg;
    }
    public void setMesg(String mesg) {
        this.mesg = mesg;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
