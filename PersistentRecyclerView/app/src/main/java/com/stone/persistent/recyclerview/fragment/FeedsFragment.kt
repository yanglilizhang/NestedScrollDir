package com.stone.persistent.recyclerview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stone.persistent.recyclerview.R
import com.stone.persistent.recyclerview.adapter.FeedsListAdapter
import com.stone.persistent.recyclerview.extensions.dp2px
import com.stone.persistent.recyclerview.library.ChildRecyclerView
import com.stone.persistent.recyclerview.widget.GridItemDecoration
import com.stone.persistent.recyclerview.widget.PersistentStaggeredGridLayoutManager

class FeedsFragment : Fragment() {

    private var childRecyclerView: ChildRecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_feeds_list, null)
        val layoutManager = PersistentStaggeredGridLayoutManager(2)

        childRecyclerView = rootView as ChildRecyclerView
        childRecyclerView!!.layoutManager = layoutManager
        childRecyclerView!!.addItemDecoration(GridItemDecoration(activity!!.dp2px(8f)))
        childRecyclerView!!.adapter = FeedsListAdapter(activity!!)
        return rootView
    }
}