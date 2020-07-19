package com.app.behavior.behavior.bottom;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

import com.app.behavior.behavior.helper.ViewOffsetBehavior2;

public class HeaderBehavior<V extends View> extends ViewOffsetBehavior2<V> {

    private static final int INVALID_POINTER = -1;

    @Nullable
    private Runnable flingRunnable;

    OverScroller scroller;

    private boolean isBeingDragged;
    private int activePointedId = INVALID_POINTER;
    private int lastMotionY;
    private int touchSlop = -1;

    @Nullable
    private VelocityTracker velocityTracker;

    public HeaderBehavior() {
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent ev) {
        assertTouchSlop(parent.getContext());
        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_MOVE && isBeingDragged) {
            return true;
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                isBeingDragged = false;
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();
                if (canDragView(child) && parent.isPointInChildBounds(child, x, y)) {
                    lastMotionY = y;
                    this.activePointedId = ev.getPointerId(0);
                    ensureVelocityTracker();
                }
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                final int activePointerId = this.activePointedId;
                if (activePointerId == INVALID_POINTER) {
                    break;
                }
                final int pointerIndex = ev.findPointerIndex(activePointerId);
                if (pointerIndex == -1) {
                    break;
                }
                final int y = (int) ev.getY(pointerIndex);
                final int yDiff = Math.abs(y - lastMotionY);
                if (yDiff > touchSlop) {
                    isBeingDragged = true;
                    lastMotionY = y;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                isBeingDragged = false;
                this.activePointedId = INVALID_POINTER;
                velocityTracker.recycle();
                velocityTracker = null;
                if (velocityTracker != null) {
                }
                break;
            }
        }
        if (velocityTracker != null) {
            velocityTracker.addMovement(ev);
        }
        return isBeingDragged;
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent ev) {
        assertTouchSlop(parent.getContext());
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();
                if (parent.isPointInChildBounds(child, x, y) && canDragView(child)) {
                    lastMotionY = y;
                    activePointedId = ev.getPointerId(0);
                    ensureVelocityTracker();
                } else {
                    return false;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int activePointerIndex = ev.findPointerIndex(activePointedId);
                if (activePointerIndex == -1) {
                    return false;
                }
                final int y = (int) ev.getY(activePointerIndex);
                int dy = lastMotionY - y;
                if (!isBeingDragged && Math.abs(dy) > touchSlop) {
                    isBeingDragged = true;
                    if (dy > 0) {
                        dy -= touchSlop;
                    } else {
                        dy += touchSlop;
                    }
                }

                if (isBeingDragged) {
                    lastMotionY = y;
                    scroll(parent, child, dy, getMaxDragOffset(child),0);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                if (velocityTracker != null) {
                    velocityTracker.addMovement(ev);
                    velocityTracker.computeCurrentVelocity(10000);
                    float yvel = velocityTracker.getYVelocity(activePointedId);
                    fling(parent, child, -getScrollRangeForDragFling(child), 0, yvel);
                }
            case MotionEvent.ACTION_CANCEL: {
                isBeingDragged=false;
                activePointedId=INVALID_POINTER;
                if (velocityTracker!=null){
                    velocityTracker.recycle();
                    velocityTracker=null;
                }
            }
            break;
        }
        if (velocityTracker!=null){
            velocityTracker.addMovement(ev);
        }
        return true;
    }

    private boolean fling(CoordinatorLayout coordinatorLayout, V layout, int minOffset, int maxOffset, float velocityY) {
        if (flingRunnable!=null){
            layout.removeCallbacks(flingRunnable);
            flingRunnable=null;
        }
        if (scroller==null){
            scroller=new OverScroller(layout.getContext());
        }
        scroller.fling(0,getTopAndBottomOffset(),0,Math.round(velocityY),
                0,0,minOffset,maxOffset);
        if (scroller.computeScrollOffset()){
            flingRunnable=new FlingRunnable(coordinatorLayout,layout);
            ViewCompat.postOnAnimation(layout,flingRunnable);
            return true;
        }else {
            onFlingFinished(coordinatorLayout,layout);
            return false;
        }
    }

    void onFlingFinished(CoordinatorLayout coordinatorLayout, V layout) {
        
    }

    private int getScrollRangeForDragFling(V view) {
        return view.getHeight();
    }

    final int scroll(CoordinatorLayout coordinatorLayout, V header, int dy, int minOffset, int maxOffset) {
        return setHeaderTopBottomOffset(coordinatorLayout,header
                ,getTopBottomOffsetForScrollingSibling()-dy,minOffset,maxOffset);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, V header, int newOffset) {
        return setHeaderTopBottomOffset(parent, header, newOffset, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V header, int newOffset,
                                    int minOffset, int maxOffset) {
        final int curOffset=getTopAndBottomOffset();
        int consumed=0;
        if (minOffset!=0&&curOffset>=minOffset&&curOffset<=maxOffset){
            newOffset= MathUtils.clamp(newOffset,minOffset,maxOffset);
            if (curOffset!=newOffset){
                setTopAndBottomOffset(newOffset);
                consumed=curOffset-newOffset;
            }
        }
        return consumed;
    }

    int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    int getMaxDragOffset(V view) {
        return -view.getHeight();
    }

    boolean canDragView(V view) {
        return false;
    }

    private void assertTouchSlop(Context context) {
        if (touchSlop < 0) {
            touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        }
    }

    private void ensureVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
    }

    private class FlingRunnable implements Runnable {

        private CoordinatorLayout parent;
        private final V layout;

        public FlingRunnable(CoordinatorLayout coordinatorLayout, V layout) {
            this.parent=coordinatorLayout;
            this.layout=layout;
        }

        @Override
        public void run() {
            if (layout!=null&&scroller!=null){
                setHeaderTopBottomOffset(parent,layout,scroller.getCurrY());
                ViewCompat.postOnAnimation(layout,this);
            }else {
                onFlingFinished(parent,layout);
            }
        }
    }
}