package com.cloud.common.util.permission;

import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/13
 * @Desc: to-> 权限的请求回调
 */
public interface OnPermissionListener {
    /**
     * 有权限被授予时回调
     * @param granted   请求成功的权限组
     * @param isAll     是否全部都被授权成功
     */
    void hasPermission(List<String> granted, boolean isAll);

    /**
     * 有权限被拒绝时回调
     * @param denied    请求失败的权限组
     * @param quick     是否有某个权限被永久拒绝
     */
    void noPermission(List<String> denied, boolean quick);
}
