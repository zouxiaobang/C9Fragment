package com.cloud.common.net.rest;

import android.content.Context;
import android.os.Handler;

import com.cloud.common.loading.CloudLoader;
import com.cloud.common.loading.IDialog;
import com.cloud.common.net.HttpClient;
import com.cloud.common.net.HttpMethod;
import com.cloud.common.net.IBuilder;
import com.cloud.common.net.callback.IFailure;
import com.cloud.common.net.callback.IRequest;
import com.cloud.common.net.callback.ISuccess;

import java.io.File;
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
    public void execute(HttpMethod method) {
        if (mRequest != null) {
            mRequest.onRequestStart();
        }

        showDialog();
        Call<String> call = null;
        switch (method) {
            case GET:
                call = mService.get(mUrl, PARAMS);
                break;
            case POST:
                call = mService.post(mUrl, PARAMS);
                break;
            case POST_RAW:
                call = mService.postRaw(mUrl, mBody);
                break;
            case PUT:
                call = mService.put(mUrl, PARAMS);
                break;
            case PUT_RAW:
                call = mService.putRaw(mUrl, mBody);
                break;
            case UPLOAD:
                final RequestBody requestBody
                        = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mFile);
                final MultipartBody.Part body
                        = MultipartBody.Part.createFormData("file", mFile.getName(), requestBody);
                call = mService.upload(mUrl, body);
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
       /* new DownloadHandler(URL, REQUEST, SUCCESS, FAILURE, ERROR, DOWNLOAD_DIR, EXTENSION, NAME)
                .handleDownload();*/
    }
}
