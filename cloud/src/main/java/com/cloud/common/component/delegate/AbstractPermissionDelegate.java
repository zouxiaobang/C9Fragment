package com.cloud.common.component.delegate;

import com.cloud.common.util.permission.EasyPermission;
import com.cloud.common.util.permission.OnPermissionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to-> 权限处理
 */
public abstract class AbstractPermissionDelegate extends BaseDelegate {
    /**
     * 申请权限接口
     */
    protected void requestPermission(boolean isConnect, String... permissions) {
        requestPermission(isConnect, Arrays.asList(permissions));
    }

    protected void requestPermission(boolean isConnect, String[]... permissions) {
        List<String> permissionList = new ArrayList<>();
        for (String[] group: permissions) {
            permissionList.addAll(Arrays.asList(group));
        }

        requestPermission(isConnect, permissionList);
    }

    protected void requestPermission(boolean isConnect, List<String> permissions) {
        EasyPermission.with(this.getProxyActivity())
                .requestConnect(isConnect)
                .permissions(permissions)
                .request(new OnPermissionListener() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        onHasPermission(granted, isAll);
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        onNoPermission(denied, quick);
                    }
                });
    }

    public void onHasPermission(List<String> granted, boolean isAll) {

    }

    public void onNoPermission(List<String> denied, boolean quick) {

    }

    /**
     * 前往设置
     */
    public void gotoSettings(){
        EasyPermission.gotoPermissionSettings(this.getProxyActivity(), true);
    }
}
