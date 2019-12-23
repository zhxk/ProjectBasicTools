package com.ks.projectbasictools.override;

import android.content.Context;

import com.google.gson.Gson;
import com.ks.projectbasictools.base_interface.StringCallBack;
import com.ks.projectbasictools.okhttp.StringCallbackYu;
import com.ks.projectbasictools.utils.LogUtils;
import com.ks.projectbasictools.utils.ToastKs;
import com.ks.projectbasictools.utils.ToastUtil;

import okhttp3.Call;

/**
 * 作者：康少
 * 时间：2019/12/23 0023
 * 说明：请求回调预加载
 */
public class StringCallBackYu<T> extends StringCallbackYu {

    @Override
    public void onResponse(Context mContext, String response, int id, StringCallBack mStringCallBack, String requestType) {
        if (LogUtils.isDebug)
            LogUtils.d(requestType + "访问response:" + formatJSONString(response));
        Gson gson = new Gson();
        T t = gson.fromJson(response, mStringCallBack.getType());
        mStringCallBack.onResponse(t, id);
    }

    @Override
    public void onError(Context mContext, Call call, Exception e, int id, StringCallBack mStringCallBack, String requestType) {
        if (LogUtils.isDebug)
            LogUtils.d(requestType + "访问ErrorMessage:" + e.getMessage());
        if (e.getMessage().equals("timeout")) {
            ToastKs.show(mContext, "网络走丢了，请稍后再试");
        } else {
            ToastUtil.show(mContext, "error:" + e.getMessage());
        }
        mStringCallBack.onError(call, e, id);
    }

    /**
     * @Desc JSON字符串格式化
     */
    static String formatJSONString(String JSONString) {
        int level = 0;
        //存放格式化的json字符串
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int index = 0; index < JSONString.length(); index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = JSONString.charAt(index);

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c).append("\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c).append("\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return String.valueOf(jsonForMatStr);
    }

    private static String getLevelStr(int level) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
