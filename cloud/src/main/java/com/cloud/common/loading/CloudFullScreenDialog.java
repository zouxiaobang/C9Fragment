package com.cloud.common.loading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cloud.common.R;

/**
 * author: xb.Zou
 * date: 2018/11/25 0025
 * desc: 一个隐藏状态栏、导航栏的Dialog
 **/
public class CloudFullScreenDialog extends Dialog implements IDialog {
    public CloudFullScreenDialog(@NonNull Context context) {
        super(context, R.style.fullScreenDialog);
        this.setCanceledOnTouchOutside(false);
        init();
    }

    @Override
    public void showDialog() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        this.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    @Override
    public void dismissDialog() {
        this.dismiss();
    }

    @Override
    public boolean isShow() {
        return this.isShowing();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.dismiss();
        }
        return true;
    }

    private void init() {
        setContentView(R.layout.dialog_full_screen);
        ImageView imageView = findViewById(R.id.iv_loading);
        final AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        imageView.post(() -> anim.start());
    }

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }
}
