package com.cloud.c9fragment.module.delegate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cloud.c9fragment.R;
import com.cloud.c9logger.logger2.log.Logger;
import com.cloud.common.component.delegate.Cloud9Delegate;
import com.cloud.tool.adapter.recyclerview.BaseRecyclerViewAdapter;
import com.cloud.tool.adapter.recyclerview.BaseRecyclerViewHolder;
import com.cloud.tool.adapter.recyclerview.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

/**
 * @Author: xb.zou
 * @Date: 2019/2/14
 * @Desc: to->
 */
public class SampleRecyclerViewDelegate extends Cloud9Delegate {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private List<String> mOriData = Arrays.asList(
            "Aasdfa",
            "Basewrq",
            "Casdfa",
            "Dydfsa",
            "Ewerw",
            "Fasdfased",
            "Gcaseweq",
            "Haeqtda",
            "IJKLMNOPQRSTUVWXYZ"
    );
    private List<String> mDataList = new ArrayList<>();
    private SampleAdapter mAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sample_recycler_view;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        initData();
        //设置布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getProxyActivity()));
        mAdapter = new SampleAdapter(this.getProxyActivity(), R.layout.list_sample_item);
        //设置适配器
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(mDataList);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getProxyActivity(), 5));

        //设置刷新圆圈的颜色
        refreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
        //下拉更新 -- 添加数据
        refreshLayout.setOnRefreshListener(() -> {
            String data = "123123";
            mAdapter.addData(0, data);
//            mDataList.add(data);
            refreshLayout.setRefreshing(false);
        });
    }

    private void initData() {
        mOriData.stream().map(d -> mDataList.add(d)).collect(Collectors.toList());
    }


    /**
     * 继承自定义的适配器BaseRecyclerViewAdapter
     * 重写其构造方法、convert方法
     * 在convert()中可以通过控件id
     */
    private class SampleAdapter extends BaseRecyclerViewAdapter<String> {

        public SampleAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(BaseRecyclerViewHolder holder, int position) {
            TextView tvSample = holder.getView(R.id.tv_sample);
            String data = mDataList.get(position);
            tvSample.setText(data);
            tvSample.setOnClickListener(v -> {
                showToast(data);
                Logger.d(data);
                mAdapter.remove(position);
            });
        }
    }
}
