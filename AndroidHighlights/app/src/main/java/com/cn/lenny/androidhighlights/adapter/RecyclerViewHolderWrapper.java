package com.cn.lenny.androidhighlights.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.cn.lenny.androidhighlights.templet.IViewTemplet;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-12
 */
public class RecyclerViewHolderWrapper extends RecyclerView.ViewHolder {
    protected Context mContext;
    protected int viewType = 0;
    protected View itemView;
    protected IViewTemplet mTemplet;

    public RecyclerViewHolderWrapper(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        this.itemView = itemView;
    }

    public int getViewType() {
        return this.viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public View getItemView() {
        return this.itemView;
    }

    public IViewTemplet getTemplet() {
        return this.mTemplet;
    }

    public void setTemplet(IViewTemplet mTemplet) {
        this.mTemplet = mTemplet;
    }
}
