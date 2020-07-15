package com.cn.lenny.androidhighlights.diff;

import com.cn.lenny.androidhighlights.adapter.IElement;
import com.cn.lenny.androidhighlights.bean.ElementRecord;

/**
 * @author fanleiliang
 * @version 1.0
 * @date 2019-10-12
 */
public class DataDiffImpl<T extends IElement> implements IDataDiff<T> {
    private final IDataCache dataCache;
    private  ChangeListCallback changedPositionCallback;


    public DataDiffImpl(ChangeListCallback changedPositionCallback,IDataCache dataCache) {
        this.changedPositionCallback = changedPositionCallback;
        this.dataCache = dataCache;
    }

    @Override
    public boolean areItemsChanged(T oldItem, T newItem, int position) {
        boolean changed = changedPositionCallback.hasPositionChanged(position);
        if(changed){
            changedPositionCallback.removeChangedPosition(position);
        }
        return changed;
    }

    @Override
    public boolean areContentsChanged(ElementRecord oldElementRecord, T newItem) {
        return oldElementRecord !=null  && oldElementRecord.getElement() != newItem && newItem!=null && !sameContent(oldElementRecord,newItem);
    }


    private boolean sameContent(ElementRecord oldElementRecord, T newItem){
        final ElementRecord newElementRecord = dataCache.getRecord(newItem);
        if(newElementRecord == null){
            return false;
        }
        if(IDHelper.forceRefresh(newElementRecord) || IDHelper.forceRefresh(oldElementRecord)){
            return false;
        }
        return newElementRecord.getUniqueId().equals(oldElementRecord.getUniqueId());
    }
}
