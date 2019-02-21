package com.cloud.c9fragment.module.delegate.indicator;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cloud.c9fragment.R;
import com.cloud.c9fragment.adapter.IndicatorPagerAdapter;
import com.cloud.common.component.delegate.Cloud9Delegate;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/21
 * @Desc: to-> 不带标题，只带指示器的标题条
 **/
public class SampleNoTabOnlyIndicatorDelegate extends Cloud9Delegate {
    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "NOUGAT", "DONUT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private IndicatorPagerAdapter mExamplePagerAdapter = new IndicatorPagerAdapter(mDataList);

    private ViewPager mViewPager;

    @Override
    public Object setLayout() {
        return R.layout.delegate_indicator_no_tab_only_indicator;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator1(rootView);
        initMagicIndicator2(rootView);
    }

    private void initMagicIndicator1(View rootView) {
        MagicIndicator magicIndicator = rootView.findViewById(R.id.magic_indicator1);
        magicIndicator.setBackgroundColor(Color.LTGRAY);
        CommonNavigator commonNavigator = new CommonNavigator(this.getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                return new DummyPagerTitleView(context);
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float lineHeight = context.getResources().getDimension(R.dimen.small_navigator_height);
                indicator.setLineHeight(lineHeight);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator2(View rootView) {
        MagicIndicator magicIndicator = rootView.findViewById(R.id.magic_indicator2);
        CommonNavigator commonNavigator = new CommonNavigator(this.getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                return new DummyPagerTitleView(context);
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                TriangularPagerIndicator indicator = new TriangularPagerIndicator(context);
                indicator.setReverse(true);
                float smallNavigatorHeight = context.getResources().getDimension(R.dimen.small_navigator_height);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setTriangleHeight((int) smallNavigatorHeight);
                indicator.setLineColor(Color.parseColor("#e94220"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }
}
