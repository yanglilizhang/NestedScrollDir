//package com.xue.viewpagerdemo.nestedScrolling;
////https://juejin.im/post/5d8c2aede51d45784d3f85e0
//public class NestedScrollingChild2 {
//
//    /**
//     * 开启一个嵌套滑动
//     *
//     * @param axes 支持的嵌套滑动方法，分为水平方向，竖直方向，或不指定
//     * @param type 滑动事件类型
//     * @return 如果返回true, 表示当前子控件已经找了一起嵌套滑动的view
//     */
//     boolean startNestedScroll(int axes, int type) {
//    }
//
//    /**
//     * 在子控件滑动前，将事件分发给父控件，由父控件判断消耗多少
//     *
//     * @param dx             水平方向嵌套滑动的子控件想要变化的距离 dx<0 向右滑动 dx>0 向左滑动
//     * @param dy             垂直方向嵌套滑动的子控件想要变化的距离 dy<0 向下滑动 dy>0 向上滑动
//     * @param consumed       子控件传给父控件数组，用于存储父控件水平与竖直方向上消耗的距离，consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离
//     * @param offsetInWindow 子控件在当前window的偏移量
//     * @return 如果返回true, 表示父控件已经消耗了
//     */
//    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
//    }
//
//
//    /**
//     * 当父控件消耗事件后，子控件处理后，又继续将事件分发给父控件,由父控件判断是否消耗剩下的距离。
//     *
//     * @param dxConsumed     水平方向嵌套滑动的子控件滑动的距离(消耗的距离)
//     * @param dyConsumed     垂直方向嵌套滑动的子控件滑动的距离(消耗的距离)
//     * @param dxUnconsumed   水平方向嵌套滑动的子控件未滑动的距离(未消耗的距离)
//     * @param dyUnconsumed   垂直方向嵌套滑动的子控件未滑动的距离(未消耗的距离)
//     * @param offsetInWindow 子控件在当前window的偏移量
//     * @return 如果返回true, 表示父控件又继续消耗了
//     */
//    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
//    }
//
//    /**
//     * 子控件停止嵌套滑动
//     */
//    public void stopNestedScroll(int type) {
//    }
//
//
//    /**
//     * 当子控件产生fling滑动时，判断父控件是否处拦截fling，如果父控件处理了fling，那子控件就没有办法处理fling了。
//     *
//     * @param velocityX 水平方向上的速度 velocityX > 0  向左滑动，反之向右滑动
//     * @param velocityY 竖直方向上的速度 velocityY > 0  向上滑动，反之向下滑动
//     * @return 如果返回true, 表示父控件拦截了fling
//     */
//    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
//    }
//
//    /**
//     * 当父控件不拦截子控件的fling,那么子控件会调用该方法将fling，传给父控件进行处理
//     *
//     * @param velocityX 水平方向上的速度 velocityX > 0  向左滑动，反之向右滑动
//     * @param velocityY 竖直方向上的速度 velocityY > 0  向上滑动，反之向下滑动
//     * @param consumed  子控件是否可以消耗该fling，也可以说是子控件是否消耗掉了该fling
//     * @return 父控件是否消耗了该fling
//     */
//    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
//    }
//
//    /**
//     * 设置当前子控件是否支持嵌套滑动，如果不支持，那么父控件是不能够响应嵌套滑动的
//     *
//     * @param enabled true 支持
//     */
//    public void setNestedScrollingEnabled(boolean enabled) {
//    }
//
//    /**
//     * 当前子控件是否支持嵌套滑动
//     */
//    public boolean isNestedScrollingEnabled() {
//    }
//
//    /**
//     * 判断当前子控件是否拥有嵌套滑动的父控件
//     */
//    public boolean hasNestedScrollingParent(int type) {
//    }
//
//
//}
