package com.cn.lenny.androidhighlights.templet;

import android.view.View;
import android.widget.AbsListView;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-12
 */
public interface ICallBackForListview {
    public void onMovedToScrapHeap(View view);

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);

    public void onScrollStateChanged(AbsListView mPageList, int scrollState);
}
