package com.cloud.tool.util.shake.interpolator;

import android.animation.Keyframe;
import android.util.Property;

import com.cloud.tool.util.shake.BaseInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/24
 * @Desc: to-> 震动插值器
 */
public class ShockInterpolator extends BaseInterpolator {
    public ShockInterpolator(Property property) {
        super(property);
    }

    @Override
    public List<Keyframe> buildKeyframe() {
        List<Keyframe> keyframeList = new ArrayList<>();
        keyframeList.add(Keyframe.ofFloat(0f, 0f));
        keyframeList.add(Keyframe.ofFloat(0.1f, 3f));
        keyframeList.add(Keyframe.ofFloat(0.2f, -3f));
        keyframeList.add(Keyframe.ofFloat(0.3f, 3f));
        keyframeList.add(Keyframe.ofFloat(0.4f, -3f));
        keyframeList.add(Keyframe.ofFloat(0.5f, 3f));
        keyframeList.add(Keyframe.ofFloat(0.6f, -3f));
        keyframeList.add(Keyframe.ofFloat(0.7f, 3f));
        keyframeList.add(Keyframe.ofFloat(0.8f, -3f));
        keyframeList.add(Keyframe.ofFloat(0.9f, 3f));
        keyframeList.add(Keyframe.ofFloat(1.0f, 0f));
        return keyframeList;
    }
}
