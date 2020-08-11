package com.moj.nested.stickyScrollView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.moj.nested.R
import kotlinx.android.synthetic.main.activity_main_sticky.*

class StickyScrollViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_sticky)

        val adapter = DummyRecyclerAdapter(arrayListOf("1", "2", "3", "4", "5", "6", "7"))
        recycler_view.apply {
            this.adapter = adapter
            this.isNestedScrollingEnabled = false
        }

        sticky_scroll_view.also {
            it.header = header_view
            it.stickListener = { _ ->
                Log.d("ss", "stickListener")
            }
            it.freeListener = { _ ->
                Log.d("ss", "freeListener")
            }
        }
    }
}