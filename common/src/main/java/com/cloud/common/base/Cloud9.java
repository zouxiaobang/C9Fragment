package com.cloud.common.base;

import android.content.Context;

import java.util.Map;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to-> 核心APP类，用于被业务模块app调用
 */
public class Cloud9 {
    /**
     * 初始化
     * 一般在Application中
     */
    public static Configuration init(Context context) {
        return Configuration.getInstance().withContext(context);
    }

    /**
     * 获取所有的参数配置
     */
    public static Map<String, Object> getConfigs() {
        return Configuration.getInstance().getAllConfigs();
    }

    /**
     * 获取某个参数配置
     */
    public static <T> T getConfig(Enum<ConfigurationKey> key) {
        return (T)Configuration.getInstance().getConfig(key);
    }
}
