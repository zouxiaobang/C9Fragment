package com.cloud.common.net.download;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.cloud.c9logger.logger2.log.Logger;
import com.cloud.common.loading.CloudLoader;
import com.cloud.common.net.callback.AnnotationCallback;
import com.cloud.common.net.callback.IFailure;
import com.cloud.common.net.callback.IRequest;
import com.cloud.common.net.callback.ISuccess;
import com.cloud.common.net.rest.RestCreator;
import com.cloud.common.net.rest.RestResponse;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author: xb.zou
 * @Date: 2019/2/14
 * @Desc: to-> 下载处理类
 */
public class DownloadHandler {
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private static final Handler HANDLER = new Handler();

    public DownloadHandler(String url,
                           WeakHashMap<String, Object> params,
                           IRequest request,
                           String downDir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure) {
        this.URL = url;
        this.PARAMS = params;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
    }

    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator
                .getRestService()
                .download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                            RestResponse restResponse = buildWebResponse(response);
                            AnnotationCallback.getInstance().send(restResponse);
                        } else {
                            if (FAILURE != null) {
                                FAILURE.onFailure(response.message());
                            }
                            AnnotationCallback.getInstance().send(response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure(t.getMessage());
                        }
                        AnnotationCallback.getInstance().send(t.getMessage());
                        dimissDialog();
                    }
                });
    }


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
    private RestResponse buildWebResponse(Response<ResponseBody> response) {
        RestResponse webResponse = new RestResponse();
        webResponse.setHeads(response.headers());
        webResponse.setResponseCode(response.code());
        webResponse.setResponseContent(response.body().toString());
        return webResponse;
    }
}
