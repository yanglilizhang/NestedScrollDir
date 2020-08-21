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

public class MyFabBehavior extends CoordinatorLayout.Behavior<View> {
    private static final FastOutSlowInInterpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private float viewY;//控件距离coordinatorLayout底部距离

    public MyFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull
            View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {

        if (child.getVisibility() == View.VISIBLE && viewY == 0) {
            viewY = coordinatorLayout.getHeight() - child.getY();
        }

        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View
            child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy > 0 && child.getVisibility() == View.VISIBLE) {
            hide(child);
        } else if (dy < 0 && child.getVisibility() == View.INVISIBLE) {
            show(child);

        }
    }

    private void hide(final View view) {
        //以图中心消失
//        ScaleAnimation animator = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation
//                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animator.setDuration(200);
//        animator.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                view.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//        });
//        view.startAnimation(animator);
        //todo 垂直方向移动消失（从上向下）
        ViewPropertyAnimator animator = view.animate().translationY(viewY).setInterpolator
                (INTERPOLATOR).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                show(view);
            }
        });
        animator.start();
    }


    private void show(final View view) {
        //以图中心显示
//        ScaleAnimation animator = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation
//                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animator.setDuration(200);
//        animator.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                view.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//        });
//        view.startAnimation(animator);
        //todo 垂直方向移动显示（从下向上）

        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator
                (INTERPOLATOR).setDuration(200).setListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }


            @Override
            public void onAnimationCancel(Animator animation) {
                hide(view);
            }
        });
        animator.start();
    }

}