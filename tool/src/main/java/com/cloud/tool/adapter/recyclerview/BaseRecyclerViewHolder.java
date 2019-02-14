package com.cloud.tool.adapter.recyclerview;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Author: xb.zou
 * @Date: 2019/2/14
 * @Desc: to-> RecyclerView的ViewHolder
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArrayCompat<View> mViews;

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        mViews = new SparseArrayCompat<>();
    }

    public <V extends View> V getView(@IdRes int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mViews.put(resId, view);
        }

        return (V) view;
    }
}
