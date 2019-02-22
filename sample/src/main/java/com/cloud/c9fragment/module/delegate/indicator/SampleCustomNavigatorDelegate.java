package com.cloud.c9fragment.module.delegate.indicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cloud.c9fragment.R;
import com.cloud.c9fragment.adapter.IndicatorPagerAdapter;
import com.cloud.c9fragment.third.indicators.navigator.ScaleCircleNavigator;
import com.cloud.common.component.delegate.Cloud9Delegate;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/22
 * @Desc: to-> 自定义指示器的标题
 */
public class SampleCustomNavigatorDelegate extends Cloud9Delegate {
    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private IndicatorPagerAdapter mExamplePagerAdapter = new IndicatorPagerAdapter(mDataList);

    private ViewPager mViewPager;

    @Override
    public Object setLayout() {
        return R.layout.delegate_indicator_custom_navigator;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        mViewPager = rootView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator1(rootView);
        initMagicIndicator2(rootView);
        initMagicIndicator3(rootView);
    }

    private void initMagicIndicator1(View rootView) {
        MagicIndicator magicIndicator = rootView.findViewById(R.id.magic_indicator1);
        CircleNavigator circleNavigator = new CircleNavigator(this.getContext());
        circleNavigator.setCircleCount(CHANNELS.length);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(index -> mViewPager.setCurrentItem(index));
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator2(View rootView) {
        MagicIndicator magicIndicator = rootView.findViewById(R.id.magic_indicator2);
        CircleNavigator circleNavigator = new CircleNavigator(this.getContext());
        circleNavigator.setFollowTouch(false);
        circleNavigator.setCircleCount(CHANNELS.length);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(index -> mViewPager.setCurrentItem(index));
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator3(View rootView) {
        MagicIndicator magicIndicator = rootView.findViewById(R.id.magic_indicator3);
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this.getContext());
        scaleCircleNavigator.setCircleCount(CHANNELS.length);
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        scaleCircleNavigator.setCircleClickListener(index -> mViewPager.setCurrentItem(index));
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }
}
