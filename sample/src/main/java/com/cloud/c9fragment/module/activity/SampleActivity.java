package com.cloud.c9fragment.module.activity;

import com.cloud.c9fragment.module.delegate.SampleDelegate;
import com.cloud.common.component.activity.ProxyActivity;
import com.cloud.common.component.delegate.Cloud9Delegate;

public class SampleActivity extends ProxyActivity {

    @Override
    public Cloud9Delegate setRootDelegate() {
        return new SampleDelegate();
    }
}
