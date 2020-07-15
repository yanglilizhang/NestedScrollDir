package com.cn.lenny.androidhighlights.diff;


import com.cn.lenny.androidhighlights.adapter.IElement;
import com.cn.lenny.androidhighlights.bean.ElementRecord;

public interface IDataCache<Data extends IElement> {
    void putRecord(ElementRecord elementRecord);
    void removeRecord(Data item);
    ElementRecord getRecord(IElement element);
    void copySelf();
}
