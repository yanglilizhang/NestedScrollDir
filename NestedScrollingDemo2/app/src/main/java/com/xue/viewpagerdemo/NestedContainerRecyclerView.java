package com.xue.viewpagerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-09-10
 * https://juejin.im/post/5d8c2aede51d45784d3f85e0
 * https://github.com/lennyup/AndroidHighlights
 */
public class NestedContainerRecyclerView extends RecyclerView implements NestedScrollingParent2 {

    private int itemTargetY = 0;
    private NestedScrollingParentHelper nestedScrollingParentHelper;
    private int mTouchType;
    /**
     * 当前显示的 recycleView
     */
    private WeakReference<View> targetChild;
    /**
     * targetChild对应的itemView
     */
    private WeakReference<View> itemChild;

    private float xDistance;
    private float yDistance;
    private float lastX;
    private float lastY;
    private static final int VELUE = 40;

    public NestedContainerRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public NestedContainerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedContainerRecyclerView(
            @NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        setNestedScrollingEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        //        itemTargetY = ToolUnit.dipToPx(context, 56);
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //            itemTargetY += StatusBarUtil.getStatusBarHeight(context);
        //        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.xDistance = this.yDistance = 0.0F;
                this.lastX = ev.getX();
                this.lastY = ev.getY();
                if (targetChild != null) {
                    super.onInterceptTouchEvent(ev);
                    targetChild = null;
                    return false;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                float curX = ev.getX();
                float curY = ev.getY();
                this.xDistance += Math.abs(curX - this.lastX);
                this.yDistance += Math.abs(curY - this.lastY);

                if (this.xDistance > this.yDistance) {
                    return false;
                }
                if (targetChild != null && targetChild.get() != null) {
                    int itemY = getItemChildY();
                    if (itemY == itemTargetY || itemY < lastY) {
                        return false;
                    }
                }
                this.lastX = curX;
                this.lastY = curY;
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (IndexOutOfBoundsException var3) {
            var3.printStackTrace();
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IndexOutOfBoundsException var3) {
            var3.printStackTrace();
            return false;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public void setTargetY(int targetY) {
        this.itemTargetY = targetY;
    }

    /**
     * *
     *
     * @param child  包裹target的父布局的直接子view
     * @param target 触发滑动的view
     * @param axes   滚动方向
     * @param type   滑动事件类型 true：表示父控件接受该嵌套滑动事件，后续嵌套滑动事件就会通知到该父控件 当子 view
     *               （直接或间接）调用startNestedScroll(View, int)时，会回调父控件该方法。
     */
    @Override
    public boolean onStartNestedScroll(
            @NonNull View child, @NonNull View target, int axes, int type) {
        //        Log.e("1myrecycle", "onStartNestedScroll");
        if (axes == ViewCompat.SCROLL_AXIS_HORIZONTAL) {
            return false;
        }
        this.mTouchType = type;
        targetChild = new WeakReference<View>(target);
        itemChild = new WeakReference<View>(getItemChild(child));
        return true;
    }

    /**
     * 滑动前的准备工作
     */
    @Override
    public void onNestedScrollAccepted(
            @NonNull View child, @NonNull View target, int axes, int type) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        //                Log.e("1myrecycle", "onStopNestedScroll1");
        //        targetChild = null;
        mTouchType = ViewCompat.TYPE_NON_TOUCH;
        //        stopScroll();
        nestedScrollingParentHelper.onStopNestedScroll(target, type);
    }

    /**
     * @param dxConsumed   view 消费了x方向的距离
     * @param dyConsumed   view 消费了y方向的距离
     * @param dxUnconsumed 表示 view 剩余未消费 x 方向距离
     * @param dyUnconsumed 表示 view 剩余未消费 y 方向距离 接收子View处理完滑动后的滑动距离信息, 在这里父控件可以选择是否处理剩余的滑动距离
     */
    @Override
    public void onNestedScroll(
            @NonNull View target,
            int dxConsumed,
            int dyConsumed,
            int dxUnconsumed,
            int dyUnconsumed,
            int type) {
//        Log.e(
//                "1myrecycle",
//                "onNestedScroll1--"
//                        + "dyUnconsumed--"
//                        + dyUnconsumed
//                        + "--canScroll-1-"
//                        + target.canScrollVertically(1)
//                        + "--canScroll-(-1-"
//                        + target.canScrollVertically(-1)
//                        + "--itemChildY--"
//                        + getItemChildY());
        if (dyUnconsumed == 0) {
            return;
        }
        if (type == ViewCompat.TYPE_TOUCH) {
            if (dyUnconsumed > 0) {
                if ((!target.canScrollVertically(1) || getItemChildY() != itemTargetY)
                        && canScrollVertically(1)) {
                    scrollBy(0, dyUnconsumed);
                }
            } else {
                if ((!target.canScrollVertically(-1) || getItemChildY() != itemTargetY)
                        && canScrollVertically(-1)) {
                    scrollBy(0, dyUnconsumed);
                }
            }
        } else if (type == ViewCompat.TYPE_NON_TOUCH) {
            if (dyUnconsumed > 0) {
                //判断一个view是否到顶或者到底的方法：
                //canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
                //canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                boolean canscroll = target.canScrollVertically(1);
                if ((!canscroll || getItemChildY() != itemTargetY) && canScrollVertically(1)) {
                    scrollBy(0, dyUnconsumed);
                }
            } else {
                boolean canscroll = target.canScrollVertically(-1);
                if ((!canscroll || getItemChildY() != itemTargetY) && canScrollVertically(-1)) {
                    scrollBy(0, dyUnconsumed);
                    if (getItemChildY() > getHeight() + dyUnconsumed) {
                        //                        JDLog.i("1myrecycle", "onNestedScroll1--fling");
                        fling(0, dyUnconsumed * VELUE);
                    }
                }
            }
        }
    }

    /**
     * @param dx 表示 view 本次 x 方向的滚动的总距离，单位：像素
     * @param dy 表示 view 本次 y 方向的滚动的总距离，单位：像素 在子View消费滑动事件前，优先响应滑动操作，消费部分或全部滑动距离。
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, int[] consumed, int type) {
        if (dy != 0) {
            if (type == ViewCompat.TYPE_TOUCH && itemChild != null && itemChild.get() != null) {
                int y = getItemChildY();
                if (dy > 0) {
                    if (y != itemTargetY) {
                        if (!canScrollVertically(1)) {
                            scrollBy(0, y - itemTargetY);
                            consumed[1] = y - itemTargetY;
                        } else {
                            scrollBy(0, dy);
                            y -= getItemChildY();
                            consumed[1] = y;
                        }
                    } else if (!target.canScrollVertically(1) && canScrollVertically(1)) {
                        scrollBy(0, dy);
                        y -= getItemChildY();
                        consumed[1] = y;
                    }

                } else {
                    if (target instanceof RecyclerView) {
                        RecyclerView tRecyclerView = (RecyclerView) target;
                        if ((tRecyclerView.computeVerticalScrollOffset() <= 0
                                || y - itemTargetY > 1)
                                && canScrollVertically(-1)) {
                            scrollBy(0, dy);
                            y -= getItemChildY();
                            consumed[1] = y;
                        }
                    } else {
                        if ((!target.canScrollVertically(-1) || y - itemTargetY > 1)
                                && canScrollVertically(-1)) {
                            scrollBy(0, dy);
                            y -= getItemChildY();
                            consumed[1] = y;
                        }
                    }
                }
            }
            if (type == ViewCompat.TYPE_NON_TOUCH && itemChild != null && itemChild.get() != null) {
                int y = getItemChildY();
                if (dy > 0) {
                    if ((!target.canScrollVertically(1) || y != itemTargetY)
                            && canScrollVertically(1)) {
                        scrollBy(0, dy);
                        y -= getItemChildY();
                        consumed[1] = y;
                    }
                } else {
                    if ((!target.canScrollVertically(-1) || y != itemTargetY)
                            && canScrollVertically(-1)) {
                        scrollBy(0, dy);
                        y -= getItemChildY();
                        consumed[1] = y;
                    }
                }
            }
        }
    }

    /**
     * 处于拖动状态时，会调用该方法，回调父控件的onNestedScroll方法，传递当前 view 滑动距离详情给到父控件
     */
    @Override
    public boolean dispatchNestedScroll(
            int dxConsumed,
            int dyConsumed,
            int dxUnconsumed,
            int dyUnconsumed,
            int[] offsetInWindow,
            int type) {
        //        Log.e("1myrecycle", "dispatchNestedScroll");
        if (mTouchType == type || dyUnconsumed == 0) {
            return super.dispatchNestedScroll(
                    dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
        }
        if (dyUnconsumed > 0) {
            if (targetChild != null && targetChild.get() != null) {
                boolean isAttache =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                                ? targetChild.get().isAttachedToWindow()
                                : true;
                if (targetChild.get().canScrollVertically(1) && isAttache) {
                    if (targetChild.get() instanceof RecyclerView) {
                        ((RecyclerView) targetChild.get()).fling(0, dyUnconsumed * VELUE);
                    } else {
                        targetChild.get().scrollBy(0, dyUnconsumed);
                    }
                    return true;
                }
            }
        } else {
            if (canScrollVertically(-1)) {
                fling(0, dyUnconsumed * VELUE);
                return true;
            }
        }
        return super.dispatchNestedScroll(
                dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        // Re-dispatch up the tree by default
//        Log.e("1myrecycle", "onNestedFling");
        if (velocityY != 0) {
            if (velocityY > 0) {
                if ((!target.canScrollVertically(1) || getItemChildY() != itemTargetY)
                        && canScrollVertically(1)) {
                    fling((int) velocityX, (int) velocityY);
                    return true;
                }
            } else {
                if ((!target.canScrollVertically(-1) || getItemChildY() != itemTargetY)
                        && canScrollVertically(-1)) {
                    //                    JDLog.i("1myrecycle", "onNestedFling------");
                    fling((int) velocityX, (int) velocityY);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        // Re-dispatch up the tree by default
        //        Log.i("1myrecycle", "onNestedPreFling");
        //        if (velocityY != 0) {
        //            if (velocityY > 0) {
        //                boolean canscroll = target.canScrollVertically(1);
        //                if ((!canscroll || getItemChildY() != 0)
        //                        && canScrollVertically(1)) {
        //                    fling((int) velocityX, (int) velocityY);
        //                    return true;
        //                }
        //            } else {
        //                boolean canscroll = target.canScrollVertically(-1);
        //                if ((!canscroll || getItemChildY() != 0)
        //                        && canScrollVertically(-1)) {
        //                    JDLog.i("1myrecycle", "onNestedPreFling------");
        //                    fling((int) velocityX, (int) velocityY);
        //                    return true;
        //                }
        //            }
        //        }
        return false;
    }

    //注意：要准确设置子RecyclerView的高度，也就是targetView的高度，推荐高度为
    //recyclerView.getHeight - itemTargetY
    private View getItemChild(View target) {
        ViewParent parent = target.getParent();
        if (parent == null) {
            return null;
        }
        if (parent == this) {
            return target;
        } else if (parent instanceof View) {
            return getItemChild((View) parent);
        }
        return null;
    }

    private int getItemChildY() {
        if (itemChild == null || itemChild.get() == null) {
            return -10000;
        }
        return (int) (itemChild.get().getY() + 0.5f);
    }
}

