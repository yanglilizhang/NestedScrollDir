package com.moj.nested

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * https://github.com/m5314/NestedScrollProject
 */
class MainActivity : AppCompatActivity() {

    val data = mutableListOf("布局用behavior", "嵌套", "用户详情页")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object : RecyclerView.Adapter<MyViewHolder>(){
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
                var v=LayoutInflater.from(this@MainActivity).inflate(android.R.layout.simple_list_item_1, p0, false)
                return MyViewHolder(v)
            }

            override fun getItemCount(): Int = data.size

            override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
                p0.bind(p1, data[p1])
            }

        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(index : Int, s: String){
            itemView.findViewById<TextView>(android.R.id.text1).text = s
            itemView.setOnClickListener {
                when(index){
                    0 -> ViewOffsetActivity.startToMe(itemView.context)
                    1 -> ViewPagerActivity.startToMe(itemView.context)
                    2 -> HeaderImageActivity.startToMe(itemView.context)
                    else -> Toast.makeText(itemView.context, "bug", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

