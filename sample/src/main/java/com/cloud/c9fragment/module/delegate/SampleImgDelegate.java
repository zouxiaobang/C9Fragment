package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cloud.c9fragment.R;
import com.cloud.common.component.delegate.Cloud9Delegate;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Author: xb.zou
 * @Date: 2019/2/13
 * @Desc: to->
 */
public class SampleImgDelegate extends Cloud9Delegate {
    @BindView(R.id.circle)
    CircleImageView circle;
    @BindView(R.id.image_view)
    AppCompatImageView imageView;
    @BindView(R.id.btn_show_circle)
    AppCompatButton btnShowCircle;
    @BindView(R.id.btn_show_image)
    AppCompatButton btnShowImage;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_img;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick({R.id.btn_show_circle, R.id.btn_show_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_circle:
                Glide.with(SampleImgDelegate.this.getProxyActivity())
                        .load(SampleImgDelegate.this.getProxyActivity()
                                .getDrawable(R.drawable.img)).into(circle);
                break;
            case R.id.btn_show_image:
                Glide.with(SampleImgDelegate.this.getProxyActivity())
                        .load(SampleImgDelegate.this.getProxyActivity()
                                .getDrawable(R.drawable.img)).into(imageView);
                break;
        }
    }
}
