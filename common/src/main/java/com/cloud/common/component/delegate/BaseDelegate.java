package com.cloud.common.component.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloud.common.component.activity.ProxyActivity;
import com.cloud.common.error.CoreError;
import com.cloud.common.error.ErrorWrapperException;
import com.cloud.common.net.callback.AnnotationCallback;

import java.util.Optional;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to->
 */
public abstract class BaseDelegate extends SwipeBackFragment {
    /**
     * 组件绑定
     */
    private Unbinder mUnbinder;

    /**
     * 设置layout布局
     * 可以是ID
     * 也可以是View
     */
    public abstract Object setLayout();

    /**
     * 已经绑定好视图时调用
     */
    public abstract void onBindedView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ErrorWrapperException(CoreError.Values.LAYOUT_TYPE_ERROR);
        }

        mUnbinder = ButterKnife.bind(this, rootView);
        AnnotationCallback.getInstance().registerSubscriber(this);
        onBindedView(savedInstanceState, rootView);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        AnnotationCallback.getInstance().unregisterSubscriber(this);
        Optional.ofNullable(mUnbinder).ifPresent(unbinder -> unbinder.unbind());
    }

    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) _mActivity;
    }
}
