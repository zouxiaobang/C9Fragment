package com.cloud.c9fragment.module.activity;

import com.cloud.c9fragment.module.delegate.SampleDelegate;
import com.cloud.common.component.activity.ProxyActivity;
import com.cloud.common.component.delegate.Cloud9Delegate;
/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to->
 */
public class SampleActivity extends ProxyActivity {

    @Override
    public Cloud9Delegate setRootDelegate() {
        return new SampleDelegate();
    }
}
