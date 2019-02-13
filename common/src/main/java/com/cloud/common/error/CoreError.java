package com.cloud.common.error;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * 核心异常类
 **/
public interface CoreError {
    int SYSTEM_ERROR = 4000;
    int CONFIG_MUST_EXIST = 4001;
    int LAYOUT_TYPE_ERROR = 4002;
    int CALLBACK_PARAM_NULL = 4003;


    enum Values {
        @ErrorCode(code = CoreError.SYSTEM_ERROR, desc = "系统异常")
        SYSTEM_ERROR,
        @ErrorCode(code = CoreError.CONFIG_MUST_EXIST, desc = "参数配置完需要调用configure()来完成配置")
        CONFIG_MUST_EXIST,
        @ErrorCode(code = CoreError.LAYOUT_TYPE_ERROR, desc = "setLayout()的返回值应该为Integer或View类型")
        LAYOUT_TYPE_ERROR,
        @ErrorCode(code = CoreError.CALLBACK_PARAM_NULL, desc = "请求返回的参数不能为空")
        CALLBACK_PARAM_NULL
    }
}
