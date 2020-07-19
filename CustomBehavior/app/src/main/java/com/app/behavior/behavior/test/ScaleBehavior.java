package com.app.behavior.behavior.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

/**
 * @author :create by ${xuwei}
 * 时间 :2019/4/20
 * <p>
 * 描述 : 自定义Behavior ：向上滑动时缩放消失，向下滑动时缩放显示
 */
public class ScaleBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private FastOutLinearInInterpolator fastOutLinearInInterpolator = new FastOutLinearInInterpolator(); //动画差值器
    private LinearOutSlowInInterpolator linearOutSlowInInterpolator = new LinearOutSlowInInterpolator();

    //根据源码可知要实现两个参数的构造方法
    public ScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //重写内嵌滑动的方法
    //开始滑动
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;//垂直滚动
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE && !isRunning) { //向下滑动
            scaleHide(child); //缩放隐藏控件
        } else if (dyConsumed < 0 && child.getVisibility() == View.INVISIBLE && !isRunning) { //向上滑动
            scaleShow(child); //缩放显示控件
        }
    }

    private void scaleShow(final V child) {
        child.setVisibility(View.VISIBLE); //开始显示动画之前，要将View设置成可见
        ViewCompat.animate(child)
                .scaleX(1) //显示时还原到初始位置
                .scaleY(1)
                .setInterpolator(linearOutSlowInInterpolator)
                .setDuration(500)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                        isRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        super.onAnimationEnd(view);
                        isRunning = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        super.onAnimationCancel(view);
                        isRunning = false;
                    }
                });
    }

    private boolean isRunning; //动画是否在执行

    private void scaleHide(final V child) {
        ViewCompat.animate(child)
                .scaleX(0) //这里是隐藏，所以这里设置成缩放到0
                .scaleY(0)
                .setDuration(500)
                .setInterpolator(fastOutLinearInInterpolator)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                        //动画开始时
                        isRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        super.onAnimationEnd(view);
                        //动画结束后，将控件设置成不可见状态
                        child.setVisibility(View.INVISIBLE);
                        isRunning = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        super.onAnimationCancel(view);
                        isRunning = false;
                    }
                });
    }
}