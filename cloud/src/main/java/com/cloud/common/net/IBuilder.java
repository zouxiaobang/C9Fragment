package com.cloud.common.net;

import com.cloud.common.loading.IDialog;
import com.cloud.common.net.callback.IFailure;
import com.cloud.common.net.callback.IRequest;
import com.cloud.common.net.callback.ISuccess;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 网络客户端Builder的接口
 **/
public interface IBuilder {
    IBuilder url(String url);
    IBuilder body(RequestBody requestBody);
    IBuilder params(String key, String value);
    IBuilder params(Map<String, String> params);
    IBuilder loader(IDialog dialog);
    IBuilder request(IRequest request);
    IBuilder success(ISuccess success);
    IBuilder failure(IFailure failure);
    IBuilder file(File file);
    IBuilder downloadDir(String downloadDir);
    IBuilder extension(String extension);
    IBuilder fileName(String fileName);
    void execute(HttpMethod method);
    HttpClient build();
}
