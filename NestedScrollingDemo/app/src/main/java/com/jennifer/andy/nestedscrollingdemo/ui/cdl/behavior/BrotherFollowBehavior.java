package com.jennifer.andy.nestedscrollingdemo.ui.cdl.behavior;

import android.content.Context;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.util.AttributeSet;
import android.view.View;

import com.jennifer.andy.nestedscrollingdemo.view.DependedView;

/**
 * Author:  andy.xwt
 * Date:    2019-07-11 10:53
 * Description:跟随行为
 */

public class BrotherFollowBehavior extends CoordinatorLayout.Behavior<View> {

    public BrotherFollowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 确定依赖关系
     * @param parent
     * @param child      自己
     * @param dependency 被依赖的View
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof DependedView;
//        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) dependency.getLayoutParams();
//        if (lp.getBehavior() instanceof TargetBehavior) {
//            return true;
//        }
//        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        // 当依赖View发生变化时，child就可以相应做出一些改变
        child.setY(dependency.getBottom() + 50);
        child.setX(dependency.getX());
        return true;
//        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) dependency.getLayoutParams();
//        if (lp.getBehavior() instanceof TargetBehavior) {
//            TargetBehavior behavior = (TargetBehavior) lp.getBehavior();
//            moveHeaderView(behavior, child);
//            return true;
//        }
//        return super.onDependentViewChanged(parent, child, dependency);
    }
}
