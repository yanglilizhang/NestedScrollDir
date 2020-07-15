package com.cn.lenny.androidhighlights.bean;

import com.cn.lenny.androidhighlights.adapter.IElement;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-11
 */
public class ElementRecord {
    /**
     * 唯一标识符
     */
    private String uniqueId;
    /**
     * 数据
     */
    private IElement element;


    public ElementRecord(String uniqueId, IElement element) {
        this.uniqueId = uniqueId;
        this.element = element;
    }

    public String getUniqueId() {
        return uniqueId;
    }


    public IElement getElement() {
        return element;
    }

    public void setElement(IElement element) {
        this.element = element;
    }
}
