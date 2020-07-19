package com.moj.nested.StickyLayout;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.tabs.TabLayout;

public class BottomBarBehavior extends CoordinatorLayout.Behavior {
    
    private int dependentViewTop = -1;

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof TabLayout;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        View tab = parent.getChildAt(1);
        child.offsetTopAndBottom(parent.getBottom() - tab.getTop());
        return true;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (dependentViewTop != -1){
            int bottom = child.getBottom();
            int dy = dependency.getTop() - dependentViewTop;
            if (dy > -bottom){
                dy = -bottom;
            }
            child.offsetTopAndBottom(dy);
        }
        dependentViewTop = dependency.getTop();
        return true;
    }


}
