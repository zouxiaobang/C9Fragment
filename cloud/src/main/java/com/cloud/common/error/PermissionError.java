package com.cloud.common.error;

/**
 * @Author: xb.zou
 * @Date: 2019/2/13
 * @Desc: to-> 权限异常接口
 */
public interface PermissionError {
    int MANIFEST_PERMISSION_NULL = 5001;
    int ACTIVITY_IS_NULL = 5002;
    int LISTENER_IS_NULL = 5003;



    enum Values {
        @ErrorCode(code = PermissionError.MANIFEST_PERMISSION_NULL, desc = "Manifest清单文件中没有注册权限")
        MANIFEST_PERMISSION_NULL,
        @ErrorCode(code = PermissionError.ACTIVITY_IS_NULL, desc = "Activity参数不能为空")
        ACTIVITY_IS_NULL,
        @ErrorCode(code = PermissionError.LISTENER_IS_NULL, desc = "OnPermissionListener回调不能为空")
        LISTENER_IS_NULL
    }
}
