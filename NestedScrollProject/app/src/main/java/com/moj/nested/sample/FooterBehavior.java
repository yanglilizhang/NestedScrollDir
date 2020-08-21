package com.moj.nested.sample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Interpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 * Created by hushendian on 2017/9/6.
 * 第二种：重写onStartNestedScroll、onNestedPreScroll
 */
public class FooterBehavior extends CoordinatorLayout.Behavior<View> {
    private int sinceDirectionChange;

    private static final FastOutSlowInInterpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull
            View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View
            child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

        if (dy > 0 && sinceDirectionChange < 0 || dy < 0 && sinceDirectionChange > 0) {
            child.animate().cancel();
            sinceDirectionChange = 0;
        }
        sinceDirectionChange += dy;
        if (sinceDirectionChange > child.getHeight() && child.getVisibility() == View.VISIBLE) {
            // TODO: 2017/9/6 隐藏布局

            hide(child);
        } else if (sinceDirectionChange < 0 && child.getVisibility() == View.INVISIBLE) {
            show(child);


        }
    }

    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator
                (INTERPOLATOR).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                hide(view);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });

        animator.start();

    }

    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight())
                .setInterpolator(INTERPOLATOR).setDuration(200).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void
                    onAnimationEnd(Animator animation) {
                        view.setVisibility(View.INVISIBLE);//原作者博客写的是设置VIEW的状态为Gone，但是测试是
                    }

                    @Override
                    public void
                    onAnimationCancel(Animator animation) {

                        show(view);
                    }
                });
        animator.start();
    }


}