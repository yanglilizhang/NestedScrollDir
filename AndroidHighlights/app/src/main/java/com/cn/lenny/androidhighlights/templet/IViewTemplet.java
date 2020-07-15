package com.cn.lenny.androidhighlights.templet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-11
 */
public interface IViewTemplet {

    View bindView();

    int bindLayout();

    View inflate(int viewType, int position, ViewGroup parent);

    View inflate(int viewType, int position, Object rowData, ViewGroup parent);

    void initView();

    void fillData(Object data, int position);

    View getItemLayoutView();

    void setUIBridge(ITempletBridge mUIBridge);

    void holdCurrentParams(int viewType, int position, Object rowData);

}
