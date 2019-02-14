package com.cloud.tool.adapter.recyclerview;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/14
 * @Desc: to-> 抽象的RecyclerView的适配器
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List<T> mItemList;

    public BaseRecyclerViewAdapter(Context context, @LayoutRes int layoutId) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mItemList = new ArrayList<T>();
    }

    public BaseRecyclerViewAdapter(Context context, @LayoutRes int layoutId, @Nullable List<T> data) {
        this.mContext = context;
        this.mItemList = data == null ? new ArrayList<T>() : data;
        if (layoutId != 0) {
            this.mLayoutId = layoutId;
        }
    }


    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new BaseRecyclerViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        convert(holder, position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setData(List<T> itemList) {
        this.mItemList = itemList == null ? new ArrayList<T>() : itemList;
        notifyDataSetChanged();
    }

    public void addData(@IntRange(from = 0) int position, @Nullable T data) {
        mItemList.add(position, data);
        notifyItemChanged(position);
        compatibilityDataSizeChanged(mItemList.size());
    }

    public void addData(@Nullable T data) {
        mItemList.add(data);
        notifyItemChanged(mItemList.size());
    }

    public void remove(int position) {
        mItemList.remove(position);
        int internalPosition = position;
        notifyItemRemoved(internalPosition);
        notifyItemRangeChanged(internalPosition, mItemList.size() - internalPosition);
    }

    protected void compatibilityDataSizeChanged(int size) {
        final int dataSize = mItemList == null ? 0 : mItemList.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }

    protected abstract void convert(BaseRecyclerViewHolder holder, int position);
}
