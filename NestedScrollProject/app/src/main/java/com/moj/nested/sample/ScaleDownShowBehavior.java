package com.moj.nested.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScaleDownShowBehavior extends FloatingActionButton.Behavior {


    public ScaleDownShowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull
            FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target,
                                       int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull
            FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int
                                       dxUnconsumed, int dyUnconsumed, int type) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && child.getVisibility() == View.VISIBLE) {
            hide(child);
        } else if (dyConsumed < 0 || dyUnconsumed < 0 && child.getVisibility() == View.INVISIBLE) {
            show(child);
        }
    }

    private void hide(final View view) {
        //以图中心消失
        ScaleAnimation animator = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animator.setDuration(200);
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        view.startAnimation(animator);
        //todo 垂直方向移动消失（从上向下）
//        ViewPropertyAnimator animator = view.animate().translationY(viewY).setInterpolator
//                (INTERPOLATOR).setDuration(200).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                view.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                show(view);
//            }
//        });
//        animator.start();
    }


    private void show(final View view) {
        //以图中心显示
        ScaleAnimation animator = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animator.setDuration(200);
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        view.startAnimation(animator);
        //todo 垂直方向移动显示（从下向上）
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
//        animator.setDuration(200).addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                view.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                hide(view);
//            }
//        });
//        animator.start();
//        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator
//                (INTERPOLATOR).setDuration(200).setListener(new AnimatorListenerAdapter() {
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                view.setVisibility(View.VISIBLE);
//            }
//
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                hide(view);
//            }
//        });
//        animator.start();
    }


}