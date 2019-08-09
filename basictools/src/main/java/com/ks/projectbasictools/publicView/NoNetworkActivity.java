package com.ks.projectbasictools.publicView;

import android.os.Bundle;

import com.ks.projectbasictools.R;

/**
 * Date:2019/2/20
 * Author：康少
 * Description：无网络界面
 */
public class NoNetworkActivity extends TitleVarView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_network);
        setTitleBar(true,"返回","暂无网络","");
    }
}
