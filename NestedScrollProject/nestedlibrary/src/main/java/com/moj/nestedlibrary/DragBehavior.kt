package com.moj.nestedlibrary

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.CoordinatorLayout.Behavior
import android.util.AttributeSet
import android.view.View

/**
 * @author : moj
 * @date : 2019/8/9
 * @description :
 */
class DragBehavior<V : View> : Behavior<V> {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


}