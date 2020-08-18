package com.moj.nested.hencoder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.moj.nested.R
import com.moj.nested.behavior.ScrollLayoutBehavior
import com.moj.nested.huawei.SearchActivity
import kotlinx.android.synthetic.main.activity_scroll_layout.*

class ScrollActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.nested_scalable_image_view)
        setContentView(R.layout.nested_scroll_view)
    }
}