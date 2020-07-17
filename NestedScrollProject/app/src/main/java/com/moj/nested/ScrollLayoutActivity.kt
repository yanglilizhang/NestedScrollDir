package com.moj.nested

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.moj.nested.behavior.ScrollLayoutBehavior
import com.moj.nested.huawei.SearchActivity
import kotlinx.android.synthetic.main.activity_scroll_layout.*

/**
 * https://github.com/msdx/behavior-master
 * @since 2018-11-27
 */
class ScrollLayoutActivity : AppCompatActivity() {

    companion object {
        fun startToMe(context: Context) {
            val intent = Intent(context, ScrollLayoutActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_layout)
        val behavior = ScrollLayoutBehavior.from(frame_layout)
        behavior.setScrollLayoutCallback(object : ScrollLayoutBehavior.ScrollLayoutCallback() {
            override fun onStateChanged(viewGroup: ViewGroup, oldState: Int, newState: Int) {
                // 在这里的根据所切换的状态，可以控制其他视图的显示隐藏
            }

            override fun onSlide(
                viewGroup: ViewGroup,
                behavior: ScrollLayoutBehavior<*>?,
                position: Int
            ) {
                // 这里通过位置的回调，可以设置一些渐变的变化，比如设置状态栏或标题栏的显示或隐藏
            }

        })
        frame_layout.post { behavior.showCollapsed() }
    }
}