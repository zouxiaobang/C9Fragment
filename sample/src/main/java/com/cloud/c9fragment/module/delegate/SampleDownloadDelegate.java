package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
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

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: xb.zou
 * @Date: 2019/2/14
 * @Desc: to->
 */
public class SampleDownloadDelegate extends Cloud9Delegate {
    @BindView(R.id.btn_download)
    AppCompatButton btnDownload;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_download;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R.id.btn_download)
    public void onClick() {
        HttpClient.builder(new RestBuilder(this.getProxyActivity()))
                .url("http://47.106.66.5/static/apk/kiosk/app-release.apk")
                .extension("apk")
                .fileName("app-9")
                .success(response -> {
                    //下载完保存好文件后才返回
                    Logger.d("【请求成功onResponse】" + response);
                })
                .loader(new CloudFullScreenDialog(this.getProxyActivity()))
                .build()
                .download();
    }

    @HttpCallback
    public void onResponse(RestResponse restResponse) {
        //发送下载请求时即可返回
        Logger.d("【请求成功onResponse】" + restResponse.getResponseCode());
    }

    @HttpCallback
    public void onResponse(String failMsg) {
        Logger.d("【请求失败onResponse】" + failMsg);
    }
}
