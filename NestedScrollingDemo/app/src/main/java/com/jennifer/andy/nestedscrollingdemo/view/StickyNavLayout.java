package com.jennifer.andy.nestedscrollingdemo.view;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jennifer.andy.nestedscrollingdemo.R;

/**
 * Author:  andy.xwt
 * Date:    2019-07-08 22:11
 * Description:
 */
public class StickyNavLayout extends LinearLayout implements NestedScrollingParent2 {
    //    为了兼容低版本并获得正确的嵌套滑动状态，
//    我们需要在onNestedScrollAccepted、onStopNestedScroll、onStopNestedScroll、
//    中调用NestedScrollingParentHelper的相应方法。
    private NestedScrollingParentHelper mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    private View mTopView;//头部view
    private View mNavView;//导航view
    private ViewPager mViewPager;//Viewpager
    private ScrollChangeListener mScrollChangeListener;
    /**
     * 父控件可以滚动的距离
     */
    private float mCanScrollDistance = 0f;

    public StickyNavLayout(Context context) {
        this(context, null);
    }

    public StickyNavLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyNavLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }

    //要获取父控件的子控件，我们可以通过onFinishInflate方法
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.sl_top_view);
        mNavView = findViewById(R.id.sl_tab);
        mViewPager = findViewById(R.id.sl_viewpager);
    }

    //    获取了子控件后,我们可以在onSizeChanged中得到，
//    可以父控件可以滑动的距离（mCanScrollDistance = 展示图片的高度 - 标题栏的高度）
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCanScrollDistance = mTopView.getMeasuredHeight() - getResources().getDimension(R.dimen.normal_title_height);
    }

    /**
     * 父控件接受嵌套滑动，不管是手势滑动还是fling 父控件都接受
     * 为了让父控件处理竖直方向上的事件
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }


    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //如果子view欲向上滑动，则先交给父view滑动 y>0&& getScrollY()<100
        boolean hideTop = dy > 0 && getScrollY() < mCanScrollDistance;
        //如果子view欲向下滑动，必须要子view不能向下滑动后，才能交给父view滑动
        boolean showTop = dy < 0 && getScrollY() >= 0 && !target.canScrollVertically(-1);
        if (hideTop || showTop) {
            //父View滑动
            scrollBy(0, dy);
            //告诉子View父view消耗的距离
            consumed[1] = dy;// consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离
        }
    }

//    通过调用View的canScrollVertically(int direction)方法来判断是否能够向下滑动，
//    其中当direcation为负数时，是检查对应View是否能够向下滑动，能，返回为true，
//    反之返回false。
//    当direcation为正数时，是检查对应View是否能够向上滑动，能，返回为true,反之返回false。


    //    因为根据嵌套滑动机制，当父控件预处理后，子控件会再消耗剩余的距离，如果子控件消耗后，
//    还有剩余的距离。那么就又会传递给父控件。
//    也就是会走onNestedScroll方法。在该方法中，我们只需要单独处理子控件的剩余的向下fling。
//    当子控件产生fling时，如果子控件消耗不完，那么就会传递给父控件。
//    也就是dyConsumed肯定是有值的，又因为我们只关心向下的fling。所以上述代码这样判断。
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.e("**", "---->子View消耗:dyConsumed=" + dyConsumed + ",子View未消耗:dyUnconsumed=" + dyUnconsumed);
        if (dyUnconsumed < 0) {//表示已经向下滑动到头，这里不用区分手势还是fling
            scrollBy(0, dyUnconsumed);
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mNestedScrollingParentHelper.onStopNestedScroll(target, type);
    }

    /**
     * 嵌套滑动时，如果父View处理了fling,那子view就没有办法处理fling了，所以这里要返回为false
     * 为了让子控件也处理fling，我们需要在onNestedPreFling方法中返回false。
     * 因为在嵌套滑动机制中，如果该方法返回true,那么子控件就没有机会处理fling了
     */
    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    //ViewPager高度的矫正
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //先测量一次
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //ViewPager修改后的高度= 总高度-TabLayout高度
        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        lp.height = getMeasuredHeight() - mNavView.getMeasuredHeight();
        mViewPager.setLayoutParams(lp);
        //因为ViewPager修改了高度，所以需要重新测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 对父控件(StickyNavaLayout)的滚动范围进行校验
     * 控制可以滑动的范围
     * 父控件(StickyNavaLayout)的滚动范围为0-mCanScrollDistance
     *
     * @param x
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {
        Log.e("**", "----->y=" + y + "(y<0向下滑动)");
        if (y < 0) {
            y = 0;
        }
        if (y > mCanScrollDistance) {
            y = (int) mCanScrollDistance;
        }
        if (mScrollChangeListener != null) {
            mScrollChangeListener.onScroll(y / mCanScrollDistance);
        }
        if (getScrollY() != y) super.scrollTo(x, y);
    }


    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }


    public interface ScrollChangeListener {
        /**
         * 移动监听
         * 当前父控件滑动的距离与最大滑动范围的比例
         *
         * @param moveRatio 移动比例
         */
        void onScroll(float moveRatio);
    }

    public void setScrollChangeListener(ScrollChangeListener scrollChangeListener) {
        mScrollChangeListener = scrollChangeListener;
    }
}
