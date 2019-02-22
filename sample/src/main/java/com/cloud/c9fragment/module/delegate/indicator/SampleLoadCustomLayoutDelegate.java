package com.cloud.c9fragment.module.delegate.indicator;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.c9fragment.R;
import com.cloud.c9fragment.adapter.IndicatorPagerAdapter;
import com.cloud.common.component.delegate.Cloud9Delegate;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/22
 * @Desc: to-> 带自定义图标的标题条
 */
public class SampleLoadCustomLayoutDelegate extends Cloud9Delegate {
    private static final String[] CHANNELS = new String[]{"NOUGAT", "DONUT", "ECLAIR", "KITKAT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private IndicatorPagerAdapter mExamplePagerAdapter = new IndicatorPagerAdapter(mDataList);

    private ViewPager mViewPager;

    @Override
    public Object setLayout() {
        return R.layout.delegate_indicator_load_custom_layout;
    }

    @Override
    public void onBindedView(@Nullable Bundle savedInstanceState, View rootView) {
        mViewPager = rootView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator1(rootView);
    }

    private void initMagicIndicator1(View rootView) {
        MagicIndicator magicIndicator = rootView.findViewById(R.id.magic_indicator1);
        magicIndicator.setBackgroundColor(Color.BLACK);
        CommonNavigator commonNavigator = new CommonNavigator(this.getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

                // load custom layout
                View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
                final ImageView titleImg = customLayout.findViewById(R.id.title_img);
                final TextView titleText = customLayout.findViewById(R.id.title_text);
                titleImg.setImageResource(R.mipmap.ic_launcher);
                titleText.setText(mDataList.get(index));
                commonPagerTitleView.setContentView(customLayout);

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(Color.LTGRAY);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                        titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
                        titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                        titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
                        titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
                    }
                });

                commonPagerTitleView.setOnClickListener(v -> mViewPager.setCurrentItem(index));

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }
}
