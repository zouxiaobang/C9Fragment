package com.cloud.common.net.callback;


import com.cloud.common.error.CoreError;
import com.cloud.common.error.ErrorWrapperException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xb.zou
 * @date 2018/11/26
 * @desc 通过注解反射的方式返回
 **/
public class AnnotationCallback {
    private Set<Object> mSubscriberSet;
    private static AnnotationCallback mInstance;

    private AnnotationCallback() {
        mSubscriberSet = new HashSet<>();
    }

    public static AnnotationCallback getInstance() {
        if (mInstance == null) {
            synchronized (AnnotationCallback.class) {
                if (mInstance == null) {
                    mInstance = new AnnotationCallback();
                }
            }
        }
        return mInstance;
    }

    // ---------------------------------------------------------------------------------------

    public synchronized void registerSubscriber(Object subscriber) {
        mSubscriberSet.add(subscriber);
    }

    public synchronized void unregisterSubscriber(Object subscriber) {
        mSubscriberSet.remove(subscriber);
    }

    public void send(Object data) {
        for (Object subscriber : mSubscriberSet) {
            callByAnntiation(subscriber, data);
        }
    }

    private void callByAnntiation(Object target, Object data) {
        //获取target中所有的方法
        Method[] methods = target.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                //判断该方法是否是被RegisterBus注册监听的
                boolean isAnntationMethod = method.isAnnotationPresent(HttpCallback.class);

                if (isAnntationMethod) {
                    if (method.getParameterTypes().length <= 0) {
                        throw new ErrorWrapperException(CoreError.Values.CALLBACK_PARAM_NULL);
                    }
                    //获取该方法第一个参数的类型
                    Class paramType = method.getParameterTypes()[0];
                    //判断该参数的类型是否和data的参数类型是一致的
                    if (paramType.equals(data.getClass())) {
                        //执行该方法
                        method.invoke(target, new Object[]{data});
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }
}
