package com.xue.viewpagerdemo.nestedScrolling;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

public interface NestedScrollingChild {
    //设置当前子View是否支持嵌套滑动
    void setNestedScrollingEnabled(boolean enabled);

    //当前子View是否支持嵌套滑动
    boolean isNestedScrollingEnabled();

    //开始嵌套滑动，对应Parent的onStartNestedScroll
    boolean startNestedScroll(@ViewCompat.ScrollAxis int axes);

    //停止本次嵌套滑动，对应Parent的onStopNestedScroll
    void stopNestedScroll();

    //true表示这个子View有一个支持嵌套滑动的父View
    boolean hasNestedScrollingParent();

    //通知父View子View开始滑动了，对应父View的onNestedScroll方法
    boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
            int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow);

    //通知父View即将开始滑动了，对应父View的onNestedPreScroll方法
    boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed,
            @Nullable int[] offsetInWindow);

    //通知父View开始Fling了，对应Parent的onNestedFling方法
    boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);

    //通知父View要开始fling了，对应Parent的onNestedPreFling方法
    boolean dispatchNestedPreFling(float velocityX, float velocityY);
}
