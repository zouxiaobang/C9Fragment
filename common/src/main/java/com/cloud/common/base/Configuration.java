package com.cloud.common.base;

import android.content.Context;

import com.cloud.common.error.CoreError;
import com.cloud.common.error.ErrorWrapperException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import okhttp3.Interceptor;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to-> 配置文件管理类
 */
public class Configuration {
    /**
     * 配置参数
     */
    private static final Map<String, Object> CONFIG_PARAMS = new HashMap<>();
    /**
     * 拦截器列表
     */
    private static final List<Interceptor> INTERCEPTOR_LIST = new ArrayList<>();

    private Configuration() {
        // 开始进行配置
        CONFIG_PARAMS.put(ConfigurationKey.CONFIG_READY.name(), false);
    }

    public static Configuration getInstance() {
        return ConfigHolder.INSTANCE;
    }

    // ----------------------------------- 静态内部类 ------------------------------------

    private static class ConfigHolder {
        private static final Configuration INSTANCE = new Configuration();
    }

    // --------------------------------- Builder模式配置参数 ----------------------------

    /**
     * 配置全局上下文，这里只提供给同包下使用
     * 目的是为了防止其他Activity可以调用该方法对context进行修改
     */
    final Configuration withContext(Context context) {
        CONFIG_PARAMS.put(ConfigurationKey.APPLICATION_CONTEXT.name(), context);
        return this;
    }

    /**
     * 配置网络域名
     */
    public final Configuration withHost(String apiHost) {
        CONFIG_PARAMS.put(ConfigurationKey.API_HOST.name(), apiHost);
        return this;
    }

    /**
     * 配置自己的项目
     */
    public final Configuration withIcon(Object icon) {
        CONFIG_PARAMS.put(ConfigurationKey.ICON.name(), icon);
        return this;
    }

    /**
     * 添加请求拦截器
     */
    public final Configuration withInterceptor(Interceptor interceptor) {
        INTERCEPTOR_LIST.add(interceptor);
        CONFIG_PARAMS.put(ConfigurationKey.INTERCEPTOR.name(), INTERCEPTOR_LIST);
        return this;
    }

    /**
     * 添加请求拦截器
     */
    public final Configuration withInterceptors(List<Interceptor> interceptorList) {
        INTERCEPTOR_LIST.addAll(interceptorList);
        CONFIG_PARAMS.put(ConfigurationKey.INTERCEPTOR.name(), INTERCEPTOR_LIST);
        return this;
    }

    /**
     * 使用Logger
     */
    public final Configuration withLogger(String globalTag) {
        CONFIG_PARAMS.put(ConfigurationKey.LOGGER.name(), globalTag);
        return this;
    }

    /**
     * 是否全屏显示
     */
    public final Configuration withHideBar(boolean hide) {
        CONFIG_PARAMS.put(ConfigurationKey.HIDE_BAR.name(), hide);
        return this;
    }

    /**
     * 完成参数的配置过程
     */
    public final void configure() {
        CONFIG_PARAMS.put(ConfigurationKey.CONFIG_READY.name(), true);
    }

    /**
     * 根据属性获取某一个参数
     */
    public final <T> T getConfig(Enum<ConfigurationKey> key) {
        checkConfiguration();
        return (T) CONFIG_PARAMS.get(key.name());
    }

    /**
     * 获取所有的配置参数
     */
    public final Map<String, Object> getAllConfigs() {
        return CONFIG_PARAMS;
    }

    /**
     * 检查参数是否配置成功
     */
    private final void checkConfiguration() {
        final boolean isReady = (boolean) CONFIG_PARAMS.get(ConfigurationKey.CONFIG_READY.name());
        if (!isReady) {
            throw new ErrorWrapperException(CoreError.Values.CONFIG_MUST_EXIST);
        }
    }
}
