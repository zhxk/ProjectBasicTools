package com.ks.projectbasictools.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ks.projectbasictools.R;
import com.ks.projectbasictools.bean.NewsEntity;

import java.util.List;

/**
 * @作者JTL.
 * @日期2018/3/15.
 * @说明：
 */

public class NewsApapter extends BaseQuickAdapter<NewsEntity.StoriesBean, BaseViewHolder> {
    private Context context;
    public NewsApapter(Context context, @Nullable List<NewsEntity.StoriesBean> data) {
        //注意这里直接把RecyclerView的item的Layout布局写在这里
        super(R.layout.news_item, data);
        this.context=context;
    }

    //设置新的数据源并刷新
    @Override
    public void setNewData(@Nullable List<NewsEntity.StoriesBean> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity.StoriesBean item) {
        helper.setText(R.id.tv_home_title,item.getTitle());

        Glide.with(context)
                .load(item.getImages().get(0))
                .centerInside()
                .into((ImageView) helper.getView(R.id.iv_home_img));
    }
}
