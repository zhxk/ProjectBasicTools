package com.ks.projectbasictools.base_interface;

/**
 * @className: IBaseActivity
 * @classDescription: activity接口基类
 * @author: leibing
 * @createTime: 2016/8/11
 */
public interface IBaseActivity<T> {
    // 设置逻辑
    void setPresenter(T mIActivityPresenter);
}
