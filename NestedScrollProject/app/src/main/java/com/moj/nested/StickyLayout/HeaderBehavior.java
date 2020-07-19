package com.moj.nested.StickyLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.tabs.TabLayout;

public class HeaderBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    private int childTop = 0;
    private int dependentViewTop = -1;


    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        return dependency instanceof TabLayout;
    }


    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        Log.e("**", "--------->childTop=" + childTop);
        if (childTop < 0) {
            Log.e("**", "--------->childTop<0");
            child.offsetTopAndBottom(childTop);
        } else {
            Log.e("**", "--------->childTop>0");
            child.offsetTopAndBottom(0);//布局到的位置
        }
        return true;
    }

    //被观察View变化的时候回调用的方法
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        Log.e("**", "------>dependentViewTop=" + dependentViewTop);
        if (dependentViewTop != -1) {
            int top = child.getTop();
            //偏移量dy
            int dy = dependency.getTop() - dependentViewTop;

            if (dy > -top) {
                dy = -top;
            }
            child.offsetTopAndBottom(dy);//LinearLayout在TabLayout移动时而移动
        }
        dependentViewTop = dependency.getTop();
        childTop = child.getTop();

        return true;
    }
}
