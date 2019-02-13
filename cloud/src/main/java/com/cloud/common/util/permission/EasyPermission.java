package com.cloud.common.util.permission;

import android.app.Activity;
import android.content.Context;

import com.cloud.common.error.ErrorWrapperException;
import com.cloud.common.error.PermissionError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/13
 * @Desc: to-> 权限请求类
 */
public class EasyPermission {
    /**
     * 当前Activity
     */
    private Activity mActivity;
    /**
     * 权限列表
     */
    private List<String> mPermissionList = new ArrayList<>();
    /**
     * 授权失败时是否继续请求
     */
    private boolean isConnect;

    private EasyPermission(Activity activity) {
        this.mActivity = activity;
    }

    public static EasyPermission with(Activity activity) {
        return new EasyPermission(activity);
    }

    public EasyPermission permissions(String... permissions) {
        mPermissionList.addAll(Arrays.asList(permissions));
        return this;
    }

    public EasyPermission permissions(String[]... permissions) {
        for (String[] group: permissions) {
            mPermissionList.addAll(Arrays.asList(group));
        }
        return this;
    }

    public EasyPermission permissions(List<String> permissions) {
        mPermissionList.addAll(permissions);
        return this;
    }

    public EasyPermission requestConnect(boolean isConnect) {
        this.isConnect = isConnect;
        return this;
    }

    public void request(OnPermissionListener listener) {
        if (mActivity == null) {
            throw new ErrorWrapperException(PermissionError.Values.ACTIVITY_IS_NULL);
        }
        if (listener == null) {
            throw new ErrorWrapperException(PermissionError.Values.LISTENER_IS_NULL);
        }

        //如果没有指定请求的权限，就从Manifest文件中获取
        if (mPermissionList == null || mPermissionList.size() == 0) {
            mPermissionList = PermissionTool.getPermissionsByManifest(mActivity);
        }

        //Manifest文件中也没有权限，则抛异常
        if (mPermissionList == null || mPermissionList.size() == 0) {
            throw new ErrorWrapperException(PermissionError.Values.MANIFEST_PERMISSION_NULL);
        }

        PermissionTool.checkTargetSdkVersion(mActivity, mPermissionList);
        List<String> failPermissionList = PermissionTool.getFailPermissions(mActivity, mPermissionList);
        if (failPermissionList == null || failPermissionList.size() == 0) {
            listener.hasPermission(mPermissionList, true);
        } else {
            PermissionTool.checkPermissionsInManifest(mActivity, mPermissionList);
            //申请没有授权过的权限
            PermissionManager
                    .newInstant((new ArrayList<>(mPermissionList)), isConnect)
                    .prepareRequest(mActivity, listener);
        }
    }

    /**
     * 检测某些权限是否已经被授予
     */
    public static boolean isHasPermission(Context context, String... permissions) {
        List<String> failPermissions = PermissionTool.getFailPermissions(context, Arrays.asList(permissions));
        return failPermissions == null || failPermissions.size() == 0;
    }

    /**
     * 检测某些权限是否已经被授予
     */
    public static boolean isHasPermission(Context context, String[]... permissions) {
        List<String> permissionList = new ArrayList<>();
        for (String[] group : permissions) {
            permissionList.addAll(Arrays.asList(group));
        }

        List<String> failPermissions = PermissionTool.getFailPermissions(context, permissionList);
        return failPermissions == null || failPermissions.size() == 0;
    }

    /**
     * 跳转到权限设置页面
     */
    public static void gotoPermissionSettings(Context context) {
        PermissionSettingPage.start(context, false);
    }

    /**
     * 跳转到权限设置页面
     */
    public static void gotoPermissionSettings(Context context, boolean newTask) {
        PermissionSettingPage.start(context, newTask);
    }
}
