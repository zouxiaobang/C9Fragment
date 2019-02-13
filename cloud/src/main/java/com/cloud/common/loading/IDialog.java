package com.cloud.common.loading;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to-> Dialog接口，用于实现多态
 */
public interface IDialog {
    /**
     * 显示Dialog
     */
    void showDialog();

    /**
     * 隐藏Dialog
     */
    void dismissDialog();

    /**
     * 该Dialog是否正在显示
     */
    boolean isShow();
}
