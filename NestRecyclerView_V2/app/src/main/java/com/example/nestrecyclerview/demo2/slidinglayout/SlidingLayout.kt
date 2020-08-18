package com.example.nestrecyclerview.demo2.slidinglayout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.nestrecyclerview.demo2.R

/**
 *  @desc
 *  @auth ${user}
 *  @time 2019/1/7  10:29
 */
@CoordinatorLayout.DefaultBehavior(SlidingLayoutBehavior::class)
class SlidingLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var backgound: Int = 0
    var headerHeight: Int = 0
    var txt: String = ""
    lateinit var headerView: TextView

    init {
        initView(context, attrs)
    }

    @SuppressLint("WrongConstant")
    private fun initView(context: Context?, attrs: AttributeSet?) {
        context?.obtainStyledAttributes(attrs, R.styleable.slidinglayout)?.let {
            backgound = it.getColor(
                R.styleable.slidinglayout_backgound,
                resources.getColor(R.color.colorPrimary)
            )
            txt = "12456789"
            it.recycle()
        }
        LayoutInflater.from(context).inflate(R.layout.sliding_layout, this)
        headerView = findViewById<TextView>(R.id.tv).apply {
            text = txt
            setBackgroundColor(backgound)
        }
        findViewById<RecyclerView>(R.id.rv)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = SimpleAdapter(
                R.layout.item,
                mutableListOf(
                    "a",
                    "b",
                    "c",
                    "d",
                    "e",
                    "a",
                    "b",
                    "c",
                    "d",
                    "e",
                    "a",
                    "b",
                    "c",
                    "d",
                    "e"
                )
            )
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            headerHeight = headerView.measuredHeight
        }

    }

    inner class SimpleAdapter(layout: Int, data: MutableList<String>?) :
        BaseQuickAdapter<String, BaseViewHolder>(layout, data) {

        override fun convert(holder: BaseViewHolder, item: String) {
            holder!!.setText(R.id.textview, item!!)
        }
    }


}