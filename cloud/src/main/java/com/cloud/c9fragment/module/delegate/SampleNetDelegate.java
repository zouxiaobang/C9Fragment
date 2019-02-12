package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
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
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Author: xb.zou
 * @Date: 2019/2/12
 * @Desc: to->
 */
public class SampleNetDelegate extends Cloud9Delegate {
    @BindView(R.id.tv_content)
    AppCompatTextView tvContent;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_net;
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", "18814182533");
        RequestBody body
                = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        HttpClient.builder(new RestBuilder(this.getProxyActivity()))
                .url("http://www.baidu.com")
//                .url("http://47.106.66.5:9090/kiosk/landlord/captcha")
//                .params("mobile", "18814182533")
                /*.body(body)
                .success((ISuccess<RestResponse>) response -> {
                    Logger.d("【请求成功】" + response.getResponseCode());
                    Logger.d("【请求成功】" + response.getResponseContent());
                })
                .failure(throwMsg -> Logger.d("【请求失败】" + throwMsg))*/
                .loader(new CloudFullScreenDialog(this.getProxyActivity()))
                .build()
//                .post();
        .get();
    }

    @HttpCallback
    public void onResponse(RestResponse restResponse) {
        Logger.d("【请求成功onResponse】" + restResponse.getResponseCode());
        tvContent.setText(restResponse.getResponseContent());
    }

    @HttpCallback
    public void onResponse(String failMsg) {
        Logger.d("【请求失败onResponse】" + failMsg);
    }
}
