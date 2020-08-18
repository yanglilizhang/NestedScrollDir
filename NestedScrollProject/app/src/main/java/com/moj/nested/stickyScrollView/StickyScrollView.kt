package com.moj.nested.stickyScrollView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView

//https://github.com/KWANGYOUNGCHOI/StickyScrollView
//https://github.com/amarjain07/StickyScrollView
class StickyScrollView : NestedScrollView, ViewTreeObserver.OnGlobalLayoutListener {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        overScrollMode = View.OVER_SCROLL_NEVER
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    var header: View? = null
        set(value) {
            field = value
            field?.let {
                ViewCompat.setTranslationZ(it, 1f)
                it.setOnClickListener { _ ->
                    this.smoothScrollTo(scrollX, it.top)
                    callStickListener()
                }
            }
        }

    var stickListener: (View) -> Unit = {}
    var freeListener: (View) -> Unit = {}

    private var mIsHeaderSticky = false

    private var mHeaderInitPosition = 0f

    override fun onGlobalLayout() {
        Log.e("shitou", "---->" + header?.top?.toFloat());//600
        mHeaderInitPosition = header?.top?.toFloat() ?: 0f
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        val scrolly = t
        Log.e("shitou", "**---->" + scrolly)//900
        if (scrolly > mHeaderInitPosition) {
            stickHeader(scrolly - mHeaderInitPosition)
            var ss = scrolly - mHeaderInitPosition
            Log.e("shitou", "***---->" + ss)
        } else {
            freeHeader()
        }
    }

    private fun stickHeader(position: Float) {
        header?.translationY = position
        callStickListener()
    }

    private fun callStickListener() {
        if (!mIsHeaderSticky) {
            stickListener(header ?: return)
            mIsHeaderSticky = true
        }
    }

    private fun freeHeader() {
        header?.translationY = 0f
        callFreeListener()
    }

    private fun callFreeListener() {
        if (mIsHeaderSticky) {
            freeListener(header ?: return)
            mIsHeaderSticky = false
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnGlobalLayoutListener(this)
    }
}