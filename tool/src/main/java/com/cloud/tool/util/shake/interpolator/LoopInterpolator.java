package com.cloud.tool.util.shake.interpolator;

import android.animation.Keyframe;
import android.util.Property;

import com.cloud.tool.util.shake.BaseInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/24
 * @Desc: to->
 */
public class LoopInterpolator extends BaseInterpolator {

    public LoopInterpolator(Property property) {
        super(property);
    }

    @Override
    public List<Keyframe> buildKeyframe() {
        List<Keyframe> keyframeList = new ArrayList<>();
        keyframeList.add(Keyframe.ofFloat(0f, 1f));
        keyframeList.add(Keyframe.ofFloat(0.1f, 1.1f));
        keyframeList.add(Keyframe.ofFloat(0.2f, 1.2f));
        keyframeList.add(Keyframe.ofFloat(0.3f, 1.3f));
        keyframeList.add(Keyframe.ofFloat(0.4f, 1.4f));
        keyframeList.add(Keyframe.ofFloat(0.5f, 1.5f));
        keyframeList.add(Keyframe.ofFloat(0.6f, 1.4f));
        keyframeList.add(Keyframe.ofFloat(0.7f, 1.3f));
        keyframeList.add(Keyframe.ofFloat(0.8f, 1.2f));
        keyframeList.add(Keyframe.ofFloat(0.9f, 1.1f));
        keyframeList.add(Keyframe.ofFloat(1.0f, 1.0f));
        return keyframeList;
    }
}
