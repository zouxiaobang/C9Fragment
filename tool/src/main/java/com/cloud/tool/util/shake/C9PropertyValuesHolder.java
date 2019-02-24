package com.cloud.tool.util.shake;

import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.util.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/24
 * @Desc: to-> 属性值类，使用插值器来构建一个属性值将要变化的列表
 */
public class C9PropertyValuesHolder {
    private List<BaseInterpolator> mInterpolatorList;

    public C9PropertyValuesHolder() {
        mInterpolatorList = new ArrayList<>();
    }

    public void registerInterpolator(BaseInterpolator interpolator) {
        mInterpolatorList.add(interpolator);
    }

    List<PropertyValuesHolder> build() {
        List<PropertyValuesHolder> propertyValuesHolderList = new ArrayList<>();

        for (BaseInterpolator interpolator: mInterpolatorList) {
            List<Keyframe> keyframeList = interpolator.buildKeyframe();
            Property property = interpolator.getProperty();
            int keyframeSize = keyframeList.size();
            Keyframe[] keyframes
                    = keyframeList.toArray(new Keyframe[keyframeSize]);
            PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe(property, keyframes);


            propertyValuesHolderList.add(holder);
        }

        return propertyValuesHolderList;
    }
}
