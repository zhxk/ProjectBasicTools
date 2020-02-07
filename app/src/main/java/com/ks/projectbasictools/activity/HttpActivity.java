package com.ks.projectbasictools.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ks.projectbasictools.R;
import com.ks.projectbasictools.adapter.NewsApapter;
import com.ks.projectbasictools.base.BaseActivity;
import com.ks.projectbasictools.base_interface.StringCallBack;
import com.ks.projectbasictools.bean.NewsEntity;
import com.ks.projectbasictools.constants.AppConstants;
import com.ks.projectbasictools.okhttp.OkHttpUtils;
import com.ks.projectbasictools.utils.ToastKs;

import java.util.List;

import okhttp3.Call;

public class HttpActivity extends BaseActivity {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private NewsApapter myAdapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_cache);
        init();
    }

    private void init() {
        refreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.rv);
        manager = new LinearLayoutManager(this);
        myAdapter = new NewsApapter(this,null);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(myAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();
    }

    private void getData() {
        if (!refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(true);
        }
        OkHttpUtils.requestGet(this, AppConstants.HTTP.NEWS, null, new StringCallBack<NewsEntity>() {
            @Override
            public void onResponse(NewsEntity response, int id) {
                getDataSuccess(response.getStories());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                getDataFailure(e.getMessage());
            }
        });
    }

    private void getDataSuccess(List<NewsEntity.StoriesBean> list){
        myAdapter.setNewData(list);

        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    private void getDataFailure(String msg) {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        ToastKs.show(HttpActivity.this, "加载失败，失败原因：" + msg);
    }
}
