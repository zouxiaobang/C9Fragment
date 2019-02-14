package com.cloud.tool.util.dimen;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.cloud.common.base.Cloud9;
import com.cloud.common.base.ConfigurationKey;

/**
 * @Author: xb.zou
 * @Date: 2019/2/13
 * @Desc: to-> 尺寸工具类
 */
public class DimenTool {
    /**
     * 获取屏幕的宽度
     */
    public static int getScreenWidth() {
        final Resources resources = ((Context)Cloud9.getConfig(ConfigurationKey.APPLICATION_CONTEXT)).getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     */
    public static int getScreenHeight(){
        final Resources resources = ((Context)Cloud9.getConfig(ConfigurationKey.APPLICATION_CONTEXT)).getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
