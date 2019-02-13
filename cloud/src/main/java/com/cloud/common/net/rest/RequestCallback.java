package com.cloud.common.net.rest;

import android.os.Handler;
import android.support.annotation.NonNull;


import com.cloud.c9logger.logger2.log.Logger;
import com.cloud.common.loading.CloudLoader;
import com.cloud.common.net.callback.AnnotationCallback;
import com.cloud.common.net.callback.IFailure;
import com.cloud.common.net.callback.IRequest;
import com.cloud.common.net.callback.ISuccess;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 请求返回的回调
 **/
@SuppressWarnings("unchecked")
public class RequestCallback implements Callback<String> {
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IFailure IFAILURE;
    private static final Handler HANDLER = new Handler();

    public RequestCallback(IRequest request, ISuccess success, IFailure failure) {
        this.IREQUEST = request;
        this.ISUCCESS = success;
        this.IFAILURE = failure;
    }

    @Override
    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
        //返回：不管服务器是否出错都返回
        RestResponse restResponse = buildWebResponse(response);
        Logger.d("【返回】：" + restResponse);
        if (ISUCCESS != null) {
            ISUCCESS.onSuccess(restResponse);
        }
        AnnotationCallback.getInstance().send(restResponse);

        if (IREQUEST != null) {
            IREQUEST.onRequestEnd();
        }
        dimissDialog();
    }

    @Override
    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
        //当前网络不可用或网络请求超时等情况
        Logger.d("【请求错误】：" + t.getMessage());
        if (IFAILURE != null) {
            IFAILURE.onFailure(t.getMessage());
        }
        AnnotationCallback.getInstance().send(t.getMessage());

        if (IREQUEST != null) {
            IREQUEST.onRequestEnd();
        }
        dimissDialog();
    }


    // ------------------------------------------------------------------------------------------------------------------

    /**
     * 隐藏Dialog
     */
    private void dimissDialog() {
        HANDLER.postDelayed(() -> CloudLoader.stopLoading(), 0);
    }

    /**
     * 构建WebResponse对象
     */
    @NonNull
    private RestResponse buildWebResponse(Response<String> response) {
        RestResponse webResponse = new RestResponse();
        webResponse.setHeads(response.headers());
        webResponse.setResponseCode(response.code());
        webResponse.setResponseContent(response.body());
        return webResponse;
    }
}
