package com.cloud.common.component.design;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.cloud.common.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * @Author: xb.zou
 * @Date: 2019/2/15
 * @Desc: to->
 */
public class C9SlidingMenu extends HorizontalScrollView implements OnGestureListener {
    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;

    private int mScreenWidth; // 屏幕宽度
    private int mMenuWidth;

    private int mMenuRightPadding = 30;

    private boolean once;
    private boolean isScale = true;

    private GestureDetector detector = new GestureDetector(this);

    /**
     * 当使用自定义属性时调用
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public C9SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 获取自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.C9SlidingMenu, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.C9SlidingMenu_RightPadding) {
                mMenuRightPadding = a.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 50,
                        context.getResources().getDisplayMetrics()));
            }
        }
        // 获取屏幕宽度
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        // 这句话有多重要
        detector.setIsLongpressEnabled(true);

    }

    public C9SlidingMenu(Context context) {
        // 一个参数构造方法 去调用 两个参数构造方法
        this(context, null);
    }

    /**
     * 未使用自定义属性时调用
     *
     * @param context
     * @param attrs
     */
    public C9SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0); // 两个参数构造方法调用 三个参数构造方法

    }

    /**
     * 触摸操作 通过设置偏移量 将menu隐藏起来
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        detector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    /**
     * 设置子view的宽和高 设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
                    - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置子view位置
     *
     * @param changed 通过设置偏移量 将mMenu隐藏
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.post(() -> smoothScrollTo(mMenuWidth, 0));
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    // 滑动
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    // 长按
    @Override
    public void onLongPress(MotionEvent e) {

    }

    // 按，滑动
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (velocityX > 10.0) {
            this.post(() -> smoothScrollTo(-mMenuWidth, 0));
        } else {
            this.post(() -> smoothScrollTo(mMenuWidth, 0));
        }

        return false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (!isScale) {
            return;
        }

        float scale = l * 1.0f / mMenuWidth;
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;

        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, 0.2f + 0.8f * (1 - scale));    //透明度设置

        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);
    }

    public void setScale(boolean isScale) {
        this.isScale = isScale;
    }
}
