package com.cn.lenny.androidhighlights.diff;


import androidx.recyclerview.widget.DiffUtil;

import com.cn.lenny.androidhighlights.adapter.IElement;
import com.cn.lenny.androidhighlights.bean.ElementRecord;

/**
 * =====================================================
 * All Right Reserved
 * Date:2019/5/13
 * Author:lenny
 * Description: 比较回调
 * =====================================================
 */
public class DiffCallBack<T extends IElement> extends DiffUtil.ItemCallback<T> {

    private final IDataCache<T> dataElementCache;

    public DiffCallBack(IDataCache<T> dataElementCache) {
        this.dataElementCache = dataElementCache;
    }



    @Override
    public boolean areItemsTheSame(IElement oldItem, IElement newItem) {
        return areContentsTheSame(oldItem, newItem);
    }

    /**
     * 总体思想是先比较对象地址，在比较内容，提高比较效率
     *
     * @param oldItem
     * @param newItem
     * @return
     */
    @Override
    public boolean areContentsTheSame(IElement oldItem, IElement newItem) {
        if (newItem == null) {
            return true;
        }
        if (oldItem == newItem) {
            return true;
        }
        recordNewElement(newItem);
        final String newContent = newItem.diffContent();
        if(newContent == null || "".equals(newContent)){
            return false;
        }

        return newContent.equals(oldItem.diffContent());
    }

    /**
     * 记录新的数据信息
     * @param newItem
     * @return
     */
    private void recordNewElement(IElement newItem) {
        dataElementCache.putRecord(new ElementRecord(IDHelper.getUniqueId(newItem), newItem));
    }

}
