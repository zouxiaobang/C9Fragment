package com.cloud.tool.util.shake;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.Animation;

import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/23
 * @Desc: to-> 抖动动画设置类
 */
public class C9Shake {
    private static C9PropertyValuesHolder mC9PropertyValuesHolder;
    private int mDuration = 3;
    private int mRepeatCount = 1;
    private int mRepeatMode = Animation.RESTART;

    private C9Shake(C9PropertyValuesHolder holder) {
        mC9PropertyValuesHolder = holder;
    }

    /**
     * 自定义动画效果
     */
    public static C9Shake with(C9PropertyValuesHolder holder) {
        return new C9Shake(holder);
    }

    public C9Shake duration(int duration) {
        if (duration == 0) {
            mDuration = 3;
            return this;
        }

        mDuration = duration;
        return this;
    }

    public C9Shake repeatCount(int count) {
        if (count == 0) {
            mRepeatCount = 1;
            return this;
        }

        mRepeatCount = count;
        return this;
    }

    public C9Shake repeateMode(int mode) {
        mRepeatMode = mode;
        return this;
    }



    @SuppressLint("WrongConstant")
    public void startShake(View view) {
        List<PropertyValuesHolder> propertyValuesHolderList = mC9PropertyValuesHolder.build();
        PropertyValuesHolder[] propertyValuesHolders
                = propertyValuesHolderList.toArray(new PropertyValuesHolder[propertyValuesHolderList.size()]);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolders);
        animator.setDuration(mDuration);
        animator.setRepeatCount(mRepeatCount);
        animator.setRepeatMode(mRepeatMode);
        animator.start();
    }


}
