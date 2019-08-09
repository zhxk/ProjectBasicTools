package com.ks.projectbasictools;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;

import com.ks.projectbasictools.utils.ActivityStackUtil;
import com.ks.projectbasictools.utils.LogUtils;
import com.ks.projectbasictools.utils.ToastUtil;

/**
 * author：康少
 * date：2019/2/20
 * description：基础Application  网络监听
 */
public class BaseApplication extends Application {
    private OnNoNetworkListener onNoNetworkListener;
    /**
     * Description：无网络刷新外部接口
     */
    public void setOnNoNetworkListener(OnNoNetworkListener onNoNetworkListener) {
        this.onNoNetworkListener = onNoNetworkListener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //activity的侧滑返回
        ActivityStackUtil.getInstance().init(this);
        /*网络改变监听*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            // 请注意这里会有一个版本适配bug，所以请在这里添加非空判断
            if (connectivityManager != null) {
                connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                    /**
                     * 网络可用的回调
                     * */
                    @Override
                    public void onAvailable(Network network) {
                        super.onAvailable(network);
                        LogUtils.e("onAvailable()网络可用的回调");
                        if (onNoNetworkListener != null) {
                            onNoNetworkListener.doRefresh();
                        }
                    }
                    /**
                     * 网络丢失的回调
                     * */
                    @Override
                    public void onLost(Network network) {
                        super.onLost(network);
                        LogUtils.e("onLost()网络丢失的回调");
                        ToastUtil.show(BaseApplication.this, "网络错误");
                    }
                });
            }
        }
    }

    /**
     * author：康少
     * date：2018/11/12
     * description：无网络监听
     */
    public interface OnNoNetworkListener{
        void doRefresh();
    }
}
