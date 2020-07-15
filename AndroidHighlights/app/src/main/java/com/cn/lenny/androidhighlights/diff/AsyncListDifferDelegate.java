package com.cn.lenny.androidhighlights.diff;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.lenny.androidhighlights.adapter.IElement;
import com.cn.lenny.androidhighlights.bean.ElementRecord;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * =====================================================
 * All Right Reserved
 * Date:2019/5/15
 * Author:lenny
 * Description:基于既有框架的比较扩展，提供关键位置的处理
 * =====================================================
 */
public class AsyncListDifferDelegate<T extends IElement> {
    private final ListUpdateCallback mUpdateCallback;
    private final AsyncDifferConfig<T> mConfig;
    private  IDataCache<T> dataElementCache;
    private WeakReference<IRender> iRenderWeakReference;

    /**
     *
     * @param adapter  列表适配器
     * @param diffCallback   数据比较回调
     */
    public AsyncListDifferDelegate(RecyclerView.Adapter adapter,
                                   DiffUtil.ItemCallback<T> diffCallback) {
        mUpdateCallback = new AdapterListUpdateCallback(adapter);
        mConfig = new AsyncDifferConfig.Builder<>(diffCallback).build();
    }


    public AsyncListDifferDelegate(ListUpdateCallback listUpdateCallback,
                                   AsyncDifferConfig<T> config, IDataCache<T> dataElementCache) {
        mUpdateCallback = listUpdateCallback;
        mConfig = config;
        this.dataElementCache = dataElementCache;
    }


    private volatile List<T>  mList;


    private volatile List<T> mReadOnlyList = Collections.emptyList();

    private int mMaxScheduledGeneration;


    public List<T> getDataSource() {
        return mReadOnlyList;
    }

    public void setRender(IRender render) {
        iRenderWeakReference = new WeakReference<>(render);
    }

    /**
     *  比较数据差异，分发差异结果，调用局部刷新API,每次请求接口增加一次版本号
     * @param newList  新的数据源
     */
    public void submitList(final List<T> newList) {
        if (newList == mList) {
            tryNotity();
            return;
        }

        final int runGeneration = ++mMaxScheduledGeneration;
        // 如果新集合是空 就把老集合所有都remove
        if (newList == null) {
            int countRemoved = mList.size();
            mList = null;
            updateDataSource(Collections.unmodifiableList(new ArrayList<T>()));
            mUpdateCallback.onRemoved(0, countRemoved);
            tryNotity();
            return;
        }
        // 如果老集合是空 就把新集合所有都insert
        if (mList == null) {
            mList = newList;
            updateDataSource(Collections.unmodifiableList(newList));
            mConfig.getBackgroundThreadExecutor()
                    .execute(
                            new Runnable() {
                                @SuppressLint("RestrictedApi")
                                @Override
                                public void run() {
                                    for (int i = 0; i < newList.size(); i++) {
                                        final T t = newList.get(i);
                                        if(t!=null){
                                            dataElementCache.putRecord(new ElementRecord(IDHelper.getUniqueId(t),t));
                                        }
                                    }
                                    dataElementCache.copySelf();
                                }
                            });
            mUpdateCallback.onInserted(0, newList.size());
            tryNotity();

            return;
        }

        final List<T> oldList = mList;
        mConfig.getBackgroundThreadExecutor().execute(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                    @Override
                    public int getOldListSize() {
                        return oldList.size();
                    }

                    @Override
                    public int getNewListSize() {
                        return newList.size();
                    }

                    @Override
                    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        return mConfig.getDiffCallback().areItemsTheSame(
                                oldList.get(oldItemPosition), newList.get(newItemPosition));
                    }

                    @Override
                    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                        return mConfig.getDiffCallback().areContentsTheSame(
                                oldList.get(oldItemPosition), newList.get(newItemPosition));
                    }
                    // payload可以理解为关键的数据,就是新老item的数据中 到底哪里变化了,局部刷新某个item -- 默认返回null
                    @Override
                    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                        return mConfig.getDiffCallback().getChangePayload(
                                oldList.get(oldItemPosition), newList.get(newItemPosition));
                    }
                });
                mConfig.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mMaxScheduledGeneration == runGeneration) {
                            latchList(newList, result);
                            tryNotity();
                        }
                    }
                });
            }
        });
    }



    /**
     * 尝试将渲染完成时机通知出去
     */
    private void tryNotity() {
        if(iRenderWeakReference!=null&&iRenderWeakReference.get()!=null){
            iRenderWeakReference.get().onRenderFinish();
        }
    }

    /**
     * 分发比较结果
     * @param newList
     * @param diffResult
     */
    private void latchList( List<T> newList,  DiffUtil.DiffResult diffResult) {
        mList = newList;
        updateDataSource(Collections.unmodifiableList(newList));
        diffResult.dispatchUpdatesTo(mUpdateCallback);
    }

    /**
     * 更新数据源
     * @param dataSource
     */
    private void updateDataSource(List<T> dataSource){
        mReadOnlyList = dataSource;
        dataElementCache.copySelf();
    }



    /**
     * 渲染接口
     */
    public interface IRender{
        /**
         * 刷新完成
         */
        void onRenderFinish();
    }
}
