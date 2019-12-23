package com.ks.projectbasictools.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SimpleView extends RelativeLayout {

    public SimpleView(Context context, int layoutId) {
        super(context);
        init(context, layoutId);
    }

    public SimpleView(Context context, AttributeSet attrs, int layoutId) {
        super(context, attrs);
        init(context, layoutId);
    }

    public SimpleView(Context context, AttributeSet attrs, int defStyleAttr, int layoutId) {
        super(context, attrs, defStyleAttr);
        init(context, layoutId);
    }

    public void init(Context context,int layoutId) {

        inflate(context, layoutId, this);
    }
}
