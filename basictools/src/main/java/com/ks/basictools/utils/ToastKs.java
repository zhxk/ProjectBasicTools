package com.ks.basictools.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ks.basictools.R;

/**
 * author：康少
 * date：2019/3/21
 * description：自定义Toast工具
 */
public class ToastKs {
    private static long lastTime = 0;

    /**
     * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     */
    public static void show(Activity activity, String messages) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 2000L) {
            //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
            LayoutInflater inflater = activity.getLayoutInflater();//调用Activity的getLayoutInflater()
            View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
            TextView text = view.findViewById(R.id.tvTextToast);
            text.setText(messages); //toast内容
            Toast toast = new Toast(activity);
            toast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
            toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
            toast.setView(view); //添加视图文件
            toast.show();
            lastTime = currentTime;
        }
    }
}
