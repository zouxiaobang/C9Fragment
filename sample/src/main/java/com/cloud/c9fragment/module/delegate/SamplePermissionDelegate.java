package com.cloud.c9fragment.module.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.cloud.c9fragment.R;
import com.cloud.common.component.delegate.Cloud9Delegate;
import com.cloud.common.util.permission.PermissionCode;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: xb.zou
 * @Date: 2019/2/13
 * @Desc: to->
 */
public class SamplePermissionDelegate extends Cloud9Delegate {
    @BindView(R.id.tv_content)
    AppCompatTextView tvContent;
    private StringBuilder mStringBuilder = new StringBuilder();

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_permission;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        requestPermission(true, PermissionCode.Group.STORAGE, PermissionCode.Group.CONTACTS);
    }

    @Override
    public void onHasPermission(List<String> granted, boolean isAll) {
        super.onHasPermission(granted, isAll);

        mStringBuilder.append("通过的权限：\n");
        for (String permission: granted) {
            mStringBuilder.append(permission + "\n");
        }

        tvContent.setText(mStringBuilder.toString());
    }

    @Override
    public void onNoPermission(List<String> denied, boolean quick) {
        super.onNoPermission(denied, quick);

        mStringBuilder.append("\n\n未通过通过的权限：\n");
        for (String permission: denied) {
            mStringBuilder.append(permission + "\n");
        }

        tvContent.setText(mStringBuilder.toString());
    }
}
