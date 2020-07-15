package com.jennifer.andy.nestedscrollingdemo.ui.cdl.behavior;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Author:  TextView嵌套滑动
 * Date:    https://juejin.im/post/5d43be5af265da03c81501a1
 * Description: 处理嵌套滑动的Behavior,仿照{@link android.support.design.widget.BottomSheetBehavior}
 * 对嵌套滑动相关方法不熟悉的的小伙伴可以查看{@link com.jennifer.andy.nestedscrollingdemo.ui.nested.normal_form.NestedScrollingParent2View}
 * 其实这里可以使用android.support.design.widget.ViewOffsetHelper,熟悉的小伙伴可以自己改造。
 */

public class NestedHeaderBehavior extends CoordinatorLayout.Behavior<View> {
//    向上滑动：
//    只有当TextView滑动至屏幕外时，RecyclerView才能处理内部内容的滚动。
//    向下滑动：
//    当TextView已经被划出屏幕且RecylerView中的内容不能继续向下滑动时，
//    那么就将TextView滑动至显示。否则RecyclerView单独处理内部内容的滚动。

    private WeakReference<View> mNestedScrollingChildRef;

    public static final String TAG = "NestedHeaderBehavior";

    //    需要将偏移量传递给RecyclerView，所以在NestedHeaderBehavior的onLayoutChild方法中，我们去创建了关于RecyclerView的弱引用，并设置了mOffset变量来记录TextViwe每次滑动的偏移量。
    private int mOffset;//记录当前布局的偏移量

    public NestedHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        //获取实现了NestedScrollingChild或NestedScrollingChild2接口的View。
        mNestedScrollingChildRef = new WeakReference<>(findScrollingChild(parent));
        return super.onLayoutChild(parent, child, layoutDirection);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //只要竖直方向上就拦截
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    //child:嵌套滑动对应的父类的子类(不一定是一级view) target:嵌套滑动的哪个子类
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        // 在嵌套滑动的子View未滑动之前，判断父view是否优先与子view处理(也就是父view可以先消耗，然后给子view消耗）
        View scrollingChild = mNestedScrollingChildRef.get();
        if (target != scrollingChild) {
            return;
        }

        int currentTop = child.getTop();//当前TextView的Top高度（currentTop）
        //新位置newTop
        int newTop = currentTop - dy;//根据当前偏移距离dy，计算出TextView新的Top高度(newTop)
        Log.i(TAG, "onNestedPreScroll:" + "tv高度：" + child.getHeight() + ",top=" + currentTop + ",偏移量:dy------->" + dy);
        if (dy > 0) {//向上滑动
            //处理在范围内的滚动与fling
            //判断偏移后的Top(newTop)高度是否大于负的TextView的测量的高度。
            //当TextView移出屏幕后，通过调用getTop方法获取的高度肯定为负数。这里判断是否大于等于-child.getHeight，
            // 表示的是当前TextView没有超过它的滚动范围（-child.getHeight到0)
            if (newTop >= -child.getHeight()) {
                //tv消耗滑动 -2>-4
                Log.i(TAG, "onNestedPreScroll:向上移动,tv消耗dy," + "currentTop--->" + currentTop + " newTop--->" + newTop);
                consumed[1] = dy;
                mOffset = -dy;//rv偏移量
                ViewCompat.offsetTopAndBottom(child, -dy);//移动当前TextView
                coordinatorLayout.dispatchDependentViewsChanged(child);//通知控件RecyclerView所依赖的TextView发生了改变
            } else { //当超过后，单独处理
                //表示在当前偏移距离dy下，如果TextView会超过它的滚动范围。
                // 那么我们就不能使用当前dy来移动TextView。我们只能滚动剩下的范围，
                // 也就是child.getHeight() +currentTop,(这里使用加号，
                // 是因为滚动范围为-child.getHeight到0）。
                consumed[1] = child.getHeight() + currentTop;
                Log.i(TAG, "onNestedPreScroll:" + "------>consumed[1]=" + child.getHeight() + currentTop);
                mOffset = -consumed[1];
                ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                coordinatorLayout.dispatchDependentViewsChanged(child);

            }
        }
        if (dy < 0) {//向下滑动
            if (newTop <= 0 && !target.canScrollVertically(-1)) {
                Log.i(TAG, "onNestedPreScroll:向下移动" + "currentTop--->" + currentTop + " newTop--->" + newTop);
                consumed[1] = dy;
                mOffset = -dy;
                ViewCompat.offsetTopAndBottom(child, -dy);
                coordinatorLayout.dispatchDependentViewsChanged(child);
            }
        }

    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        我们需要处理RecyclerView向下方向上未消耗的距离(dyUnconsumed)。
//        同样根据当前偏移记录计算出TextVie新的Top高度，计算出是否超出其滚功范围范围。如果没有超过，
//        则TextView向下偏移距离为-dyUnconsumed,同时记录偏移量(mOffset=-dyUnconsumed),
//        最后通知RecyclerView，TextView的位置发生了改变。反之，当前TextView的top的值是多少，
//        那么TextView就向下偏移多少。
        if (dyUnconsumed < 0) {//表示已经向下滑动到头。
            int currentTop = child.getTop();//向上top为负值，原始位置为0
            int newTop = currentTop - dyUnconsumed;
            if (newTop <= 0) {//如果当前的值在滚动范围之内。
                Log.i(TAG, "onNestedScroll: " + "dyUnconsumed--> " + dyUnconsumed + " currentTop--->" + currentTop + " newTop--->" + newTop);
                ViewCompat.offsetTopAndBottom(child, -dyUnconsumed);
                mOffset = -dyUnconsumed;
            } else {//如果当前的值大于最大的偏移量，那么就直接滚动到-currentTop就行了
                ViewCompat.offsetTopAndBottom(child, -currentTop);
                mOffset = -currentTop;
            }
            coordinatorLayout.dispatchDependentViewsChanged(child);
        }
    }

    /**
     * 这里是为了解决在CoordinatorLayout下，RecyclerView调用smoothScrollToPosition导致嵌套滑动效果失效的问题
     * 如有需要可将注释打开
     */
    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
        String message = type == ViewCompat.TYPE_NON_TOUCH ? "TYPE_NON_TOUCH" : "TYPE_TOUCH";
        Log.i(TAG, "onStopNestedScroll: " + message);
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            if (!target.canScrollVertically(-1)) {
                ViewCompat.offsetTopAndBottom(child, -child.getTop());
                coordinatorLayout.dispatchDependentViewsChanged(child);
            }
        }

    }

    /**
     * 获取实现了NestedScrollingChild或NestedScrollingChild2接口的View。
     */
    private View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }

    /**
     * 获取当前控件的偏移量
     */
    public int getOffset() {
        return mOffset;
    }

}
