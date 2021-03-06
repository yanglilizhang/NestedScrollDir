package com.moj.nested.behavior;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * @Author yangsanning
 * @ClassName FollowUpBehavior
 * @Description 控件跟随控件移动（这里监听的是一个TextView）
 * @Date 2020/4/30
 */
public class FollowUpBehavior extends CoordinatorLayout.Behavior<View> {

    public FollowUpBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 建立关系
     *
     * @param parent     父容器
     * @param child      跟随者
     * @param dependency 拖动控件
     */
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        // 这里监听的是一个TextView
        return dependency instanceof TextView || super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * @param parent
     * @param child      跟随者
     * @param dependency 拖动者-依赖对象
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        // 偏移量
        int topOffset = dependency.getTop() - child.getTop();
        Log.e("**", "dependency的top=" + dependency.getTop() + ",child的top=" + child.getTop());
        int width = parent.getContext().getResources().getDisplayMetrics().widthPixels;
        int leftOffset = dependency.getLeft() - (width - child.getRight());
        //平移
        ViewCompat.offsetTopAndBottom(child, topOffset);
        ViewCompat.offsetLeftAndRight(child, -leftOffset);
        return super.onDependentViewChanged(parent, child, dependency);
    }
}