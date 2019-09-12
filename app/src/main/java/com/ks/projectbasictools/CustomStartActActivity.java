package com.ks.projectbasictools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ks.projectbasictools.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("SetTextI18n")
public class CustomStartActActivity extends BaseActivity {

    private final int CODE_RESULT = 0x1654;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_start_act);
        ButterKnife.bind(this);
    }

    public void startActOnDefault(View view) {
        textView.setText("这是第一个 Activity");
        startAct(this, SecondActivity.class);
    }

    /**
     * @Desc 自定义样式跳转
     */
    public void startActOnCustom(View view) {
        textView.setText("这是第一个 Activity");
        Map<String, Object> map = new HashMap<>();
        map.put("param1", "自定义样式跳转");
        startAct(this, SecondActivity.class, map, R.anim.activity_in);
    }

    /**
     * @Desc 带参数回调的跳转
     */
    public void startActOnParam(View view) {
        Map<String, Object> map = new HashMap<>();
        map.put("param1", "param1 = 好棒的跳转");
        map.put("param2", "param2 = 好丑的跳转");
        startActForResult(this, SecondActivity.class, CODE_RESULT, map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODE_RESULT) {
                String data1 = data.getStringExtra("data");
                textView.setText(data1);
            }
        }
    }
}
