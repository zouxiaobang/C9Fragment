package com.cloud.c9fragment.module.delegate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloud.c9fragment.R;
import com.cloud.common.component.delegate.Cloud9Delegate;
import com.cloud.tool.util.qr.QrTool;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: xb.zou
 * @Date: 2019/2/22
 * @Desc: to->
 */
public class SampleQrDelegate extends Cloud9Delegate {
    @BindView(R.id.tv_qr)
    TextView tvQr;
    @BindView(R.id.iv_qr)
    AppCompatImageView ivQr;
    @BindView(R.id.tv_qr_content)
    TextView tvQrContent;

    private static final String URL = "https://www.baidu.com";
    private static final String TEXT = "没错，我很帅!";
    private Bitmap mQrImg;

    @Override
    public Object setLayout() {
        return R.layout.delegate_qr;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        mQrImg = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);

    }

    @OnClick({R.id.btn_create_qr_with_url, R.id.btn_create_qr_with_text, R.id.btn_create_qr_with_logo, R.id.btn_decode_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_qr_with_url:
                createQrWithUrl();
                break;
            case R.id.btn_create_qr_with_text:
                createQrWithText();
                break;
            case R.id.btn_create_qr_with_logo:
                createQrWithLogo();
                break;
            case R.id.btn_decode_qr:
                decodeQr();
                break;
        }
    }

    private void decodeQr() {
        ivQr.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(ivQr.getDrawingCache());
        if (bitmap != null) {
            String text = QrTool.decodeQrImg(bitmap);
            tvQrContent.setText(text);
        }
        ivQr.setDrawingCacheEnabled(false);
    }

    private void createQrWithLogo() {
        Bitmap qrImgWithLogo = QrTool.createQrImgWithLogo(URL, mQrImg);
        Glide.with(this.getProxyActivity()).load(qrImgWithLogo).into(ivQr);
        tvQr.setText(URL);
    }

    private void createQrWithText() {
        Bitmap qrImg = QrTool.createQrImg(TEXT);
        Glide.with(this.getProxyActivity()).load(qrImg).into(ivQr);
        tvQr.setText(TEXT);
    }

    private void createQrWithUrl() {
        Bitmap qrImg = QrTool.createQrImg(URL);
        Glide.with(this.getProxyActivity()).load(qrImg).into(ivQr);
        tvQr.setText(URL);
    }
}
