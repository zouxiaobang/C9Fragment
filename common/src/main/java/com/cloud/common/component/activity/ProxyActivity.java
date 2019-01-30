package com.cloud.common.component.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.cloud.common.R;
import com.cloud.common.component.delegate.Cloud9Delegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to->
 */
public abstract class ProxyActivity extends SupportActivity {
    /**
     * 设置主Delegate
     */
    public abstract Cloud9Delegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContainer(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    private void initContainer(Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);

        setContentView(container);

        if (savedInstanceState == null){
            //首次进入，对Fragment进行加载
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }
}
