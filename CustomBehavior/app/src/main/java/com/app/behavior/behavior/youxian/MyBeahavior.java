package com.app.behavior.behavior.youxian;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import com.app.behavior.R;


public class MyBeahavior extends CoordinatorLayout.Behavior {


    public MyBeahavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBeahavior() {
    }

    private View dependency;

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        boolean needDepends = dependency.getId() == R.id.head;

        if (needDepends) {
            this.dependency = dependency;
            if (headLastTop == 0xffff) {
                headLastTop = dependency.getTop();
            }
        }
        return needDepends;
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {

        if (child instanceof NestedScrollView) {//用于处理过度下滑
            commOnTouchEvent((NestedScrollView) child, ev);
            return true;
        }
        return super.onTouchEvent(parent, child, ev);
    }


    private boolean isStartOverScrollPre = false;
    /**
     * 在触摸的时候动画是否没有结束
     */
    private boolean isAnimatorStartInTouchDown = false;

    /**
     * 恢复动画是否结束
     */
    private boolean isAnimatorStart = false;

    /**
     * 在down的时候是否触摸到
     */
    private boolean isTouchScrollView = false;

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
        int action = ev.getAction();
        boolean isBeingDragged = false;
        if (child instanceof NestedScrollView) {
            NestedScrollView child1 = (NestedScrollView) child;
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    firstY = ev.getRawY();
                    int x = (int) ev.getX();
                    int y = (int) ev.getY();
                    isTouchScrollView = parent.isPointInChildBounds(child, x, y);
                    LogUtil.xuTianXiong().e("firstY down:" + firstY);
                    isStartOverScrollPre = canOverScroll(child1);
                    isAnimatorStartInTouchDown = isAnimatorStart;
                    if (isAnimatorStartInTouchDown)//如果在手触摸的时候动画没有结束，则直接拦截并不进行处理
                        isBeingDragged = true;

                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    clearTouchTag();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!isTouchScrollView) {//如果手触摸到的不是滑动布局，则不拦截
                        break;
                    }
                    if (isAnimatorStartInTouchDown) {
                        isBeingDragged = true;
                        break;
                    }
                    if (isStartOverScrollPre) {
                        float nowY = ev.getRawY();
                        float deltaY = nowY - firstY;
                        firstY = nowY;
                        LogUtil.xuTianXiong().e("firstY move:" + firstY);
                        LogUtil.xuTianXiong().e(deltaY);
                        if (deltaY > 0 && canOverScroll(child1)) {//如果满足滑动条件 则拦截
                            isBeingDragged = true;
                        } else {
                            isBeingDragged = false;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        LogUtil.xuTianXiong().e("onInterceptTouchEvent" + action + isBeingDragged);
        return isBeingDragged;
    }

    private void clearTouchTag() {
        isStartOverScrollPre = false;
    }

    // 拖动的距离 size = 4 的意思 仅仅同意拖动屏幕的1/4
    private static final int size = 4;
    private float firstY;


    private boolean isScrollUp = false;

    private void commOnTouchEvent(NestedScrollView scrollView, MotionEvent ev) {
        if (isAnimatorStartInTouchDown) {
            return;
        }
        int action = ev.getAction();
        LogUtil.xuTianXiong().e("onTouchEvent" + action);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                firstY = ev.getRawY();
                isAnimatorStartInTouchDown = isAnimatorStart;
                break;

            case MotionEvent.ACTION_UP:
                clearTouchTag();
                if (isScrollUp) {
                    isScrollUp = false;
                    ViewCompat.stopNestedScroll(scrollView);
                }
                if (isAnimatorStart) {
                    return;
                }
                recover(scrollView);
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getRawY();
                /**
                 * size=4 表示 拖动的距离为屏幕的高度的1/4
                 */
                int deltaY = (int) (nowY - firstY) / size;
                LogUtil.xuTianXiong().e(nowY);
                firstY = nowY;
                LogUtil.xuTianXiong().e("onTouchEvent" + deltaY);
                if (deltaY >= 0 && canOverScroll(scrollView)) {
                    int top = scrollView.getTop() + deltaY;
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();
                    int maxTop = layoutParams.topMargin + 200;//过度下滑最大高度200
                    if (top > maxTop) {
                        top = maxTop;
                    }
                    scrollView.setTop(top);
                } else if (deltaY < 0) {//如果不是过度下滑则让头部可以上滑
                    isScrollUp = true;
                    int[] ints = new int[2];
                    ints[1] = -deltaY;
                    ViewCompat.startNestedScroll(scrollView, ViewCompat.SCROLL_AXIS_VERTICAL);
                    ViewCompat.dispatchNestedPreScroll(scrollView, 0, -deltaY, ints, new int[2]);
                }

                break;
            default:
                break;
        }
    }

    // 开启恢复动画移动
    private void recover(final NestedScrollView scrollView) {
        int top = dependency.getTop();
        int height = dependency.getMeasuredHeight();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();
        int topMargin = 0;

        if (height / 2 > Math.abs(top)) {//向上滑动没有过一半的高则返回完全展开
            topMargin = layoutParams.topMargin;
        } else {//向上滑动超过一半的高则完全收起
            topMargin = 0;
        }

        final float dy = scrollView.getTop() - topMargin;
        if (dy == 0) {//如果没有偏移则直接返回
            return;
        }
        if (Math.abs(dy) < 20) {//如果偏移量过小则不触发动画
            scrollView.setTop(topMargin);
            return;
        }
        final float duration = 800;
        // 开启移动动画
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, duration);
        valueAnimator.setDuration((long) duration);
        final int finalTopMargin = topMargin;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float t = (float) animation.getAnimatedValue();
                float translationTime = 200;
                float y = 0;
                if (t < translationTime) {
                    y = -dy / translationTime * t + dy;
                } else if (t == translationTime || t == duration) {
                    y = 0;
                } else {
                    float percent = (t - translationTime) / (duration - translationTime);
                    double count = 4 * Math.PI * percent;
                    y = (float) (-30 * (1 - percent) * Math.sin(count));
                }
                scrollView.setTop((int) (finalTopMargin + y));
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimatorStart = false;
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimatorStart = true;
                super.onAnimationStart(animation);
            }
        });
        valueAnimator.start();
    }

    // 是否可以过渡滑动
    private boolean canOverScroll(NestedScrollView scrollView) {
        int scrollY = scrollView.getScrollY();
        int top = scrollView.getTop();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();
        int topMargin = layoutParams.topMargin;
        if (scrollY == 0 && topMargin <= top) {
            return true;
        }
        return false;
    }

    /**
     * 最后一次偏移的top值
     */
    private int headLastTop = 0xffff;

    /**
     * scrollView的原始top
     */
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        offsetTitle(parent, dependency);//位移title

        int top = dependency.getTop();

        int childTop = child.getTop();
        int height = dependency.getMeasuredHeight();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        int topMargin = layoutParams.topMargin;
        int deltY = top - headLastTop;
        LogUtil.xuTianXiong().e("onDependentViewChanged" + top);

        deltY = (int) Math.ceil((float) deltY * (float) topMargin / (float) height);//头部和下方滑动列表滑动距离不一致，需要转换

        int finalTop = child.getTop() + deltY;

        LogUtil.xuTianXiong().e("onDependentViewChanged" + finalTop);

        if (Math.abs(top + height) == 0 || finalTop < 0) {//向下滑动，最多回到原处
            finalTop = 0;
        } else if (top == 0 || -finalTop > topMargin) {//向上滑动最多滑动到margin高度
            finalTop = topMargin;
        }
        if ((childTop > 0 && deltY < 0) || topMargin > childTop && deltY > 0) {
            child.setTop(finalTop);
        }
        headLastTop = top;
        return false;
    }


    /**
     * 位移title
     */
    private void offsetTitle(@NonNull View parent, @NonNull View dependency) {
        View child = parent.findViewById(R.id.title);
        if (child == null) {
            return;
        }

        int height = child.getMeasuredHeight();

        int top = dependency.getTop();
        if (top > 0) {
            top = 0;
        } else if (top < -height) {
            top = -height;
        }
        int top1 = Math.abs(top) - height;
        child.setTop(top1);
        float alpha = ((float) Math.abs(top)) / ((float) height);
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 1) {
            alpha = 1;
        }
        child.setAlpha(alpha);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        LogUtil.xuTianXiong().e("onNestedPreScroll");
        if (dy < 0) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) target.getLayoutParams();
            int topMargin = layoutParams.topMargin;
            if (target.getTop() >= topMargin) {//如果完全展开，且向下滑动时，则停止NEST滑动
                ViewCompat.stopNestedScroll(target, type);
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

}