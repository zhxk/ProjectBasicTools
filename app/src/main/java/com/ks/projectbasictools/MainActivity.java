package com.ks.projectbasictools;

import android.os.Bundle;
import android.view.View;

import com.ks.projectbasictools.base.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startCustomStatusBar(View view) {
        startAct(this, CustomStatusActivity.class);
    }

    public void startCustomToast(View view) {
        startAct(this, CustomToastActivity.class);
    }

    public void startStartAct(View view) {
        startAct(this, CustomStartActActivity.class);
    }
}
