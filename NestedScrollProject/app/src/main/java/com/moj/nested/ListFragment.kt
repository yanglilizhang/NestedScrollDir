package com.moj.nested

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * @author : moj
 * @date : 2019/8/9
 * @description : 简单的列表页，配合演示滑动嵌套
 */
class ListFragment : Fragment() {

    var mView:View?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = mView?:inflater.inflate(R.layout.fragment_list, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = object : RecyclerView.Adapter<MyHolder>(){
            override fun getItemCount(): Int = 50

            override fun onBindViewHolder(p0: MyHolder, p1: Int) {

            }

            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyHolder {
                return MyHolder(LayoutInflater.from(p0.context).inflate(android.R.layout.simple_list_item_1,p0, false))
            }

        }

        refreshView.setOnRefreshListener {
            refreshView.postDelayed({
                refreshView.isRefreshing = false
            },1000)
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            (itemView as TextView).text = "2"
        }
    }
}
