package com.ks.basictools.java.override;

import com.ks.basictools.base_interface.StringCallBack;
import com.ks.basictools.utils.OkHttpUtil;

/**
 * author：康少
 * date：2019/6/13
 * description：OkHttp工具封装类
 */
public class OkHttp extends OkHttpUtil {
    private StringCallBack stringCallBack;

    public OkHttp(StringCallBack stringCallBack) {
        if (stringCallBack == null) {
            try {
                throw new Exception("stringCallBack不能为空！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.stringCallBack = stringCallBack;
        }
    }

    @Override
    protected String getBaseUrl() {
        return "http://10.10.0.59:8080/";
    }

    @Override
    protected StringCallBack getStringCallBack() {
        return stringCallBack;
    }
}
