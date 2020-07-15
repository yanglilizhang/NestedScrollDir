package com.cn.lenny.androidhighlights.diff;

import com.cn.lenny.androidhighlights.adapter.IElement;
import com.cn.lenny.androidhighlights.bean.ElementRecord;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-12
 */
public interface IDataDiff<T extends IElement> {
    boolean areItemsChanged(T oldItem, T newItem, int position);
    boolean areContentsChanged(ElementRecord oldItem, T newItem);
}
