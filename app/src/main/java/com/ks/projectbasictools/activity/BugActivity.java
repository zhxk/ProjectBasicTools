package com.ks.projectbasictools.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ks.projectbasictools.R;
import com.ks.projectbasictools.base.BaseActivity;
import com.ks.projectbasictools.utils.FixDexUtil;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BugActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);
        ButterKnife.bind(this);
        requestPermission();
    }

    private void requestPermission() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermission(0x0099, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x0099) {

        }
    }

    private void init() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();

        // 遍历所有的修复dex , 因为可能是多个dex修复包
        File fileDir = externalStorageDirectory != null ?
                new File(externalStorageDirectory,"007"):
                new File(getFilesDir(), FixDexUtil.DEX_DIR);// data/user/0/包名/files/odex（这个可以任意位置）
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        if (FixDexUtil.isGoingToFix(this)) {
            FixDexUtil.loadFixedDex(this, Environment.getExternalStorageDirectory());
            /*ToastUtil.show(this, "正在修复。。。。");*/

        }
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtil.show(BugActivity.this, "修复完成2");
            }
        },3000);*/
    }

    /**
     * bug测试类
     */
    @OnClick({R.id.btn_click, R.id.btn_splash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
                Toast.makeText(this, "这是一个优美的bog?", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_splash:
                init();
                break;
        }
    }
}
