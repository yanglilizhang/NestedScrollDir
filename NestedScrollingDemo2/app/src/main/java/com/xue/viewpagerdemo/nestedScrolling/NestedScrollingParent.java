package com.xue.viewpagerdemo.nestedScrolling;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

public interface NestedScrollingParent {

    //当子View开始滑动时，会触发这个方法，判断接下来是否进行嵌套滑动，
    //返回false，则表示不使用嵌套滑动
    boolean onStartNestedScroll(@NonNull View child, @NonNull View target, @ViewCompat.ScrollAxis int axes);

    //onStartNestedScroll如果返回true，那么接下来就会调用这个方法，用来做一些初始化操作，一般可以忽略
    void onNestedScrollAccepted(@NonNull View child, @NonNull View target, @ViewCompat.ScrollAxis int axes);

    //嵌套滑动结束时会触发这个方法
    void onStopNestedScroll(@NonNull View target);

    //子View滑动时会触发这个方法，dyConsumed代表子View滑动的距离，dyUnconsumed代表子View本次滑动未消耗的距离，比如RecyclerView滑到了边界，那么会有一部分y未消耗掉
    void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                        int dxUnconsumed, int dyUnconsumed);

    //子View开始滑动时，会触发这个回调，dy表示滑动的y距离，consumed数组代表父View要消耗的距离,假如consumed[1] = dy,那么子View就不会滑动了
    void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed);

    //当子View fling时，会触发这个回调,consumed代表速度是否被子View消耗掉，比如RecyclerView滑动到了边界，那么它显然没法消耗本次的fling
    boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed);

    //当子View要开始fling时，会先询问父View是否要拦截本次fling，返回true表示要拦截，那么子View就不会惯性滑动了
    boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY);

    //表示目前正在进行的嵌套滑动的方向，值有ViewCompat.SCROLL_AXIS_HORIZONTAL 或者ViewCompat.SCROLL_AXIS_VERTICAL或者SCROLL_AXIS_NONE
    @ViewCompat.ScrollAxis
    int getNestedScrollAxes();
}

