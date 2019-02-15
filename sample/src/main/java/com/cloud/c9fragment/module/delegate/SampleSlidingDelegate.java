package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloud.c9fragment.R;
import com.cloud.common.component.delegate.Cloud9Delegate;
import com.cloud.common.component.design.C9SlidingMenu;

import butterknife.BindView;

/**
 * @Author: xb.zou
 * @Date: 2019/2/15
 * @Desc: to->
 */
public class SampleSlidingDelegate extends Cloud9Delegate {
    @BindView(R.id.my_slidingMenu)
    C9SlidingMenu mC9SlidingMenu;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_sliding;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        //设置侧边栏出现时，主界面是否缩小
        mC9SlidingMenu.setScale(true);
    }
}
