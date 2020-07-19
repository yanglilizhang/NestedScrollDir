package com.app.behavior.behavior.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * 封装了对水平和垂直方向偏移
 * ViewOffsetBehavior 的作用是方便改变控件的位置和获取偏移量
 * @param <V>
 */
public class ViewOffsetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
//    当我们考虑一个滑动交互时，不要把滑动看做一个连续过程，
//    而要拆分成多个单独的循环过程，连续的滑动只不过是单独循环过程
//    在时间上不断重复而已；而滑动的单个循环过程，说到底都是对View进行偏移处理。
//    当看到一个复杂交互效果的时候，要学会拆分，一个是刚说的时间上拆分，
//    另一个方面就是要能拆分成多个单独效果的合成

    private ViewOffsetHelper mViewOffsetHelper;

    private int mTempTopBottomOffset = 0;
    private int mTempLeftRightOffset = 0;

    public ViewOffsetBehavior() {
    }

    public ViewOffsetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 主要是创建ViewOffsetHelper、获取真实偏移量并且将child偏移到正确位置。
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        // First let lay the child out
        layoutChild(parent, child, layoutDirection);

        if (mViewOffsetHelper == null) {
            mViewOffsetHelper = new ViewOffsetHelper(child);
        }
        mViewOffsetHelper.onViewLayout();

        if (mTempTopBottomOffset != 0) {
            mViewOffsetHelper.setTopAndBottomOffset(mTempTopBottomOffset);
            mTempTopBottomOffset = 0;
        }
        if (mTempLeftRightOffset != 0) {
            mViewOffsetHelper.setLeftAndRightOffset(mTempLeftRightOffset);
            mTempLeftRightOffset = 0;
        }

        return true;
    }

    protected void layoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        // Let the parent lay it out by default
        parent.onLayoutChild(child, layoutDirection);
    }

    public boolean setTopAndBottomOffset(int offset) {
        if (mViewOffsetHelper != null) {
            return mViewOffsetHelper.setTopAndBottomOffset(offset);
        } else {
            mTempTopBottomOffset = offset;
        }
        return false;
    }

    public boolean setLeftAndRightOffset(int offset) {
        if (mViewOffsetHelper != null) {
            return mViewOffsetHelper.setLeftAndRightOffset(offset);
        } else {
            mTempLeftRightOffset = offset;
        }
        return false;
    }

    public int getTopAndBottomOffset() {
        return mViewOffsetHelper != null ? mViewOffsetHelper.getTopAndBottomOffset() : 0;
    }

    public int getLeftAndRightOffset() {
        return mViewOffsetHelper != null ? mViewOffsetHelper.getLeftAndRightOffset() : 0;
    }
}
