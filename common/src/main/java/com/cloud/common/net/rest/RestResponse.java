package com.cloud.common.net.rest;

import okhttp3.Headers;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 自定义请求返回类
 **/
public class RestResponse {
    private Integer responseCode;
    private String responseContent;
    private Headers heads;

    public RestResponse() {
    }

    public RestResponse(Integer responseCode, String responseContent, Headers heads) {
        this.responseCode = responseCode;
        this.responseContent = responseContent;
        this.heads = heads;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public Headers getHeads() {
        return heads;
    }

    public void setHeads(Headers heads) {
        this.heads = heads;
    }

    public String getHead(String key) {
        return heads.get(key);
    }
}
