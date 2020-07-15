package com.cn.lenny.androidhighlights.templet;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-12
 */
public abstract class BaseViewTemplet extends AbsViewTemplet{
    protected Handler mUIHandler = new Handler();
    public BaseViewTemplet(Context mContext) {
        super(mContext);
    }
    public void onMovedToScrapHeap(View view) {
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void onScrollStateChanged(AbsListView mPageList, int scrollState) {
    }
}
