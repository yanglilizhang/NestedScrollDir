package com.cn.lenny.androidhighlights.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-11
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<Object> mDataSource = new ArrayList();
    protected Context mContext;
    protected static final String TAG = "RecyclerViewAdapter";

    public BaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return this.mDataSource.size();
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    public List<Object> gainDataSource() {
        return this.mDataSource;
    }

    public Object getItem(int position) {
        return position >= 0 && position < this.mDataSource.size() ? this.mDataSource.get(position) : null;
    }

    public boolean addItem(Object object) {
        return this.mDataSource.add(object);
    }

    public void addItem(int location, Object object) {
        this.mDataSource.add(location, object);
    }

    public boolean addItem(Collection<? extends Object> collection) {
        return this.mDataSource.addAll(collection);
    }

    public boolean addItem(int location, Collection<? extends Object> collection) {
        return this.mDataSource.addAll(location, collection);
    }

    public boolean removeItem(Object object) {
        return this.mDataSource.remove(object);
    }

    public Object removeItem(int location) {
        return this.mDataSource.remove(location);
    }

    public boolean removeAll(Collection<? extends Object> collection) {
        return this.mDataSource.removeAll(collection);
    }

    public void clear() {
        this.mDataSource.clear();
    }

    public void newAnList() {
        this.mDataSource = new ArrayList();
    }

    public Activity getActivity() {
        return this.mContext instanceof Activity ? (Activity)this.mContext : null;
    }

    public Context getContext() {
        return this.mContext;
    }
}
