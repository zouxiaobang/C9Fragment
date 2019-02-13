package com.cloud.common.net.callback;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 失败回调
 **/
public interface IFailure {
    void onFailure(String throwMsg);
}
