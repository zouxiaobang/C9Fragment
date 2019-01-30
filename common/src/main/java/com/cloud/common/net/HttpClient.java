package com.cloud.common.net;

import android.content.Context;

import com.cloud.common.net.rest.RestBuilder;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 网络请求的客户端
 *           请求网络的返回方式有两种
 *           一种通过接口回调：可通过ISuccess、IFailure来处理返回数据
 *           一种通过注解回调：可通过@HttpCallback来处理，这种方法需要注意的是返回参数必须和所采用的网络请求策略返回的类型一致
 *           即在接收成功信息时使用如 @HttpCallback public void onSuccessResponse(RestResponse successResponse){}的方法
 *           而接收失败时信息则使用如 @HttpCallback public void onFailureResponse(String failMsg){}的方法
 *           方法名称、参数名称可以自取
 **/
public class HttpClient {
    private static IBuilder sHttpBuilder;

    /**
     * 可自定义网络请求框架
     */
    public static IBuilder builder(IBuilder httpBuilder) {
        sHttpBuilder = httpBuilder;
        return sHttpBuilder;
    }

    /**
     * 默认使用Rest框架请求网络
     */
    public static IBuilder builder(Context context) {
        sHttpBuilder = new RestBuilder(context);
        return sHttpBuilder;
    }

    public void get() {
        sHttpBuilder.execute(HttpMethod.GET);
    }

    public void post() {
        sHttpBuilder.execute(HttpMethod.POST);
    }

    public void put() {
        sHttpBuilder.execute(HttpMethod.PUT);
    }

    public void delete() {
        sHttpBuilder.execute(HttpMethod.DELETE);
    }

    public void upload() {
        sHttpBuilder.execute(HttpMethod.UPLOAD);
    }

    public void download() {
        sHttpBuilder.execute(HttpMethod.DOWNLOAD);
    }
}
