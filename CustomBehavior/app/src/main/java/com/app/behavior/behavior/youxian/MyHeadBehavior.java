package com.app.behavior.behavior.youxian;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import static androidx.customview.widget.ViewDragHelper.INVALID_POINTER;


public class MyHeadBehavior extends CoordinatorLayout.Behavior {

    private int initTop = -0xffff;
    private boolean isBeingDragged;
    private int lastMotionY;
    private int activePointerId;

    private Handler handler = new Handler();
    private View mySelf;
    private Runnable recover = new Runnable() {
        @Override
        public void run() {
            LogUtil.xuTianXiong().e("run");
            if (isTouch) {//如果手还在触摸则不弹回
                recoverRun();
            } else {
                recover();
            }
        }
    };

    boolean isTouch = false;
    private View dependency;

    public MyHeadBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHeadBehavior() {

    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View directTargetChild, View target, int nestedScrollAxes, int type) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (initTop == -0xffff) {
            initTop = child.getTop();
            parent.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            isTouch = true;
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            isTouch = false;
                            break;
                    }

                    return false;
                }
            });
        }
        if (dependency instanceof NestedScrollView) {
            this.dependency = dependency;
        }
        return false;
    }

    private VelocityTracker velocityTracker;

    @Override
    public boolean onInterceptTouchEvent(
            @NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
        mySelf = child;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isBeingDragged = false;
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();
                int top = child.getTop();
                if (top >= 0) {
                    boolean isTouchInHead = parent.isPointInChildBounds(child, x, y);
                    boolean isTouchInScroll = parent.isPointInChildBounds(dependency, x, y);
                    if (isTouchInHead&&!isTouchInScroll) {
                        lastMotionY = y;
                        activePointerId = ev.getPointerId(0);
                        ensureVelocityTracker();
                        isBeingDragged = true;
                    }
                }

                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                isBeingDragged = false;
                this.activePointerId = INVALID_POINTER;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
            }
        }

        if (velocityTracker != null) {
            velocityTracker.addMovement(ev);
        }


        return isBeingDragged;
    }

    private void ensureVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
    }


    @Override
    public boolean onTouchEvent(
            @NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();
                if (parent.isPointInChildBounds(child, x, y)) {
                    lastMotionY = y;
                    activePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                } else {
                    return false;
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int activePointerIndex = ev.findPointerIndex(activePointerId);
                if (activePointerIndex == -1) {
                    return false;
                }

                final int y = (int) ev.getY(activePointerIndex);
                int dy = lastMotionY - y;
                lastMotionY = y;
                // We're being dragged so scroll the ABL
                customScroll(child, dy);//手滑动比例距离和控件实际滑动距离
                break;
            }

            case MotionEvent.ACTION_UP:
                if (velocityTracker != null) {
                    velocityTracker.addMovement(ev);
                    velocityTracker.computeCurrentVelocity(1000);
                    float yvel = velocityTracker.getYVelocity(activePointerId);
                    LogUtil.xuTianXiong().e(yvel);
                    //fling(parent, child, -child.getMeasuredHeight(), 0, yvel);
                }
                mySelf = child;
                recover();

            case MotionEvent.ACTION_CANCEL: {
                isBeingDragged = false;
                activePointerId = INVALID_POINTER;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }

                break;
            }
        }

        if (velocityTracker != null) {
            velocityTracker.addMovement(ev);
        }

        return true;
    }

    private boolean isAnimalStart = false;

    private void recover() {

        if (isAnimalStart) {
            return;
        }
        int height = mySelf.getMeasuredHeight();
        int currentTop = mySelf.getTop();
        float dy = currentTop - initTop;
        LogUtil.xuTianXiong().e("recover" + dy);
        LogUtil.xuTianXiong().e("height" + height);
        LogUtil.xuTianXiong().e("currentTop" + currentTop);


        if (dy == 0) {//如果没有偏移则直接返回
            return;
        }
        if (Math.abs(dy) < 20) {//完全展开时如果偏移量过小则不触发动画
            mySelf.setTop(initTop);
            return;
        }

        if (Math.abs(height + currentTop) < 20) {//完全收起时如果偏移量过小则不触发动画
            mySelf.setTop(-height);
            return;
        }

        final int duration = 250;
        // 开启移动动画
        ValueAnimator valueAnimator;

        if (height / 2.0 > Math.abs(dy)) {//向上滑动没有过一半的高则返回完全展开
            valueAnimator = ValueAnimator.ofFloat(dy, getMaxTop());
        } else {//向上滑动超过一半的高则完全收起
            valueAnimator = ValueAnimator.ofFloat(dy, getMiniTop());
        }
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mySelf.setTop((int) (initTop + value));
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimalStart = false;
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimalStart = true;
                super.onAnimationStart(animation);
            }
        });
        valueAnimator.start();
    }

    /**
     * 完全收起时的顶部高度
     *
     * @return
     */
    private int getMiniTop() {
        return -(mySelf.getMeasuredHeight());
    }


    /**
     * 完全打开时候的顶部高度
     *
     * @return
     */
    private int getMaxTop() {
        return 0;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        LogUtil.xuTianXiong().e("onNestedPreScroll" + dy);
        //dy>0则向上滑动，dy<0则向下滑动
        if (target instanceof NestedScrollView) {
            mySelf = child;
            NestedScrollView nestedScrollView = (NestedScrollView) target;
            int scrollY = nestedScrollView.getScrollY();
            if (dy > 0 || (dy < 0 && scrollY == 0)) {
                int height = child.getMeasuredHeight();
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) target.getLayoutParams();
                int topMargin = layoutParams.topMargin;
                int realDy = (int) Math.ceil((float) dy * (float) height / (float) topMargin);//头部和下方滑动列表滑动距离不一致，需要转换
                LogUtil.xuTianXiong().e("realDy" + realDy);

                boolean isScroll = customScroll(child, realDy);
                recoverRun();
                if (isScroll) {
                    consumed[1] = dy;
                }
            }
        }
    }

    private void recoverRun() {
        if (handler.hasCallbacks(recover)) {
            handler.removeCallbacks(recover);
        }
        handler.postDelayed(recover, 150);
    }

    /**
     * 滑动
     */
    private boolean customScroll(View child, int dy) {
        int oldTop = child.getTop();
        int height = child.getMeasuredHeight();
        int newTop = oldTop - dy;

        if (newTop > getMaxTop()) {
            newTop = getMaxTop();
        } else if (newTop < getMiniTop()) {
            newTop = getMiniTop();
        }

        if ((dy < 0 && oldTop != 0) || (dy > 0 && -oldTop != height)) {//向上边界top=-height,向下边界top=0
            child.setTop(newTop);
            return true;
        }
        return false;
    }

}