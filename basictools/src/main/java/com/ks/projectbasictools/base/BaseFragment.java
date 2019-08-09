package com.ks.projectbasictools.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ks.projectbasictools.R;

/**
 * Date:2019/2/20
 * Author：康少
 * Description：BaseFragment是所有Fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    // 抽象 - 初始化方法，可以对控件进行初始化，也可以对数据进行初始化
    protected abstract void init();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*设置状态栏*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.getActivity() != null) {
                View decorView = this.getActivity().getWindow().getDecorView();
                //状态栏中的文字颜色和图标颜色为深色，需要android系统6.0以上，而且目前只有一种可以修改（一种是深色，一种是浅色即白色）
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                //设置状态栏的颜色
                this.getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            }
        }

        // 初始化方法
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
