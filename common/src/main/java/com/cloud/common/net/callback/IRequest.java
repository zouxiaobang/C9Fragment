package com.cloud.common.net.callback;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 请求时回调
 **/
public interface IRequest {
    void onRequestStart();
    void onRequestEnd();
}
