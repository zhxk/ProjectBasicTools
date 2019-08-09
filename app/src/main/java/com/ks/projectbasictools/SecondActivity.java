package com.ks.projectbasictools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ks.projectbasictools.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends BaseActivity {

    @BindView(R.id.textView2)
    TextView textView2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        String param1 = getIntent().getStringExtra("param1");
        String param2 = getIntent().getStringExtra("param2");
        textView2.setText(param1 + "\n" + param2);
    }

    public void finishActivity(View view) {
        Intent intent = new Intent();
        intent.putExtra("data", "回调的文字数据");
        setResult(Activity.RESULT_OK, intent);
        SecondActivity.this.finishAct(R.anim.activity_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityCollector.removeActivity(this);
            finishAct(R.anim.activity_out);
        }
        //返回true，不再调用父类onKeyDown方法
        return true;
    }
}
