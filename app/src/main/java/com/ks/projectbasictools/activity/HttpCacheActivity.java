package com.ks.projectbasictools.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ks.projectbasictools.R;
import com.ks.projectbasictools.adapter.NewsApapter;
import com.ks.projectbasictools.base.BaseActivity;
import com.ks.projectbasictools.bean.NewsEntity;
import com.ks.projectbasictools.constants.AppConstants;
import com.ks.projectbasictools.retrofit.HttpResponseListener;
import com.ks.projectbasictools.retrofit.ServerUtils;
import com.ks.projectbasictools.utils.ToastKs;
import com.ks.projectbasictools.utils.ToastUtil;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class HttpCacheActivity extends BaseActivity {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private NewsApapter myAdapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_cache);

        initPermission();
    }

    //Android 6.0以上的权限申请
    private void initPermission() {
        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermission(0, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            init();
        }
    }

    private void init() {
        refreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.rv);
        manager = new LinearLayoutManager(this);
        myAdapter = new NewsApapter(this, null);

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
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }

        /*Map<String, Object> map = new HashMap<>();
        map.put("key", "value");*/
        ServerUtils.requestGet(AppConstants.HTTP.NEWS, null, new HttpResponseListener<NewsEntity>() {
            @Override
            public void onResponse(NewsEntity newsEntity) {
                //请求成功
                getDataSuccess(newsEntity.getStories());
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtil.show(HttpCacheActivity.this, "错误码：" + code + "；错误信息：" + msg);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                //请求失败
                getDataFailure(e.getMessage());
            }
        });
    }

    private void getDataSuccess(List<NewsEntity.StoriesBean> list) {
        myAdapter.setNewData(list);

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    private void getDataFailure(String msg) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        ToastKs.show(HttpCacheActivity.this, "加载失败，失败原因：" + msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        } else {
            finish();
        }
    }
}
