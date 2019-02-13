package com.cloud.common.component.delegate;

import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cloud.common.R;
import com.cloud.common.base.Cloud9;
import com.cloud.common.base.ConfigurationKey;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to->
 */
public abstract class Cloud9Delegate extends AbstractPermissionDelegate {
    @Override
    public void onResume() {
        super.onResume();

        if (Cloud9.getConfig(ConfigurationKey.HIDE_BAR)) {
            hideStatusNavigationBar();
        }
    }

    /**
     * 默认吐司显示:
     * 居中
     * 默认灰色背景
     */
    protected void showToast(String text) {
        View background = LayoutInflater.from(this.getProxyActivity()).inflate(R.layout.common_toast, null);
        ((TextView) background.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(this.getProxyActivity().getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(background);
        toast.show();
    }

    /**
     * 吐司显示
     */
    protected void showToast(String text, View background, int gravity, int duration) {
        ((TextView) background.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(this.getProxyActivity().getApplicationContext());
        toast.setGravity(gravity, 0, 0);
        toast.setDuration(duration);
        toast.setView(background);
        toast.show();
    }


    /**
     * 隐藏状态栏
     */
    private void hideStatusNavigationBar() {
        if (Build.VERSION.SDK_INT < 16) {
            this.getProxyActivity().getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            this.getProxyActivity().getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }
}
