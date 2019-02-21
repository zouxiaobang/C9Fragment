package com.cloud.c9fragment.module.delegate.indicator;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.cloud.c9fragment.R;
import com.cloud.c9fragment.adapter.IndicatorPagerAdapter;
import com.cloud.common.component.delegate.Cloud9Delegate;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.OnClick;

/**
 * @Author: xb.zou
 * @Date: 2019/2/21
 * @Desc: to-> 动态标题条的Style
 * 动态改变标题条中的标题个数
 */
public class SampleDynamicTabDelegate extends Cloud9Delegate {
    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};
    private List<String> mDataList = new ArrayList<String>(Arrays.asList(CHANNELS));
    private IndicatorPagerAdapter mExamplePagerAdapter = new IndicatorPagerAdapter(mDataList);

    private ViewPager mViewPager;
    private MagicIndicator mMagicIndicator;
    private CommonNavigator mCommonNavigator;
    private Toast mToast;

    @Override
    public Object setLayout() {
        return R.layout.delegate_indicator_dynamic_tab;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        mViewPager = rootView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator1(rootView);
    }

    private void initMagicIndicator1(View rootView) {
        mMagicIndicator = rootView.findViewById(R.id.magic_indicator1);
        mMagicIndicator.setBackgroundColor(Color.parseColor("#d43d3d"));
        mCommonNavigator = new CommonNavigator(this.getContext());
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(v -> mViewPager.setCurrentItem(index));
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);

        mToast = Toast.makeText(this.getProxyActivity(), "", Toast.LENGTH_SHORT);
    }

    private void randomPage() {
        mDataList.clear();
        int total = new Random().nextInt(CHANNELS.length);
        for (int i = 0; i <= total; i++) {
            mDataList.add(CHANNELS[i]);
        }

        mCommonNavigator.notifyDataSetChanged();    // must call firstly
        mExamplePagerAdapter.notifyDataSetChanged();

        mToast.setText("" + mDataList.size() + " page");
        mToast.show();
    }

    @OnClick(R.id.btn_random_page)
    public void onViewClicked() {
        randomPage();
    }
}
