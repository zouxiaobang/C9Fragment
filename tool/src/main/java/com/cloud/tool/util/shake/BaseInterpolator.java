package com.cloud.tool.util.shake;

import android.animation.Keyframe;
import android.util.Property;

import java.util.List;

/**
 * @Author: xb.zou
 * @Date: 2019/2/24
 * @Desc: to-> 抽象的插值器，可自定义插值器（继承自该抽象类）
 */
public abstract class BaseInterpolator {
    private Property mProperty;

    public BaseInterpolator(Property property) {
        this.mProperty = property;
    }

    public Property getProperty() {
        return mProperty;
    }

    public abstract List<Keyframe> buildKeyframe();

}
