package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.cloud.c9fragment.R;
import com.cloud.c9logger.logger2.log.Logger;
import com.cloud.common.component.delegate.Cloud9Delegate;
import com.cloud.common.loading.CloudFullScreenDialog;
import com.cloud.common.net.HttpClient;
import com.cloud.common.net.callback.HttpCallback;
import com.cloud.common.net.rest.RestBuilder;
import com.cloud.common.net.rest.RestResponse;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to->
 */
public class SampleDelegate extends Cloud9Delegate {
    @BindView(R.id.btn_test_net)
    AppCompatButton btnTestNet;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
    }


    @OnClick(R.id.btn_test_net)
    public void onClick() {
//        start(new SampleNetDelegate(), SupportFragment.SINGLETASK);
        start(new SampleNetDelegate());
    }
}
