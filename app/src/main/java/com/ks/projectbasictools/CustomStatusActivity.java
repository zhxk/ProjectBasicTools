package com.ks.projectbasictools;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ks.projectbasictools.base.BaseActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomStatusActivity extends BaseActivity {

    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.switch2)
    Switch switch2;
    private Integer[] colors = new Integer[]{R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.black, R.color.white};

    boolean isWhite = false;
    private boolean isFull = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_status);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isWhite = true;
                } else {
                    isWhite = false;
                }
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isFull = true;
                } else {
                    isFull = false;
                }
            }
        });
    }

    /**
     * @Desc 默认
     */
    public void statusBarDefault(View view) {
        setStatusBar(isFull, isWhite);
    }

    /**
     * @Desc 透明
     */
    public void statusBarTran(View view) {
        setStatusBar(isFull, isWhite, R.color.colorTransparent);
    }

    /**
     * @Desc 自定义颜色
     */
    public void statusBarCustom(View view) {
        //生成10到30之间的数字
        int max = 4;
        int min = 0;
        int num = new Random().nextInt(max - min + 1) + min;
        setStatusBar(isFull, isWhite, colors[num]);
    }
}
