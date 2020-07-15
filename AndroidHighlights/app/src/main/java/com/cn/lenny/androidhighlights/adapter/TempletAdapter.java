package com.cn.lenny.androidhighlights.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cn.lenny.androidhighlights.templet.AbsViewTemplet;
import com.cn.lenny.androidhighlights.templet.EmptyViewTemplet;
import com.cn.lenny.androidhighlights.templet.ITempletBridge;
import com.cn.lenny.androidhighlights.templet.IViewTemplet;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author fanleiliang
 * @version 1.0
 * @date 2019-10-17
 */
public abstract class TempletAdapter <DATA extends IElement>  extends BaseSwiftAdapter<DATA,TempletViewHolder>{

    protected Map<Integer, Class<? extends IViewTemplet>> mViewTemplet = new TreeMap();

    protected ITempletBridge mUIBridge;
    public TempletAdapter(Context mContext) {
        super(mContext);
        this.registeViewTemplet(this.mViewTemplet);
    }

    @Override
    public int getItemViewType(int position) {
        Object model = this.getItem(position);
        return this.adjustItemViewType(model, position);
    }

    @Override
    public TempletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Class<? extends IViewTemplet> templetClass = mViewTemplet.get(viewType);
        if (null == templetClass) {
            templetClass = EmptyViewTemplet.class;
        }

        IViewTemplet mTemplet = AbsViewTemplet.createViewTemplet(templetClass, new Object[]{mContext});

        View convertView = null;

        try {
            mTemplet.setUIBridge(mUIBridge);
            mTemplet.inflate(viewType, 0 , parent);
            mTemplet.initView();
            convertView = mTemplet.getItemLayoutView();
        }catch (Throwable th) {
            th.printStackTrace();

        }
        TempletViewHolder mHolder = new TempletViewHolder(this.mContext, convertView);
        mHolder.setViewType(viewType);
        mHolder.setTemplet(mTemplet);
        return mHolder;
    }

    @Override
    protected void onBindData(TempletViewHolder holder, IElement newData, int position) {
        if (newData!=null) {
            try {
                IViewTemplet templet = holder.getTemplet();
                if (null == templet) {
                    return;
                }
                templet.fillData(newData,position);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void registeTempletBridge(ITempletBridge mBridge) {
        this.mUIBridge = mBridge;
    }

    public <B extends ITempletBridge> B getTempletBridge(Class<B> mBridge) {
        return (B) this.mUIBridge;
    }

    protected abstract void registeViewTemplet(Map<Integer, Class<? extends IViewTemplet>> var1);

    protected abstract int adjustItemViewType(Object model, int position);
}
