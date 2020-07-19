package com.moj.nested.StickyLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.tabs.TabLayout;

public class BottomBehavior extends CoordinatorLayout.Behavior {
    private boolean isInit = true;
    private int dependentViewTop = -1;

    public BottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        View head = parent.getChildAt(0);
        View stickyLayout = parent.getChildAt(1);
        //放在TabLayout下面
        child.offsetTopAndBottom(head.getBottom() + stickyLayout.getHeight());
        return true;
    }
    //HideBottomViewOnScrollBehavior
    //SwipeDismissBehavior
    //BottomSheetBehavior
    //ExtendedFloatingActionButtonBehavior

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof TabLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        Log.e("**", "----------->dependentViewTop=" + dependentViewTop);
        if (dependentViewTop != -1) {
            int dy = dependency.getTop() - dependentViewTop;
            Log.e("**", "----------->dy=" + dy);
            if (!isInit) {
                child.offsetTopAndBottom(dy);
            }
            isInit = false;
        }
        dependentViewTop = dependency.getTop();

        return true;
    }
}
