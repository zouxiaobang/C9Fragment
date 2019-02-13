package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.cloud.c9fragment.R;
import com.cloud.common.component.delegate.Cloud9Delegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to->
 */
public class SampleDelegate extends Cloud9Delegate {
    @BindView(R.id.btn_test_net)
    AppCompatButton btnTestNet;
    @BindView(R.id.btn_test_permission)
    AppCompatButton btnTestPermission;
    @BindView(R.id.btn_test_img)
    AppCompatButton btnTestImg;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
    }


    @OnClick({R.id.btn_test_net, R.id.btn_test_permission, R.id.btn_test_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test_net:
//                start(new SampleNetDelegate(), SupportFragment.SINGLETASK);
                start(new SampleNetDelegate());
                break;
            case R.id.btn_test_permission:
                start(new SamplePermissionDelegate());
                break;
            case R.id.btn_test_img:
                start(new SampleImgDelegate());
                break;
        }
    }
}
