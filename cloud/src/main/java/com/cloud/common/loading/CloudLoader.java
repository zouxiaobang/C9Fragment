package com.cloud.common.loading;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to-> 对话框管理
 */
public class CloudLoader {
    private static final List<IDialog> DIALOGS = new ArrayList<>();

    public static void showLoaging(IDialog dialog) {
        if (dialog == null) {
            return;
        }

        //如果正在显示，不理他
        if (dialog.isShow()) {
            return;
        }

        //先清除之前的Dialog
        for (IDialog oldDialog : DIALOGS) {
            oldDialog.dismissDialog();
        }

        //将Dialog添加到集合中
        DIALOGS.add(dialog);
        dialog.showDialog();
    }

    public static void stopLoading() {
        for (IDialog dialog : DIALOGS) {
            if (dialog != null && dialog.isShow()) {
                dialog.dismissDialog();
            }
        }
        DIALOGS.clear();
    }
}
