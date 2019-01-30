package com.cloud.common.net.callback;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 成功回调
 **/
public interface ISuccess<T> {
    void onSuccess(T response);
}
