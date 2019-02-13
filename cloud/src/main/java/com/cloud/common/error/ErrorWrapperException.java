package com.cloud.common.error;

import android.support.annotation.NonNull;

import java.lang.reflect.Field;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 核心模块的异常类
 **/
public class ErrorWrapperException extends RuntimeException implements IError {
    private Enum<?> error;
    private String desc;

    public ErrorWrapperException(String message) {
        super(message);
    }

    public ErrorWrapperException(Enum<?> error) {
        this.error = error;
    }

    public ErrorWrapperException(Enum<?> error, String desc) {
        this.error = error;
        this.desc = desc;
    }

    @Override
    public String getMessage() {
        return "【错误码】" + getErrorCode() + " -- 【错误描述】" + getErrorDesc();
    }

    @Override
    public int getErrorCode() {
        ErrorCode errorCode = getField();

        return errorCode.code();
    }

    @Override
    public String getErrorDesc() {
        if (desc != null && desc.length() != 0) {
            return desc;
        }

        ErrorCode errorCode = getField();
        return errorCode.desc();
    }

    @NonNull
    private ErrorCode getField() {
        Field errorField = null;
        try {
            errorField = error.getClass().getField(error.name());
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Can't read annotated field.", e);
        }

        ErrorCode errorCode = errorField.getAnnotation(ErrorCode.class);
        if (errorCode == null) {
            throw new IllegalArgumentException(String.format("%s.%s should to be annotated by @ErrorCode.", error.getClass().getName(), error));
        }
        return errorCode;
    }
}
