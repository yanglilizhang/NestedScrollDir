package com.moj.nested.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/**
 * Created by hushendian on 2017/9/6.
 * 第一种：重写layoutDependsOn、onDependentViewChanged
 */

public class FooterBehaviorDependAppBar extends CoordinatorLayout.Behavior<View> {
    public FooterBehaviorDependAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        float translationY = Math.abs(dependency.getTop() - 20);//减掉20是为了解决底部布局漏一点问题
//        Log.d("FooterBehaviorDependAppBar", "onDependentViewChanged: " + translationY);
//        child.setTranslationY(translationY);
        return true;
    }
}