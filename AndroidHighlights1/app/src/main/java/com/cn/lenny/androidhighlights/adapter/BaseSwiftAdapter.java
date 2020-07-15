package com.cn.lenny.androidhighlights.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.lenny.androidhighlights.bean.ElementRecord;
import com.cn.lenny.androidhighlights.diff.AsyncListDifferDelegate;
import com.cn.lenny.androidhighlights.diff.ChangeListCallback;
import com.cn.lenny.androidhighlights.diff.DataDiffImpl;
import com.cn.lenny.androidhighlights.diff.DiffCallBack;
import com.cn.lenny.androidhighlights.diff.ElementCache;
import com.cn.lenny.androidhighlights.diff.IDataCache;
import com.cn.lenny.androidhighlights.diff.IDataDiff;
import com.cn.lenny.androidhighlights.executors.AppExecutors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author fanleiliang
 * @version 1.0
 * @date 2019-10-15
 */
public abstract class BaseSwiftAdapter<DATA extends IElement, VH extends StateViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<Object> mDataSource = new ArrayList();
    protected Context mContext;
    protected static final String TAG = "DiffRecyclerViewAdapter";

    /**
     * 数据项比较工具
     */
    private final IDataDiff mDataDiff;

    /** 数据比较工具 */
    private final AsyncListDifferDelegate<DATA> mDiffer;

    private final IDataCache<DATA> dataElementCache;

    public BaseSwiftAdapter(Context mContext) {
        this.mContext = mContext;
        dataElementCache = new ElementCache<>();
        final DiffCallBack diffCallBack = new DiffCallBack(dataElementCache);

        @SuppressLint("RestrictedApi") AsyncDifferConfig config =
                new AsyncDifferConfig.Builder<>(diffCallBack)
                        .setBackgroundThreadExecutor(AppExecutors.backGroudExecutors)
                        .setMainThreadExecutor(AppExecutors.mainExecutors)
                        .build();
        ChangeListCallback changedPositionCallback = new ChangeListCallback(this);
        mDataDiff = new DataDiffImpl<>(changedPositionCallback, dataElementCache);
        mDiffer =
                new AsyncListDifferDelegate(changedPositionCallback, config, dataElementCache);
    }

    /**
     * 视图完成渲染的回调，常用于关闭刷新
     * @param renderListener
     */
    public void setRenderListener(AsyncListDifferDelegate.IRender renderListener) {
        mDiffer.setRender(renderListener);
    }

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        if (null != holder && holder.itemView != null) {
            tryBindData(holder, position, this.getItem(position));
        }
    }

    /**
     * step 1.比较局部刷新派发的变化，若变化或者老数据为空，rebind
     * step 2.无变化的数据比较数据版本是否变化，若发生变化同步状态
     * step 3.数据内容是否发生变化，若变化rebind
     * @param holder
     * @param position
     * @param newData
     */
    private void tryBindData(VH holder, int position, DATA newData) {
        final ElementRecord oldDataRecord = holder.content();
        boolean needBind ;
        if(needBind = (hasPositionDataRefreshChanged(oldDataRecord == null ? null : (DATA) oldDataRecord.getElement(), newData, position) || oldDataRecord == null) ){
            Log.d(getClass().getName(),"adapter onBindData 刷新或者新建"+ holder.getItemViewType());
        }else if(needBind =  hasDataContentChanged(oldDataRecord,newData)){
            Log.d(getClass().getName(),"adapter onBindData 滑动内容改变"+ holder.getItemViewType());
        }
        if(needBind){
            refreshAndBind(holder, position, newData);
        }else {
            Log.d(getClass().getName(),"adapter onBindData 复用不刷新"+ holder.getItemViewType());
        }
    }

    /**
     * 刷新视图状态
     * @param holder
     * @param position
     * @param newData
     */
    private void refreshAndBind(VH holder, int position, DATA newData) {
        holder.setElementRecord(dataElementCache.getRecord(newData));
        onBindData(holder, newData,position);
    }



    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 视图绑定数据
     * @param holder
     * @param newData
     * @param position
     */
    protected abstract void onBindData(VH holder, IElement newData,int position);




    /**
     * 刷新变化
     * @param oldItem
     * @param newItem
     * @param position
     * @return
     */
    private boolean hasPositionDataRefreshChanged(DATA oldItem, DATA newItem, int position){
        return  mDataDiff.areItemsChanged(oldItem, newItem, position);
    }

    /**
     * 内容变化
     * @param oldItem
     * @param newItem
     * @return
     */
    private boolean hasDataContentChanged(ElementRecord oldItem, DATA newItem){
        return  mDataDiff.areContentsChanged(oldItem, newItem);
    }
    /**
     * 刷新列表
     *
     * @param pagedList 新的列表数据
     */
    public final void refreshDataSource(List<DATA> pagedList) {
        mDiffer.submitList(pagedList);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDiffer.getDataSource().size();
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    protected final DATA getItem(int position) {
        return mDiffer.getDataSource().get(position);
    }

    public Activity getActivity() {
        return this.mContext instanceof Activity ? (Activity)this.mContext : null;
    }

    public Context getContext() {
        return this.mContext;
    }
}
