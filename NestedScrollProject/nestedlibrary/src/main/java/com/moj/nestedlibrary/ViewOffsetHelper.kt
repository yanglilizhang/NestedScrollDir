package com.moj.nestedlibrary

import androidx.core.view.ViewCompat
import android.view.View

/**
 * @author : moj
 * @date : 2019/8/9
 * @description : 同系统的 ViewOffsetBehavior
 */
open class ViewOffsetHelper(private val view: View) {
    var layoutTop: Int = 0
        private set
    var layoutLeft: Int = 0
        private set
    private var offsetTop: Int = 0
    private var offsetLeft: Int = 0

    fun onViewLayout() {
        this.layoutTop = this.view.top
        this.layoutLeft = this.view.left
        this.updateOffsets()
    }

    private fun updateOffsets() {
        ViewCompat.offsetTopAndBottom(this.view, this.offsetTop - (this.view.top - this.layoutTop))
        ViewCompat.offsetLeftAndRight(this.view, this.offsetLeft - (this.view.left - this.layoutLeft))
    }

    fun setTopAndBottomOffset(offset: Int): Boolean {
        if (this.offsetTop != offset) {
            this.offsetTop = offset
            this.updateOffsets()
            return true
        } else {
            return false
        }
    }

    fun setLeftAndRightOffset(offset: Int): Boolean {
        if (this.offsetLeft != offset) {
            this.offsetLeft = offset
            this.updateOffsets()
            return true
        } else {
            return false
        }
    }

    fun getTopAndBottomOffset(): Int {
        return this.offsetTop
    }

    fun getLeftAndRightOffset(): Int {
        return this.offsetLeft
    }
}

