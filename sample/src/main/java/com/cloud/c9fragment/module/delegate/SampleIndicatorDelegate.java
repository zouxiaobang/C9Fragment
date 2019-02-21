package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloud.c9fragment.R;
import com.cloud.c9fragment.module.delegate.indicator.SampleDynamicTabDelegate;
import com.cloud.c9fragment.module.delegate.indicator.SampleFixedTabDelegate;
import com.cloud.c9fragment.module.delegate.indicator.SampleNoTabOnlyIndicatorDelegate;
import com.cloud.c9fragment.module.delegate.indicator.SampleScrollableTabDelegate;
import com.cloud.common.component.delegate.Cloud9Delegate;

import butterknife.OnClick;

/**
 * @Author: xb.zou
 * @Date: 2019/2/21
 * @Desc: to->
 */
public class SampleIndicatorDelegate extends Cloud9Delegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_indicator;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick({R.id.scrollable_tab, R.id.fixed_tab, R.id.dynamic_tab, R.id.no_tab_only_indicator, R.id.work_with_fragment_container, R.id.tab_with_badge_view, R.id.load_custom_layout, R.id.custom_navigator})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scrollable_tab:
                start(new SampleScrollableTabDelegate());
                break;
            case R.id.fixed_tab:
                start(new SampleFixedTabDelegate());
                break;
            case R.id.dynamic_tab:
                start(new SampleDynamicTabDelegate());
                break;
            case R.id.no_tab_only_indicator:
                start(new SampleNoTabOnlyIndicatorDelegate());
                break;
            case R.id.work_with_fragment_container:
                break;
            case R.id.tab_with_badge_view:
                break;
            case R.id.load_custom_layout:
                break;
            case R.id.custom_navigator:
                break;
                default:
                    break;
        }
    }
}
