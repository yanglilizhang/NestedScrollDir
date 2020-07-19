package com.app.behavior.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

/**
 * Behavior API 详解
 */
public class BehaviorTest extends CoordinatorLayout.Behavior {
    private static final String TAG = "BehaviorTest";
    // 列表顶部和滚动控件的距离
    private float deltaY;


    public BehaviorTest(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    /**
     * 是否给应用了Behavior的View,指定一个观察的布局
     * 当布局发生变化的时候
     * 确定所提供的子视图是否有另一个特定的同级视图作为布局依赖项。
     * 注意:本身的View会重新布局
     *
     * @param parent 顶层嵌套布局
     * @param child 绑定behavior的View(观察者,就是TextView本身)
     * @param dependency 被观察的view(被观察者，就是同级别的其他子视图)
     * @return 这里可以检查自己需要观察的试图，是返回true，否则返回false
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.i(TAG,"layoutDependsOn child " + child.getClass().getSimpleName()
                + "  dependency " + dependency.getClass().getSimpleName());
        // 这里检查被观察者（TexView观察的是滑动NestedScrollView）
        return dependency instanceof NestedScrollView;
    }


    /**
     * 是否响应子程序依赖视图(被观察者)中的更改
     * 每当依赖视图在标准布局流之外的大小或位置发生变化时，都会调用此方法
     *
     * @param parent 顶层嵌套布局
     * @param child 绑定behavior的View(观察者,就是TextView本身)
     * @param dependency 被观察的view(被观察者，就是同级别的其他子视图)
     * @return true 响应了被观察者的变化，false没有响应
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.i(TAG,"onDependentViewChanged child " + child.getClass().getSimpleName()
                + "  dependency " + dependency.getClass().getSimpleName());
        //一开始  等于0
        if (deltaY == 0) {
            //初始化两者之间的距离
            deltaY = dependency.getY() - child.getHeight();
        }
        Log.i("DN deltaY------------>",deltaY+"------------");
        //被观察者View的Y坐标 - 观察者的高度  得到两者之间剩余的距离
        float dy = dependency.getY() - child.getHeight();
        //如果距离小于0  就赋值0  如果距离大于等于0  就把剩余距离赋值给它
        dy = dy < 0 ? 0 : dy;
        //计算Y轴每次偏移的距离
        float y = -(dy / deltaY) * child.getHeight();
        Log.i("DN-------------->",y+"------------");
        //设置Y轴的偏移参数
        child.setTranslationY(y);
        return false;
    }

    /**
     *  当coordinatorLayout 的子View试图开始嵌套滑动的时候被调用
     *  任何与协调员Layout的任何直接子级相关联的行为都可能响应此事件并返回TRUE，
     *  以指示协调员Layout应该充当此滚动条的嵌套滚动父级。
     *  只有从此方法返回true的行为才会接收后续嵌套滚动事件。
     *
     * @param coordinatorLayout 顶层嵌套布局
     * @param child 绑定behavior的View(观察者,就是TextView本身)
     * @param directTargetChild 该子视图是或包含嵌套滚动操作的目标
     * @param target View启动嵌套滚动的协调员Layout的后代视图
     * @param nestedScrollAxes 嵌套滑动滑动方向，横向或者竖向
     * @param type 导致此滚动事件的输入类型
     * @return 如果行为希望接受此嵌套滚动，则为true。
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild,
                                       View target, int nestedScrollAxes, int type) {
        Log.i(TAG,"onStartNestedScroll type " + type);
        return true;
    }


    /**
     * 当正在进行的嵌套滚动即将更新时，在目标消耗任何滚动距离之前调用
     * 在nested scroll child 消费掉自己的滚动距离之前，嵌套滚动每次被nested scroll child
     * 更新都会调用onNestedPreScroll。注意有个重要的参数consumed，可以修改这个数组表示你消费
     * 了多少距离。假设用户滑动了100px,child 做了90px 的位移，你需要把 consumed［1］的值改成90，
     * 这样coordinatorLayout就能知道只处理剩下的10px的滚动。
     *
     * @param coordinatorLayout 顶层嵌套布局
     * @param child 绑定behavior的View(观察者,就是TextView本身)
     * @param target View启动嵌套滚动的协调员Layout的后代视图
     * @param dx  用户试图滚动的原始水平像素数
     * @param dy  用户试图滚动的原始垂直像素数
     * @param consumed 输出参数。消费[0]应设置为消耗的dx的距离，消费[1]应设置为所消费的dy的距离。
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed, int type) {
        Log.i(TAG,"onNestedPreScroll dx " + dx
                + "  dy " + dy);
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed,type);
    }



    /**
     * 当正在进行的嵌套滚动更新并且目标已滚动或试图滚动时调用。
     *
     * @param coordinatorLayout 顶层嵌套布局
     * @param child 绑定behavior的View
     * @param target 被观察的view(被观察者，就是同级别的其他子视图)
     * @param dxConsumed target 已经消费的x方向的距离
     * @param dyConsumed target 已经消费的y方向的距离
     * @param dxUnconsumed x 方向剩下的滚动距离
     * @param dyUnconsumed y 方向剩下的滚动距离
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.i(TAG,"onNestedScroll child " + child.getClass().getSimpleName()
                + "  dependency " + target.getClass().getSimpleName());
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,type);
    }

    /**
     *  嵌套滚动结束时被调用，这是一个清除滚动状态等的好时机。
     *
     * @param coordinatorLayout 顶层嵌套布局
     * @param child 绑定behavior的View(观察者,就是TextView本身)
     * @param target 被观察的view(被观察者，就是同级别的其他子视图)
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int type) {
        Log.i(TAG,"onStopNestedScroll child " + child.getClass().getSimpleName()
                + "  dependency " + target.getClass().getSimpleName());
        super.onStopNestedScroll(coordinatorLayout, child, target,type);
    }

    /**
     * onStartNestedScroll返回true才会触发这个方法，接受滚动处理后回调，可以在这个
     * 方法里做一些准备工作，如一些状态的重置等。
     *
     * @param coordinatorLayout 顶层嵌套布局
     * @param child 绑定behavior的View(观察者,就是TextView本身)
     * @param directTargetChild 该子视图是或包含嵌套滚动操作的目标
     * @param target 被观察的view(被观察者，就是同级别的其他子视图)
     * @param nestedScrollAxes 方向
     * @param type 导致此滚动事件的输入类型
     */
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild,
                                       View target, int nestedScrollAxes,int type) {
        Log.i(TAG,"onNestedScrollAccepted child " + child.getClass().getSimpleName()
                + "  dependency " + target.getClass().getSimpleName());
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes,type);
    }

    /**
     * 用户松开手指并且会发生惯性动作之前调用，参数提供了速度信息，可以根据这些速度信息
     * 决定最终状态，比如滚动Header，是让Header处于展开状态还是折叠状态。返回true 表
     * 示消费了fling.
     *
     * @param coordinatorLayout 顶层嵌套布局
     * @param child 绑定behavior的View(观察者,就是TextView本身)
     * @param target 被观察的view(被观察者，就是同级别的其他子视图)
     * @param velocityX x 方向的速度
     * @param velocityY y 方向的速度
     * @return true 行为消耗了，否则没有
     */
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target,
                                    float velocityX, float velocityY) {
        Log.i(TAG,"onNestedPreFling child " + child.getClass().getSimpleName()
                + "  dependency " + target.getClass().getSimpleName());
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }



    //可以重写这个方法对子View 进行重新布局
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        Log.i(TAG,"onLayoutChild child " + child.getClass().getSimpleName());
        return super.onLayoutChild(parent, child, layoutDirection);
    }



}