package com.cloud.common.net;

import com.cloud.common.net.callback.IFailure;
import com.cloud.common.net.callback.IRequest;
import com.cloud.common.net.callback.ISuccess;
import com.cloud9.core.loader.IDialog;
import com.cloud9.core.net.callback.IFailure;
import com.cloud9.core.net.callback.IRequest;
import com.cloud9.core.net.callback.ISuccess;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 网络客户端Builder的接口
 **/
public interface IBuilder {
    IBuilder url(String url);
    IBuilder loader(IDialog dialog);
    IBuilder request(IRequest request);
    IBuilder success(ISuccess success);
    IBuilder failure(IFailure failure);
    void execute(HttpMethod method);
    HttpClient build();
}
