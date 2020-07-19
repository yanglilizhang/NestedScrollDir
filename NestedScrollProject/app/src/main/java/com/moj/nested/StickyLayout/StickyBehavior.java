package com.moj.nested.StickyLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class StickyBehavior extends CoordinatorLayout.Behavior {

    private int maxOffset = 0;
    private int minOffset = 0;
    private static boolean isInit = true;
    private boolean customScrollToTop = false;

    public StickyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        View head = parent.getChildAt(0);
        //放在head下面
        if (isInit && head.getBottom() == 0) {
            child.offsetTopAndBottom(head.getMeasuredHeight());
            maxOffset = head.getMeasuredHeight();
        } else {
            child.offsetTopAndBottom(head.getBottom());
            maxOffset = head.getHeight();
            isInit = false;
        }
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int top = child.getTop();
        if (top >= minOffset) {
            customScrollToTop = dy > 0;
            //滑动并且告知子已消耗dy
            consumed[1] = scroll(child, dy, minOffset, maxOffset);
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        int top = child.getTop();
        if (top >= minOffset) {
            //子未消耗的父再消耗
            scroll(child, dyUnconsumed, minOffset, maxOffset);
        }
    }

//    @Override
//    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
//        int top = child.getTop();
//        if (top >= minOffset) {
//            //子未消耗的父再消耗
//            scroll(child, dyUnconsumed, minOffset, maxOffset);
//        }
//    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        if (Math.abs(child.getTop()) < maxOffset / 3 && customScrollToTop) {
            int duration = 100;
            scrollTo(coordinatorLayout, child, minOffset, duration);
        }
    }

    private void scrollTo(final CoordinatorLayout parent, final View child, int y, int duration) {
        customScrollToTop = false;
        final Scroller scroller = new Scroller(parent.getContext());
        scroller.startScroll(0, child.getTop(), 0, y - child.getTop(), duration);
        ViewCompat.postOnAnimation(child, new Runnable() {
            @Override
            public void run() {
                if (scroller.computeScrollOffset()) {
                    int delta = scroller.getCurrY() - child.getTop();
                    child.offsetTopAndBottom(delta);
                    parent.dispatchDependentViewsChanged(child);
                    if (child.getBottom() != 0) {

                    }
                    ViewCompat.postOnAnimation(child, this);
                }
            }
        });
    }


    private int scroll(View child, int dy, int minOffset, int maxOffset) {
        int top = child.getTop();
        int offset = clamp(top - dy, minOffset, maxOffset) - top;
        child.offsetTopAndBottom(offset);
        return -offset;
    }

    private int clamp(int i, int minOffset, int maxOffset) {
        if (i < minOffset) {
            return minOffset;
        } else if (i > maxOffset) {
            return maxOffset;
        } else {
            return i;
        }
    }
}
