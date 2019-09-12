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
    private String param1;
    private String param2;
    private int finishDh = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        param1 = getIntent().getStringExtra("param1");
        param2 = getIntent().getStringExtra("param2");
        if (param1 == null) {
            textView2.setText("第二个 Activity");
        } else if (param1.equals("自定义样式跳转")) {
            textView2.setText("第二个 Activity");
            finishDh = R.anim.activity_out;
        } else {
            textView2.setText("这是第一个 Activity传过来的两个参数：\n" + param1 + "\n" + param2);
        }
    }

    public void finishActivity(View view) {
        Intent intent = new Intent();
        intent.putExtra("data", "这是第二个 Activity回传过来的两个参数：\n" + param1 + "\n" + param2);
        setResult(Activity.RESULT_OK, intent);
        SecondActivity.this.finishAct(finishDh);
    }

    /**
     * 注意：自定义finish样式时，需要重写此方法。并设置finishAct(int p)中的p 样式。并且必须 return true ！！！
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityCollector.removeActivity(this);
            finishAct(finishDh);
        }
        //返回true，不再调用父类onKeyDown方法
        return true;
    }
}
