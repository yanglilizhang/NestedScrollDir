package com.cn.lenny.androidhighlights.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.cn.lenny.androidhighlights.templet.IViewTemplet;

/**
 * @author fanleiliang
 * @version 1.0
 * @date 2019-10-17
 */
public class TempletViewHolder extends StateViewHolder{

    protected Context mContext;
    protected int viewType = 0;
    protected View itemView;
    protected IViewTemplet mTemplet;

    public TempletViewHolder(Context mContext, View itemView) {
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
