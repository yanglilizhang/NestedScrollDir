package com.cn.lenny.androidhighlights.diff;




import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

/**
 * =====================================================
 * All Right Reserved
 * Date:2019/5/16
 * Author:lenny
 * Description:局部刷新回调分发
 * =====================================================
 */
public  class ListAdapterListUpdateCallback implements ListUpdateCallback {
    private final RecyclerView.Adapter mAdapter;


    public ListAdapterListUpdateCallback(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onInserted(int position, int count) {
        mAdapter.notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count) {
        mAdapter.notifyItemRangeRemoved(position, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onChanged(int position, int count, Object payload) {
        mAdapter.notifyItemRangeChanged(position, count, payload);
    }
}
