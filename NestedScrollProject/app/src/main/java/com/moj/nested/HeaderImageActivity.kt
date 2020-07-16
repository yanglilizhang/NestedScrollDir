package com.moj.nested

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_header_image.*

/**
 * @author : moj
 * @date : 2019/8/10
 * @description : 头部是图片的样例，如用户详情页
 */
class HeaderImageActivity : AppCompatActivity() {

    companion object {
        fun startToMe(context: Context) {
            val intent = Intent(context, HeaderImageActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_header_image)
        supportActionBar?.hide()

        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBar, offset ->

            var s = 1f - (Math.abs(offset) * 1.0f / (appBar.height - toolBar.height)) * 2

            if (s < 0) {
                s = 0f
            }

            headerIv.scaleX = s
            headerIv.scaleY = s

        })
    }
}
