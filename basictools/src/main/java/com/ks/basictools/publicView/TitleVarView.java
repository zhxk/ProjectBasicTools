package com.ks.basictools.publicView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ks.basictools.R;
import com.ks.basictools.R2;
import com.ks.basictools.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleVarView extends BaseActivity {
    private TitleBarOnRightClickListener mRight;
    private TitleBarOnLeftBackClickListener mLeftBack;

    @BindView(R2.id.root_layout)
    RelativeLayout root_layout;
    @BindView(R2.id.ll_toolbar)
    View ll_toolbar;

    private RelativeLayout rl_full;
    private TextView tv_left_back;
    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_title_right;

    /**
     * 设置RightTitleBar的点击事件
     */
    public void setRightTitleBarOnClickListener(TitleBarOnRightClickListener callback) {
        this.mRight = callback;
    }
    public void setLeftTitleBarOnClickListener(TitleBarOnLeftBackClickListener callback) {
        this.mLeftBack = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.titlebar);
        ButterKnife.bind(this);
    }

    /**
     * Description：layout在titleBar下面显示
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        if (root_layout != null) {
            if (false) {//重合显示
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                root_layout.addView(view, layoutParams);
                ll_toolbar.bringToFront();
                initToolbar();
            } else {//分开显示
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.BELOW, R.id.ll_toolbar);
                root_layout.addView(view, layoutParams);
                initToolbar();
            }
        }
    }

    /**
     * 初始化 toolbar 内容布局
     */
    private void initToolbar() {
        tv_left_back = findViewById(R.id.tv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_right = findViewById(R.id.tv_title_right);
        iv_title_right = findViewById(R.id.iv_title_right);
        rl_full = findViewById(R.id.rl_full);
    }

    /**
     * 设置titleBar
     * @param isShowBack 是否显示回退
     * @param setTitleBarCenterText 设置bar中间title
     * @param setTitleBarRightText 设置bar右边的文字显示
     */
    public void setTitleBar(Boolean isShowBack, String setTitleBarCenterText, String setTitleBarRightText) {
        if (isShowBack != null) {
            isShowBack(isShowBack);
        }
        setTitleBarCenterText(setTitleBarCenterText);
        setTitleBarRightText(setTitleBarRightText);
    }
    /**
     * 设置titleBar
     * @param isShowBack            是否显示回退
     * @param setBackText 设置bar左边的文字显示
     * @param setTitleBarCenterText 设置bar中间title
     */
    public void setTitleBar(Boolean isShowBack,String setBackText, String setTitleBarCenterText, String setTitleBarRightText) {
        if (isShowBack != null) {
            isShowBack(isShowBack);
        }
        setTitleBarBackText(setBackText);
        setTitleBarRightText(setTitleBarRightText);
        setTitleBarCenterText(setTitleBarCenterText);
    }
    /**
     * 设置titleBar
     * @param isShowBack            是否显示回退
     * @param setTitleBarCenterText 设置bar中间title
     * @param setTitleBarRightImage 设置bar右边图片显示，使用getResources().getDrawable(R.drawable.xxx)的形式
     */
    public void setTitleBar(Boolean isShowBack, String setTitleBarCenterText, Drawable setTitleBarRightImage) {
        if (isShowBack != null) {
            isShowBack(isShowBack);
        }
        setTitleBarCenterText(setTitleBarCenterText);
        setTitleBarRightImage(setTitleBarRightImage);
    }
    /**
     * 设置titleBar
     * @param isShowBack            是否显示回退
     * @param setBackText 设置bar左边的文字显示
     * @param setTitleBarCenterText 设置bar中间title
     * @param setTitleBarRightImage 设置bar右边图片显示，使用getResources().getDrawable(R.drawable.xxx)的形式
     */
    public void setTitleBar(Boolean isShowBack,String setBackText, String setTitleBarCenterText, Drawable setTitleBarRightImage) {
        if (isShowBack != null) {
            isShowBack(isShowBack);
        }
        setTitleBarBackText(setBackText);
        setTitleBarCenterText(setTitleBarCenterText);
        setTitleBarRightImage(setTitleBarRightImage);
    }

    /**
     * 设置返回按钮
     */
    public void isShowBack(boolean flag) {
        if (tv_left_back != null) {
            if (flag) {
                tv_left_back.setVisibility(View.VISIBLE);
                tv_left_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLeftBack == null) {
                            finish();
                        } else {
                            mLeftBack.onLeftBackClick();
                        }

                    }
                });
            } else {
                tv_left_back.setVisibility(View.GONE);
            }
        }
    }
    /**
     * 设置当前 Activity 标题
     *
     * @param BackText
     */
    protected void setTitleBarBackText(String BackText) {
        if (tv_left_back != null) {
            tv_left_back.setVisibility(View.VISIBLE);
            tv_left_back.setText(BackText);
        }
    }
    /**
     * 设置当前 Activity 标题
     *
     * @param title
     */
    protected void setTitleBarCenterText(String title) {
        if (tv_title != null) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
    }
    /**
     * 设置当前 rightTitleBar
     */
    protected void setTitleBarRightText(String text) {
        if (text.equals("")) {
            tv_right.setVisibility(View.GONE);
        }else {
            iv_title_right.setVisibility(View.GONE);
            if (tv_right != null) {
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText(text);
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mRight !=null) {
                            mRight.onRightClick();
                        }
                    }
                });
            }
        }
    }
    /**
     * 设置右边文字颜色
     * @param color Color.parseColor("#FF0000")、getResources().getColor(R.color.colorPrimary)
     */
    public void setTitleBarRightTextColor(int color) {
        tv_right.setTextColor(color);
    }
    /**
     * 设置当前 rightTitleBar图片
     * @param src  getResources().getDrawable(R.drawable.benwu_icon_classify)
     */
    protected void setTitleBarRightImage(Drawable src){
        if (src == null) {
            iv_title_right.setVisibility(View.GONE);
        }else{
            tv_right.setVisibility(View.GONE);
            if (iv_title_right!=null) {
                iv_title_right.setVisibility(View.VISIBLE);
                iv_title_right.setImageDrawable(src);
                iv_title_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mRight !=null) {
                            mRight.onRightClick();
                        }
                    }
                });
            }
        }
    }

    public interface TitleBarOnRightClickListener {
        void onRightClick();  //右侧按钮点击事件
    }
    public interface TitleBarOnLeftBackClickListener {
        void onLeftBackClick();  //返回按钮点击事件
    }
}
