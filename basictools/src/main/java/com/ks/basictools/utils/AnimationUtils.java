package com.ks.basictools.utils;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

/**
 * author：康少
 * date：2019/2/28
 * description：动画工具
 */
public class AnimationUtils {
    /**
     * 震动动画
     * @param view 视图控件
     * @param scaleSmall 缩小程度,1.0f 为原图大小。例如0.8f，表示缩小程度为原始大小的0.8倍；
     * @param scaleLarge 放大程度，1.0f为原图大小；
     * @param shakeDegrees 摇摆角度，10f为∠10°；
     * @param duration 持续时长，1000 = 1秒；
     */
    public static void startShakeByProperty(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) {
        //先变小后变大
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );

        //先往左再往右
        PropertyValuesHolder rotateValuesHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
//                Keyframe.ofFloat(0.1f, -shakeDegrees),
                Keyframe.ofFloat(0.2f, shakeDegrees),
//                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.4f, -shakeDegrees),
//                Keyframe.ofFloat(0.5f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
//                Keyframe.ofFloat(0.7f, -shakeDegrees),
                Keyframe.ofFloat(0.8f, -shakeDegrees),
//                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(1.0f, 0f)
        );

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    /**
     * 执行view加载动画
     * @param view 控件
     * @param layoutId .xml配置文件地址，如：R.anim.xxx；
     * @param listener 监听器
     */
    public static void runLayoutAnimation(final View view, int layoutId, Animation.AnimationListener listener) {
        final Context context = view.getContext();
        Animation loadAnimation = android.view.animation.AnimationUtils.loadAnimation(context,
                layoutId);
        loadAnimation.setFillEnabled(true);//启动Fill保持
        loadAnimation.setFillAfter(true);//设置动画的最后一帧是保留在view上的
        view.setAnimation(loadAnimation);
        loadAnimation.setAnimationListener(listener);
        view.startAnimation(loadAnimation);
    }
}
