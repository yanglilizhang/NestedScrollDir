package com.funnywolf.hollowkit.scroll.behavior

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.scene.group.UserVisibleHintGroupScene
import com.funnywolf.hollowkit.R
import com.funnywolf.hollowkit.douban.view.ToolbarView
import com.funnywolf.hollowkit.utils.dp
import com.funnywolf.hollowkit.utils.simpleInit
import com.funnywolf.hollowkit.utils.toast
import com.funnywolf.hollowkit.utils.westWorldHolderBackgroundColor
import com.funnywolf.hollowkit.view.scroll.behavior.BehavioralScrollView
import com.funnywolf.hollowkit.view.scroll.behavior.CollapsingHeaderBehavior

/**
 * @author https://github.com/funnywolfdadada
 * @since 2020/6/27
 */
class CollapsingHeaderScene: UserVisibleHintGroupScene() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        val context = inflater.context

        val toolbarHeight = 50.dp
        val toolbar = ToolbarView(context).apply {
            setup("电视", "西部世界 第三季", R.drawable.poster_westworld_season_3, Color.BLACK)
            setListeners(View.OnClickListener { navigationScene?.pop() }, null)
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, toolbarHeight)
        }

        val headerHeight = 260.dp
        val headerBgHeight = (headerHeight * 1.2).toInt()
        val headerBgView = ImageView(context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            setImageResource(R.drawable.poster_westworld_season_3)
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerBgHeight)
        }
        val headerView = View(context).apply {
            alpha = 0F
            setBackgroundColor(Color.BLACK)
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight)
            setOnClickListener {
                context.toast("Click header!")
            }
        }
        val rv = RecyclerView(context).apply {
            simpleInit(55, westWorldHolderBackgroundColor)
        }

        val collapsingHeader = BehavioralScrollView(context)

        collapsingHeader.apply {
            setupBehavior(CollapsingHeaderBehavior(rv, headerView))
            onScrollChangedListeners.add {
                val sy = it.scrollY.toFloat()
                toolbar.process = sy / headerHeight
                headerView.alpha = if (sy < 0) { 0F } else { sy / headerHeight }

                headerBgView.layoutParams?.also { lp ->
                    lp.height = headerBgHeight - collapsingHeader.scrollY
                    headerBgView.requestLayout()
                }
            }
        }
        return FrameLayout(context).apply {
            addView(headerBgView)
            addView(collapsingHeader)
            addView(toolbar)
        }
    }

}