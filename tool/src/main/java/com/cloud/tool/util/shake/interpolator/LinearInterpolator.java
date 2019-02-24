package com.cloud.tool.util.shake.interpolator;

import android.animation.Keyframe;
import android.util.Property;

import com.cloud.tool.util.shake.BaseInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/24
 * @Desc: to-> 线性插值器
 */
public class LinearInterpolator extends BaseInterpolator {
    public LinearInterpolator(Property property) {
        super(property);
    }

    @Override
    public List<Keyframe> buildKeyframe() {
        List<Keyframe> keyframeList = new ArrayList<>();
        keyframeList.add(Keyframe.ofFloat(0f, 0f));
        keyframeList.add(Keyframe.ofFloat(0.1f, 0.1f));
        keyframeList.add(Keyframe.ofFloat(0.2f, 0.2f));
        keyframeList.add(Keyframe.ofFloat(0.3f, 0.3f));
        keyframeList.add(Keyframe.ofFloat(0.4f, 0.4f));
        keyframeList.add(Keyframe.ofFloat(0.5f, 0.5f));
        keyframeList.add(Keyframe.ofFloat(0.6f, 0.6f));
        keyframeList.add(Keyframe.ofFloat(0.7f, 0.7f));
        keyframeList.add(Keyframe.ofFloat(0.8f, 0.8f));
        keyframeList.add(Keyframe.ofFloat(0.9f, 0.9f));
        keyframeList.add(Keyframe.ofFloat(1.0f, 1.0f));
        return keyframeList;
    }
}
