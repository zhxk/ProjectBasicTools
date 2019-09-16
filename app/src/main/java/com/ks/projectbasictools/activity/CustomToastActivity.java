package com.ks.projectbasictools.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ks.projectbasictools.R;
import com.ks.projectbasictools.base.BaseActivity;
import com.ks.projectbasictools.utils.ToastKs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomToastActivity extends BaseActivity {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_toast);
        ButterKnife.bind(this);
    }

    public void showToast(View view) {
        String toastMsg = editText.getText().toString();
        if (toastMsg.equals("")) {
            toastMsg = "EditText为空";
        }
        ToastKs.show(this, toastMsg);
    }

    /**
      * @Desc 转圈的颜色为color.xml的主题色
      */
    public void showLoading(View view) {
        String loadText = editText2.getText().toString();
        if (loadText.equals("")) {
            showLoading();
        } else {
            showLoading(loadText);
        }
    }
}