package com.moj.nestedlibrary

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

/**
 * @author : moj
 * @date : 2019/8/9
 * @description : 布局行为, 如何布局呢？
 *
 * 规则：比如现在有一个View称之为a， 另外一个View称之为b，我想b在a的右边。
 * 1.（b依赖a,所以需要为b设置behavior-解释为b依赖a）并设置layout_anchor为a-解释为b绑定a
 * 明确了布局要求之后，我们知道b的布局需要依赖a的位置，所以我们可以把ViewOffsetBehavior添加在b上，
 * 并设置layout_anchor为a，这可以理解为b绑定了a，
 * 此时我们设置b的layout_gravity属性，这个属性表明a与b的对齐关系，如设置left|top，表示a与b的左边和上边对齐，
 * 然后设置b的layout_anchorGravity属性，表明b与a的位置，left表示b在a的左边。
 *
 * ps：如果不设置layout_gravity属性，只设置layout_anchorGravity，那么结果是，如果是left，会是b的right边在a的中间，即b
 * 向右平移的a的一半宽度
 *
 * ps: 使用此种布局方式时margin属性无效，如需添加间隔，可以使用Space实现。
 */
open class ViewOffsetBehavior<V : View> : CoordinatorLayout.Behavior<V> {

    private var viewOffsetHelper: ViewOffsetHelper? = null
    private var tempTopBottomOffset = 0
    private var tempLeftRightOffset = 0

    val topAndBottomOffset: Int
        get() = if (this.viewOffsetHelper != null) this.viewOffsetHelper!!.getTopAndBottomOffset() else 0

    val leftAndRightOffset: Int
        get() = if (this.viewOffsetHelper != null) this.viewOffsetHelper!!.getLeftAndRightOffset() else 0

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        this.layoutChild(parent, child, layoutDirection)
        if (this.viewOffsetHelper == null) {
            this.viewOffsetHelper = ViewOffsetHelper(child)
        }

        this.viewOffsetHelper!!.onViewLayout()
        if (this.tempTopBottomOffset != 0) {
            this.viewOffsetHelper!!.setTopAndBottomOffset(this.tempTopBottomOffset)
            this.tempTopBottomOffset = 0
        }

        if (this.tempLeftRightOffset != 0) {
            this.viewOffsetHelper!!.setLeftAndRightOffset(this.tempLeftRightOffset)
            this.tempLeftRightOffset = 0
        }

        return true
    }

    protected fun layoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int) {
        parent.onLayoutChild(child, layoutDirection)
    }

    fun setTopAndBottomOffset(offset: Int): Boolean {
        if (this.viewOffsetHelper != null) {
            return this.viewOffsetHelper!!.setTopAndBottomOffset(offset)
        } else {
            this.tempTopBottomOffset = offset
            return false
        }
    }

    fun setLeftAndRightOffset(offset: Int): Boolean {
        if (this.viewOffsetHelper != null) {
            return this.viewOffsetHelper!!.setLeftAndRightOffset(offset)
        } else {
            this.tempLeftRightOffset = offset
            return false
        }
    }

}
