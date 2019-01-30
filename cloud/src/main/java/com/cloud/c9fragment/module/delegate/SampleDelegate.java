package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloud.c9fragment.R;
import com.cloud.c9logger.logger2.log.Logger;
import com.cloud.common.component.delegate.Cloud9Delegate;
import com.cloud.common.loading.CloudFullScreenDialog;
import com.cloud.common.net.HttpClient;
import com.cloud.common.net.callback.HttpCallback;
import com.cloud.common.net.callback.ISuccess;
import com.cloud.common.net.rest.RestBuilder;
import com.cloud.common.net.rest.RestResponse;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to->
 */
public class SampleDelegate extends Cloud9Delegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sample;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        /**
         * 请求网络的返回方式有两种
         * 一种通过接口回调：可通过ISuccess、IFailure来处理返回数据
         * 一种通过注解回调：可通过@HttpCallback来处理，这种方法需要注意的是返回参数必须和所采用的网络请求策略返回的类型一致
         * 即在接收成功信息时使用如 @HttpCallback public void onSuccessResponse(RestResponse successResponse){}的方法
         * 而接收失败时信息则使用如 @HttpCallback public void onFailureResponse(String failMsg){}的方法
         * 方法名称、参数名称可以自取
         */
        HttpClient.builder(new RestBuilder(this.getProxyActivity()))
//                .builder(this.getProxyActivity())
                .url("https://www.baidu.com/")
                .success((ISuccess<RestResponse>) response -> {
                    Logger.d("【请求成功】" + response.getResponseCode());
                    Logger.d("【请求成功】" + response.getResponseContent());
                })
                .failure(throwMsg -> Logger.d("【请求失败】" + throwMsg))
                .loader(new CloudFullScreenDialog(this.getProxyActivity()))
                .build()
                .get();
    }

    @HttpCallback
    public void onResponse(RestResponse restResponse) {
        Logger.d("【请求成功onResponse】" + restResponse.getResponseCode());
    }

    @HttpCallback
    public void onResponse(String failMsg) {
        Logger.d("【请求失败onResponse】" + failMsg);
    }
}
