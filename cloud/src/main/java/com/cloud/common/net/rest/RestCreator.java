package com.cloud.common.net.rest;


import com.cloud.common.base.Cloud9;
import com.cloud.common.base.ConfigurationKey;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: Rest 构建类
 **/
public class RestCreator {
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    public static WeakHashMap<String, Object> getParamsMap() {
        return ParamsHolder.PARAMS;
    }

    // --------------------------------------------------- 内部静态类 保证唯一性 -----------------------------------------------------

    private static final class ParamsHolder {
        private static WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    private static final class RetrofitHolder {
        /**
         * 共有域
         */
        private static final String BASE_HOST = Cloud9.getConfig(ConfigurationKey.API_HOST);
        /**
         * retrofit client
         */
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_HOST)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OkHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS
                = Cloud9.getConfig(ConfigurationKey.INTERCEPTOR);

        private static OkHttpClient.Builder addIntercceptor(){
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor: INTERCEPTORS){
                    BUILDER.addInterceptor(interceptor);
                }
            }

            return BUILDER;
        }
        private static final OkHttpClient OK_HTTP_CLIENT =
                addIntercceptor()
                        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .build();
    }
}
