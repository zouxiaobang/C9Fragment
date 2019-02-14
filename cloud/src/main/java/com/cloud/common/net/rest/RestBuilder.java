package com.cloud.common.net.rest;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSONObject;
import com.cloud.c9logger.logger2.log.Logger;
import com.cloud.common.loading.CloudLoader;
import com.cloud.common.loading.IDialog;
import com.cloud.common.net.HttpClient;
import com.cloud.common.net.HttpMethod;
import com.cloud.common.net.IBuilder;
import com.cloud.common.net.callback.AnnotationCallback;
import com.cloud.common.net.callback.IFailure;
import com.cloud.common.net.callback.IRequest;
import com.cloud.common.net.callback.ISuccess;
import com.cloud.common.net.download.DownloadHandler;
import com.cloud.common.util.net.NetTool;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;


/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: Rest请求网络的Builder类
 **/
public class RestBuilder implements IBuilder {
    private static final Handler HANDLER = new Handler();
    private Context mContext;
    private String mUrl;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParamsMap();
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private RequestBody mBody;
    private IDialog mDialog;
    private File mFile;
    private String mDownloadDir;
    private String mExtension;
    private String mFileName;
    private RestService mService;

    public RestBuilder(Context context) {
        this.mContext = context;
        this.mService = RestCreator.getRestService();
    }

    @Override
    public IBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    @Override
    public IBuilder body(RequestBody requestBody) {
        this.mBody = requestBody;
        return this;
    }

    @Override
    public IBuilder params(String key, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        RequestBody body
                = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        this.mBody = body;

        PARAMS.clear();
        PARAMS.put(key, value);
        return this;
    }

    @Override
    public IBuilder params(Map<String, String> params) {
        JSONObject jsonObject = new JSONObject();
        for (String key: params.keySet()) {
            jsonObject.put(key, params.get(key));
        }
        RequestBody body
                = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        this.mBody = body;

        PARAMS.clear();
        PARAMS.putAll(params);
        return this;
    }


    @Override
    public IBuilder loader(IDialog dialog) {
        this.mDialog = dialog;
        return this;
    }

    @Override
    public IBuilder request(IRequest request) {
        this.mRequest = request;
        return this;
    }

    @Override
    public IBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    @Override
    public IBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    @Override
    public IBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    @Override
    public IBuilder downloadDir(String downloadDir) {
        this.mDownloadDir = downloadDir;
        return this;
    }

    @Override
    public IBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    @Override
    public IBuilder fileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }

    @Override
    public void execute(HttpMethod method) {
        if (!NetTool.isNetworkAvailable(mContext)) {
            String errMsg = "网络连接异常";
            if (mFailure != null) {
                mFailure.onFailure(errMsg);
            }
            AnnotationCallback.getInstance().send(errMsg);

            return;
        }

        if (mRequest != null) {
            mRequest.onRequestStart();
        }

        Logger.d("【请求】：" + mUrl);
        Logger.d("【请求参数】：" + PARAMS);
        showDialog();
        Call<String> call = null;
        JSONObject jsonObject = new JSONObject();
        for (String key: PARAMS.keySet()) {
            jsonObject.put(key, PARAMS.get(key));
        }
        RequestBody body
                = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString());

        switch (method) {
            case GET:
                call = mService.get(mUrl, PARAMS);
                break;
            case POST:
                if (PARAMS.size() == 0) {
                    call = mService.post(mUrl, mBody);
                } else {
                    call = mService.post(mUrl, body);
                }
                break;
            case PUT:
                if (PARAMS.size() == 0) {
                    call = mService.put(mUrl, mBody);
                } else {
                    call = mService.put(mUrl, body);
                }
                break;
            case UPLOAD:
                final RequestBody requestBody
                        = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mFile);
                final MultipartBody.Part multipartBody
                        = MultipartBody.Part.createFormData("file", mFile.getName(), requestBody);
                call = mService.upload(mUrl, multipartBody);
                break;
            case DELETE:
                call = mService.delete(mUrl, PARAMS);
                break;
            case DOWNLOAD:
                download();
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(new RequestCallback(mRequest, mSuccess, mFailure));
        }
    }

    @Override
    public HttpClient build() {
        return new HttpClient();
    }

    /**
     * 显示Dialog
     */
    private void showDialog() {
        HANDLER.postDelayed(() -> CloudLoader.showLoaging(mDialog), 0);
    }

    private void download(){
        new DownloadHandler(mUrl, PARAMS, mRequest, mDownloadDir, mExtension, mFileName, mSuccess, mFailure)
                .handleDownload();
    }
}
