package com.xue.viewpagerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.xue.viewpagerdemo.model.NestedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 薛贤俊 on 2019/2/22.
 * 在外层RV之上嵌套一层自定义的FrameLayout，并开启外层RV和内层RV的嵌套滑动功能，
 * 那么我们就能在FrameLayout中接收到RV传递上来的scroll和fling事件
 * ViewGroup默认实现了Parent接口，这里我们不需要再implement一次
 */
public class NestedScrollLayout2 extends FrameLayout implements NestedScrollingParent2 {

    private View mChildView;
    /**
     * 最外层的RecyclerView
     */
    private RecyclerView mRootList;
    /**
     * 子RecyclerView
     */
    private RecyclerView mChildList;

    private int mAxes;

    public NestedScrollLayout2(@NonNull Context context) {
        super(context);
    }

    public NestedScrollLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTarget(LifecycleOwner target) {
        NestedViewModel mScrollViewModel;
        if (target instanceof FragmentActivity) {
            mScrollViewModel = new ViewModelProvider((FragmentActivity) target).get(NestedViewModel.class);
        } else if (target instanceof Fragment) {
            mScrollViewModel = new ViewModelProvider((Fragment) target).get(NestedViewModel.class);
        } else {
            throw new IllegalArgumentException("target must be FragmentActivity or Fragment");
        }
        mScrollViewModel.getChildView().observe(target, new Observer<View>() {
            @Override
            public void onChanged(@Nullable View view) {
                mChildView = view;
            }
        });
        mScrollViewModel.getChildList().observe(target, new Observer<View>() {
            @Override
            public void onChanged(@Nullable View view) {
                mChildList = (RecyclerView) view;
            }
        });
    }

    public void setRootList(RecyclerView recyclerView) {
        mRootList = recyclerView;
    }

    //当子View开始滑动时，会触发这个方法，判断接下来是否进行嵌套滑动，
    //返回false，则表示不使用嵌套滑动
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;//这里表示只有在纵向滑动时，我们才拦截事件
    }

    //onStartNestedScroll如果返回true，那么接下来就会调用这个方法，用来做一些初始化操作，一般可以忽略
    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mAxes = axes;
    }

    //嵌套滑动结束时会触发这个方法
    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mAxes = SCROLL_AXIS_NONE;
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        ////子View滑动时会触发这个方法，dyConsumed代表子View滑动的距离，dyUnconsumed代表子View本次滑动未消耗的距离，
        // 比如RecyclerView滑到了边界，那么会有一部分y未消耗掉
    }

    //onNestedPreScroll方法在嵌套滑动时判断父控件的滑动距离时尤为重要。
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        // //子View开始滑动时，会触发这个回调，dy表示滑动的y距离，consumed数组代表父View要消耗的距离,
        // 假如consumed[1] = dy,那么子View就不会滑动了
        //mChildView表示TabLayout和ViewPager的父View，比如说我们用一个LinearLayout包裹住TabLayout和ViewPager
        if (mChildView == null) {
            return;
        }
        if (target == mRootList) {
            //mChildView.getTop() RecyclerView的item
            Log.e("**", "父滑" + "mChildView.getTop()=" + mChildView.getTop() + ",dy=" + dy);
            onParentScrolling(mChildView.getTop(), dy, consumed);
        }
//        else {
//            Log.e("**", "子滑" + "mChildView.getTop()=" + mChildView.getTop() + ",dy=" + dy);
//            onChildScrolling(mChildView.getTop(), dy, consumed);
//        }
    }

    /**
     * 父列表在滑动
     *
     * @param childTop
     * @param dy
     * @param consumed
     */
    private void onParentScrolling(int childTop, int dy, int[] consumed) {
        if (childTop == 0) {
            //列表已经置顶
            if (dy > 0 && mChildList != null) {
                //还在向上滑动（dy > 0），此时滑动子列表
                mChildList.scrollBy(0, dy);//滑动子View
                consumed[1] = dy;//这句会导致子View不会滑动，consumed数组代表父View要消耗的距离
            } else {
                //下滑
                if (mChildList != null && mChildList.canScrollVertically(dy)) {
                    consumed[1] = dy;
                    mChildList.scrollBy(0, dy);
                }
            }
        } else {
            //父滑动 没到tablayout
            if (childTop < dy) {
                consumed[1] = dy - childTop;
            }
        }
    }

    private void onChildScrolling(int childTop, int dy, int[] consumed) {
        if (childTop == 0) {
            if (dy < 0) {
                //向上滑动
                if (!mChildList.canScrollVertically(dy)) {
                    consumed[1] = dy;
                    mRootList.scrollBy(0, dy);
                }
            }
        } else {
            if (dy < 0 || childTop > dy) {
                consumed[1] = dy;
                mRootList.scrollBy(0, dy);
            } else {
                //dy大于0
                consumed[1] = dy;
                mRootList.scrollBy(0, childTop);
            }
        }
    }

    @Override
    public int getNestedScrollAxes() {
        return mAxes;//表示我们只接收纵向的事件
    }

}
