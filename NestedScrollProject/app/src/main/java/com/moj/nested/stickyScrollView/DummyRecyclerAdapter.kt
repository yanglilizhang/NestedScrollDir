package com.moj.nested.stickyScrollView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moj.nested.R

class DummyRecyclerAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<DummyRecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyRecyclerHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dummy_recycler, parent, false)
        return DummyRecyclerHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DummyRecyclerHolder, position: Int) {
        val item = items[position]
        holder.apply {
            bind(item)
        }
    }

}