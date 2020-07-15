package com.cn.lenny.androidhighlights.diff;


import com.cn.lenny.androidhighlights.adapter.IElement;
import com.cn.lenny.androidhighlights.bean.ElementRecord;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * =====================================================
 * All Right Reserved
 * Date:2019/6/11
 * Author:lenny
 * Description:记录最新数据源的信息
 * =====================================================
 */
public class ElementCache<DATA extends IElement> implements IDataCache<DATA>{
    private volatile ConcurrentMap<IElement, ElementRecord> elementRecords = new ConcurrentHashMap<>();
    private volatile ConcurrentMap<IElement,ElementRecord> elementRecordsCopy = new ConcurrentHashMap<>();
    @Override
    public void putRecord(ElementRecord elementRecord) {
        elementRecordsCopy.put(elementRecord.getElement(),elementRecord);
    }

    @Override
    public void removeRecord(DATA item) {
        elementRecords.remove(item);
    }

    @Override
    public ElementRecord getRecord(IElement element) {
        return elementRecords.get(element);

    }

    @Override
    public void copySelf() {
        ConcurrentMap<IElement,ElementRecord> temp = elementRecords;
        elementRecords = elementRecordsCopy;
        elementRecordsCopy = temp;
        elementRecordsCopy.clear();
    }

}
