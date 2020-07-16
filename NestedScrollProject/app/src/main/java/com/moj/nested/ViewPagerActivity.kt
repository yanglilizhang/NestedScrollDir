package com.moj.nested

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_viewpager.*

/**
 * @author : moj
 * @date : 2019/8/9
 * @description : 模拟这样一种情况，头部显示的时候，头部跟着列表滑动，
 * 当头部划出屏幕后不再出现，除非用户点击某个按钮
 * https://www.jianshu.com/p/94ceeb8bbf87
 */
class ViewPagerActivity : AppCompatActivity(){
    companion object{
        fun startToMe(context:Context){
            val intent = Intent(context, ViewPagerActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)

        viewPager.adapter = object : androidx.fragment.app.FragmentStatePagerAdapter(supportFragmentManager){
            override fun getItem(p0: Int): androidx.fragment.app.Fragment {
                return ListFragment()
            }

            override fun getCount(): Int = 4

            override fun getPageTitle(position: Int): CharSequence? {
                return "title"
            }
        }

        supportActionBar?.hide()


        //给一个按钮，用户点击可以重新弹出头部
        top.setOnClickListener {
            appBar.visibility = VISIBLE
            appBar.setExpanded(true,false)
        }
        //appBarLayout.getTotalScrollRange()方法获取最大偏移值

//        内容蒙纱
//        滚动位置达到一定阈值时显示或隐藏内容。你可以通过改变这个setContentScrim(Drawable)。
//
//        状态栏蒙纱
//        当滚动位置已经达到一定的阈值时，状态栏显示或隐藏。你可以通过改变这个setStatusBarScrim(Drawable)

        //监听头部滑动情况，当头部完全划出屏幕看不见时隐藏它，使得滑动不会再出现头部 滑动偏移监听
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, p1 ->
            //实现头部消失后不再出现的功能
            if(appBar.height + p1 == 0){
                appBar.visibility = GONE
            }
        })

    }
}
