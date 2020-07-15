package com.cn.lenny.androidhighlights.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cn.lenny.androidhighlights.R;
import com.cn.lenny.androidhighlights.templet.HeaderFooterViewTemplet;
import com.cn.lenny.androidhighlights.templet.IViewTemplet;

import java.util.Map;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-12
 */
public class RecyclerViewMutilTypeAdapter extends BaseMutilTypeRecyclerViewAdapter{
    protected static final int BASE_ITEM_TYPE_HEADER = 10000;
    protected static final int BASE_ITEM_TYPE_FOOTER = 20000;
    protected SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat();
    protected SparseArrayCompat<View> mFootViews = new SparseArrayCompat();

    public RecyclerViewMutilTypeAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemCount() {
        return this.getHeaderCount() + this.getFooterCount() + this.mDataSource.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (this.isHeaderViewPosition(position)) {
            return this.mHeaderViews.keyAt(position);
        } else {
            return this.isFooterViewPosition(position) ? this.mFootViews.keyAt(this.getFooterCount() + position - this.getItemCount()) : super.getItemViewType(position - this.getHeaderCount());
        }
    }

    @Override @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.mHeaderViews.get(viewType) != null) {
            return this.getFixHeaderFooterViewHolder(parent, viewType, this.mHeaderViews);
        } else {
            return this.mFootViews.get(viewType) != null ? this.getFixHeaderFooterViewHolder(parent, viewType, this.mFootViews) : super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!this.isHeaderViewPosition(position)) {
            if (!this.isFooterViewPosition(position)) {
                super.onBindViewHolder(holder, position - this.getHeaderCount());
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager)manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return !isHeaderViewPosition(position) && !isFooterViewPosition(position) ? 1 : gridManager.getSpanCount();
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (null != lp && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams)lp;
            if (null != holder && holder instanceof RecyclerViewHolderWrapper) {
                RecyclerViewHolderWrapper holderWrapper = (RecyclerViewHolderWrapper)holder;
                Object mTemplet = holderWrapper.getTemplet();
                if (null != mTemplet && mTemplet instanceof HeaderFooterViewTemplet) {
                    p.setFullSpan(true);
                }

            }
        }
    }

    public void addHeaderView(View view) {
        this.mHeaderViews.put(this.mHeaderViews.size() + 10000, view);
    }

    public void addFooterView(View view) {
        this.mFootViews.put(this.mFootViews.size() + 20000, view);
    }

    public void removeHeaderView(View view) {
        if (null != view && this.mHeaderViews.size() > 0) {
            int mTargetIndex = -1;

            for(int i = 0; i < this.mHeaderViews.size(); ++i) {
                if (this.mHeaderViews.get(i + 10000) == view) {
                    mTargetIndex = i;
                    break;
                }
            }

            if (-1 != mTargetIndex) {
                this.mHeaderViews.remove(mTargetIndex + 10000);
                this.notifyDataSetChanged();
            }

        }
    }

    public void removeFooterView(View view) {
        if (null != view && this.mFootViews.size() > 0) {
            int mTargetIndex = -1;

            for(int i = 0; i < this.mFootViews.size(); ++i) {
                if (this.mFootViews.get(i + 20000) == view) {
                    mTargetIndex = i;
                    break;
                }
            }

            if (-1 != mTargetIndex) {
                this.mFootViews.remove(mTargetIndex + 20000);
                this.notifyDataSetChanged();
            }

        }
    }

    public void clearFooterView() {
        if (this.mFootViews.size() > 0) {
            this.mFootViews.clear();
            this.notifyDataSetChanged();
        }

    }

    public void clearHeaderView() {
        if (this.mHeaderViews.size() > 0) {
            this.mFootViews.clear();
            this.notifyDataSetChanged();
        }

    }

    /** @deprecated */
    @Deprecated
    public int getDataItemCount() {
        return this.mDataSource.size();
    }

    public int getCount() {
        return this.mDataSource.size();
    }

    public int getHeaderCount() {
        return this.mHeaderViews.size();
    }

    public int getFooterCount() {
        return this.mFootViews.size();
    }

    public void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams)lp;
            p.setFullSpan(true);
        }

    }

    public void resetHeaderFooterCloumn(RecyclerView recyclerView) {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager)manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    return !isHeaderViewPosition(position) && !isFooterViewPosition(position) ? 1 : gridManager.getSpanCount();
                }
            });
        }

    }

    public void registeViewTemplet(int viewType, Class<? extends IViewTemplet> mTemplet) {
        this.mViewTemplet.put(viewType, mTemplet);
    }

    @Override
    protected void registeViewTemplet(Map<Integer, Class<? extends IViewTemplet>> mViewTemplet) {
        this.mViewTemplet = mViewTemplet;
    }

    @Override
    protected int adjustItemViewType(Object model, int position) {
        return 0;
    }

    private boolean isHeaderViewPosition(int position) {
        return position < this.getHeaderCount();
    }

    private boolean isFooterViewPosition(int position) {
        return position >= this.getItemCount() - this.getFooterCount();
    }

    private RecyclerView.ViewHolder getFixHeaderFooterViewHolder(ViewGroup parent, int viewType, SparseArrayCompat<View> mViewCache) {
        if (mViewCache.get(viewType) != null) {
            IViewTemplet mTemplet = new HeaderFooterViewTemplet(this.mContext, (View)mViewCache.get(viewType));
            mTemplet.setUIBridge(this.mUIBridge);
            View convertView = mTemplet.getItemLayoutView();
            convertView.setTag(R.id.dynamic_view_templet, mTemplet);
            convertView.setTag(R.id.dynamic_elelemt_id, mTemplet.getClass().getName());
            RecyclerViewHolderWrapper mHolderWrapper = new RecyclerViewHolderWrapper(this.mContext, convertView);
            mHolderWrapper.setViewType(viewType);
            mHolderWrapper.setTemplet(mTemplet);
            return mHolderWrapper;
        } else {
            return null;
        }
    }
}
