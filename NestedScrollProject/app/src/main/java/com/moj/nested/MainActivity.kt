package com.moj.nested

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.moj.nested.page.TwoActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * https://github.com/m5314/NestedScrollProject
 */
class MainActivity : AppCompatActivity() {

    val data = mutableListOf("布局用behavior", "嵌套", "用户详情页", "其他效果")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = object : androidx.recyclerview.widget.RecyclerView.Adapter<MyViewHolder>() {
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
                var v = LayoutInflater.from(this@MainActivity)
                    .inflate(android.R.layout.simple_list_item_1, p0, false)
                return MyViewHolder(v)
            }

            override fun getItemCount(): Int = data.size

            override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
                p0.bind(p1, data[p1])
            }

        }
    }

    class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(index: Int, s: String) {
            itemView.findViewById<TextView>(android.R.id.text1).text = s
            itemView.setOnClickListener {
                when (index) {
                    0 -> ViewOffsetActivity.startToMe(itemView.context)
                    1 -> ViewPagerActivity.startToMe(itemView.context)
                    2 -> HeaderImageActivity.startToMe(itemView.context)
                    3 -> TwoActivity.startToMe(itemView.context)
                    else -> Toast.makeText(itemView.context, "bug", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

