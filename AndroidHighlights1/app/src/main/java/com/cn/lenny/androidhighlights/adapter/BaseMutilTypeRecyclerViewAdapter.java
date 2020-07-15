package com.cn.lenny.androidhighlights.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.lenny.androidhighlights.R;
import com.cn.lenny.androidhighlights.templet.AbsViewTemplet;
import com.cn.lenny.androidhighlights.templet.EmptyViewTemplet;
import com.cn.lenny.androidhighlights.templet.ITempletBridge;
import com.cn.lenny.androidhighlights.templet.IViewTemplet;
import com.cn.lenny.androidhighlights.utils.ExceptionHandler;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author fanleiliang
 * @version 1.0
 * @date 2019-10-11
 */
public abstract class BaseMutilTypeRecyclerViewAdapter extends BaseRecyclerViewAdapter{
    protected static boolean DEBUG = false;
    protected Map<Integer, Class<? extends IViewTemplet>> mViewTemplet = new TreeMap();
    protected Map<Integer, Class<? extends Serializable>> mViewTypeModel = new TreeMap();
    protected Map<Integer, Integer> mViewLayoutCache = new TreeMap();
    /** @deprecated */
    @Deprecated
    protected Fragment mFragment;
    protected ITempletBridge mUIBridge;
    protected String ctp = "";

    public BaseMutilTypeRecyclerViewAdapter(Context mContext) {
        super(mContext);
        this.ctp = this.getClass().getName();
        this.registeViewTemplet(this.mViewTemplet);
    }

    @Override
    public int getItemViewType(int position) {
        Object model = this.getItem(position);
        return this.adjustItemViewType(model, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Class<? extends IViewTemplet> templetClass = (Class)this.mViewTemplet.get(viewType);
        if (null == templetClass) {
            this.debugLog("onCreateViewHolder-->can not find IViewTemplet for viewType=" + viewType + " in mViewTemplet");
            templetClass = EmptyViewTemplet.class;
        }

        IViewTemplet mTemplet = AbsViewTemplet.createViewTemplet(templetClass, new Object[]{this.mContext});
        View convertView = null;

        try {
            mTemplet.setUIBridge(this.mUIBridge);
            mTemplet.inflate(viewType, 0, parent);
            mTemplet.initView();
            this.debugLog("onCreateViewHolder.实例化viewType=" + viewType + "视图模板-->" + mTemplet.getClass().getName());
            convertView = mTemplet.getItemLayoutView();
            convertView.setTag(R.id.dynamic_view_templet, mTemplet);
            convertView.setTag(R.id.dynamic_elelemt_id, mTemplet.getClass().getName());
        } catch (Throwable var8) {
            ExceptionHandler.handleException(var8);
            Context mAppContext = null != this.mContext ? this.mContext.getApplicationContext() : this.mContext;
        }

        RecyclerViewHolderWrapper mHolderWrapper = new RecyclerViewHolderWrapper(this.mContext, convertView);
        mHolderWrapper.setViewType(viewType);
        mHolderWrapper.setTemplet(mTemplet);
        return mHolderWrapper;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (null != holder && holder instanceof RecyclerViewHolderWrapper) {
            RecyclerViewHolderWrapper mHolderWrapper = (RecyclerViewHolderWrapper)holder;
            int viewType = 0;
            Object rowData = null;

            try {
                rowData = this.getItem(position);
                IViewTemplet mTemplet = mHolderWrapper.getTemplet();
                if (null == mTemplet) {
                    return;
                }

                viewType = mHolderWrapper.getViewType();
                this.debugLog("onBindViewHolder.填充第" + position + "个位置数据,视图模板-->" + mTemplet.getClass().getName());
                mTemplet.holdCurrentParams(mHolderWrapper.getViewType(), position, rowData);
                mTemplet.fillData(rowData, position);
                View mItemView = mHolderWrapper.getItemView();
                if (null == mItemView) {
                    return;
                }

                Object mDataTag = mItemView.getTag(R.id.dynamic_data_source);
                if (null == mDataTag) {
                    mItemView.setTag(R.id.dynamic_data_source, rowData);
                }
            } catch (Throwable var9) {
                ExceptionHandler.handleException(var9);
                if (null != this.mContext) {
                    this.mContext.getApplicationContext();
                } else {
                    Context var10000 = this.mContext;
                }

            }

        } else {
            this.debugLog("onBindViewHolder-->holder is null or holder is not instanceof IViewTemplet");
        }
    }

    public void holdFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public void registeTempletBridge(ITempletBridge mBridge) {
        this.mUIBridge = mBridge;
    }

    public <B extends ITempletBridge> B getTempletBridge(Class<B> mBridge) {
        return (B) this.mUIBridge;
    }

    public Object getTempletBridge() {
        return this.mUIBridge;
    }

    protected void debugLog(String msg) {
        if (DEBUG) {
            Log.d("RecyclerViewAdapter", msg);
        }
    }

    public static void isDebug(boolean isDebug) {
        DEBUG = isDebug;
    }

    protected abstract void registeViewTemplet(Map<Integer, Class<? extends IViewTemplet>> var1);

    protected abstract int adjustItemViewType(Object model, int position);
}
