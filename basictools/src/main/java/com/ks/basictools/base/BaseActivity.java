package com.ks.basictools.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.ks.basictools.ActivityCollector;
import com.ks.basictools.AppManager;
import com.ks.basictools.R;
import com.ks.basictools.overView.LoadingDialog;
import com.ks.basictools.publicView.SlideBackLayout;
import com.ks.basictools.utils.DisplayUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Date:2019/2/20
 * Author：康少
 * Description：BaseActivity是所有Activity的基类，把一些公共的方法放到里面，如基础样式设置，权限封装等
 */
public abstract class BaseActivity extends AppCompatActivity {

    /*全局加载中Dialog*/
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*侧滑返回 在 super.onCreate(savedInstanceState);之前调用此方法*/
//        new SlideBackLayout(this).attach2Activity(this, null);
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        /*设置状态栏*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            //状态栏中的文字颜色和图标颜色为深色，需要android系统6.0以上，而且目前只有一种可以修改（一种是深色，一种是浅色即白色）
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //设置状态栏的颜色
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        //添加到Activity管理
        ActivityCollector.addActivity(this, getClass());
        //将Activity实例添加到AppManager的堆栈
        AppManager.getAppManager().addActivity(this);
        //全局加载中···
        mLoadingDialog = LoadingDialog.getInstance();
    }

    /**
     * 设置状态栏是否全屏
     * @param isFull         true：全屏；false：非全屏
     * @param isUIBlack       true：图标文字为深色；false：图标文字为亮色
     * @param backgroundColor 状态栏背景色 R.color.xxx
     */
    public void setStatusBar(boolean isFull, boolean isUIBlack, int backgroundColor) {
        /*设置状态栏*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int vis;
            //View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：设置状态栏中的文字颜色和图标颜色为深色，不设置默认为白色，需要android系统6.0以上。
            if (isFull) {
                if (isUIBlack) {
                    vis = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE;
                }
            } else {
                if (isUIBlack) {
                    vis = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis = View.SYSTEM_UI_FLAG_VISIBLE;
                }
            }
            decorView.setSystemUiVisibility(vis);
            if (backgroundColor != 0) {
                //设置状态栏规定的颜色
                getWindow().setStatusBarColor(getResources().getColor(backgroundColor));
            } else {
                //设置状态栏的默认背景颜色为白色
                getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            }
        }
    }
    public void setStatusBar(boolean isFull, boolean isUIBlack) {
        setStatusBar(isFull, isUIBlack, 0);
    }

    /**
     * Description：显示加载中。。。
     */
    public void showLoading() {
        if (!AppManager.getAppManager().currentActivity().isFinishing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * Description：监听收起或者展开评论输入键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                //触摸其他地方，关闭输入框
                DisplayUtils.hideInputWhenTouchOtherView(this, ev, null);
                break;
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * Description：关掉加载中。。。
     */
    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    public void startAct(Context packageContext, Class<?> cls) {
        startAct(packageContext, cls, 0, null, 0, 0);
    }

    public void startAct(Context packageContext, Class<?> cls, int RESULT_CODE) {
        startAct(packageContext, cls, RESULT_CODE, null, 0, 0);
    }

    public void startAct(Context packageContext, Class<?>
            cls, Map<String, Object> map) {
        startAct(packageContext, cls, 0, map, 0, 0);
    }

    public void startAct(Context packageContext, Class<?> cls, int RESULT_CODE,
            Map<String, Object> map) {
        startAct(packageContext, cls, RESULT_CODE, map, 0, 0);
    }

    public void startAct(Context packageContext, Class<?> cls, int RESULT_CODE,
            Map<String, Object> map, int enterAnim) {
        startAct(packageContext, cls, RESULT_CODE, map, enterAnim, 0);
    }

    /**
     * 跳转activity
     * 注：跳转默认动画，从下往上进入Activity
     *
     * @param packageContext 不解释
     * @param cls            目标activity类
     * @param RESULT_CODE    跳转返回code
     * @param map           参数map集合
     * @param enterAnim      打开activity动画
     * @param exitAnim       关闭activity动画
     */
    public void startAct(Context packageContext, Class<?> cls, int RESULT_CODE,
            Map<String, Object> map, int enterAnim, int exitAnim) {
        Intent intent = new Intent(packageContext, cls);
        //循环添加参数
        if (map != null) {
            for (String k : map.keySet()) {
                if (map.get(k) instanceof Boolean) {
                    intent.putExtra(k, (boolean) map.get(k));
                } else if (map.get(k) instanceof Byte) {
                    intent.putExtra(k, (byte) map.get(k));
                } else if (map.get(k) instanceof String) {
                    intent.putExtra(k, (String) map.get(k));
                } else if (map.get(k) instanceof Integer) {
                    intent.putExtra(k, (int) map.get(k));
                } else if (map.get(k) instanceof Long) {
                    intent.putExtra(k, (long) map.get(k));
                } else if (map.get(k) instanceof Double) {
                    intent.putExtra(k, (double) map.get(k));
                } else if (map.get(k) instanceof Float) {
                    intent.putExtra(k, (float) map.get(k));
                } else if (map.get(k) instanceof Serializable) {
                    intent.putExtra(k, (Serializable) map.get(k));
                }
            }
        }
        //设置回调
        if (RESULT_CODE == 0) {
            AppManager.getAppManager().currentActivity().startActivity(intent);
        } else {
            AppManager.getAppManager().currentActivity().startActivityForResult(intent, RESULT_CODE);
        }
        //自定义跳转动画
        if (enterAnim != 0 || exitAnim != 0) {
            overridePendingTransition(enterAnim, exitAnim);
        } else {
            overridePendingTransition(R.anim.alpha_in, 0);
        }
    }

    public void startAct(Intent intent) {
        startAct(intent, 0, 0, 0);
    }

    public void startAct(Intent intent, int RESULT_CODE) {
        startAct(intent, RESULT_CODE, 0, 0);
    }

    public void startAct(Intent intent, int enterAnim, int exitAnim) {
        startAct(intent, 0, enterAnim, exitAnim);
    }

    /**
     * 跳转activity
     * 注：跳转默认动画，从下往上进入Activity
     *
     * @param intent      intent参数
     * @param RESULT_CODE 回调参数
     * @param enterAnim   进入动画
     * @param exitAnim    退出动画
     */
    public void startAct(Intent intent, int RESULT_CODE, int enterAnim, int exitAnim) {
        //设置回调
        if (RESULT_CODE == 0) {
            AppManager.getAppManager().currentActivity().startActivity(intent);
        } else {
            AppManager.getAppManager().currentActivity().startActivityForResult(intent, RESULT_CODE);
        }
        //自定义跳转动画
        if (enterAnim != 0 || exitAnim != 0) {
            overridePendingTransition(enterAnim, exitAnim);
        } else {
            overridePendingTransition(R.anim.alpha_in, 0);
        }
    }

    public void finishAct() {
        finishAct(0, 0);
    }

    public void finishAct(int exitAnim) {
        finishAct(0, exitAnim);
    }

    /**
     * 退出activity
     * <p>
     * 注：跳转默认动画，从上往下离开Activity
     *
     * @param enterAnim 进入动画
     * @param exitAnim  退出动画
     */
    public void finishAct(int enterAnim, int exitAnim) {
        AppManager.getAppManager().finishActivity();
        //自定义跳转动画
        if (enterAnim != 0 || exitAnim != 0) {
            overridePendingTransition(enterAnim, exitAnim);
        } else {
            overridePendingTransition(0, R.anim.alpha_out);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        // Activity销毁时，提示系统回收
        System.gc();
        // 移除Activity
        ActivityCollector.removeActivity(this);
        //销毁加载中控件
        dismissLoading();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityCollector.removeActivity(this);
            finishAct();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults 结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequestPermissionsResult(requestCode, grantResults);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param grantResults 结果集
     */
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
    }
}
