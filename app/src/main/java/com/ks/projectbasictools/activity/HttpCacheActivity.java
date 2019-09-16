package com.ks.projectbasictools.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ks.projectbasictools.R;
import com.ks.projectbasictools.adapter.NewsApapter;
import com.ks.projectbasictools.api.NewsApi;
import com.ks.projectbasictools.base.BaseActivity;
import com.ks.projectbasictools.bean.NewsEntity;
import com.ks.projectbasictools.constants.AppConstants;
import com.ks.projectbasictools.helper.OkHttpClientHelper;
import com.ks.projectbasictools.override.RetrofitHttp;
import com.ks.projectbasictools.utils.ToastKs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpCacheActivity extends BaseActivity {
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
        Retrofit retrofit =RetrofitHttp.getInstance().getRetrofit(HttpCacheActivity.this);

        NewsApi newsApi = retrofit.create(NewsApi.class);
        newsApi.getNews().enqueue(new Callback<NewsEntity>() {
            @Override
            public void onResponse(Call<NewsEntity> call, Response<NewsEntity> response) {
                //请求成功
                getDataSuccess(response.body().getStories());
            }

            @Override
            public void onFailure(Call<NewsEntity> call, Throwable t) {
                //请求失败
                getDataFailure(t.getMessage());
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
        ToastKs.show(HttpCacheActivity.this, "加载失败，失败原因：" + msg);
    }
}
