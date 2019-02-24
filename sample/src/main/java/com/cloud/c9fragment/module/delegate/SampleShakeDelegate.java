package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.Animation;

import com.cloud.c9fragment.R;
import com.cloud.common.component.delegate.Cloud9Delegate;
import com.cloud.tool.util.shake.C9PropertyValuesHolder;
import com.cloud.tool.util.shake.C9Shake;
import com.cloud.tool.util.shake.interpolator.ShockInterpolator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: xb.zou
 * @Date: 2019/2/23
 * @Desc: to-> 抖动Demo
 */
public class SampleShakeDelegate extends Cloud9Delegate {
    @BindView(R.id.iv_shake)
    AppCompatImageView ivShake;


    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_shake;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @OnClick({R.id.iv_shake})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_shake:
                shakeImg();
                break;
        }
    }

    private void shakeImg() {
        C9PropertyValuesHolder holder = new C9PropertyValuesHolder();
//        holder.registerInterpolator(new LinearInterpolator(View.SCALE_X));
//        holder.registerInterpolator(new LinearInterpolator(View.SCALE_Y));
        holder.registerInterpolator(new ShockInterpolator(View.ROTATION));

        C9Shake.with(holder)
                .duration(1000)
                .repeatCount(2)
                .repeateMode(Animation.RESTART)
                .startShake(ivShake);
    }
}
