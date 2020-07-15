package com.cn.lenny.androidhighlights.templet;

import android.content.Context;
import android.view.View;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-12
 */
public class HeaderFooterViewTemplet extends BaseViewTemplet {
    public HeaderFooterViewTemplet(Context mContext, View mLayout) {
        super(mContext);
        this.mLayoutView = mLayout;
    }

    public View bindView() {
        return this.mLayoutView;
    }

    public int bindLayout() {
        return -1;
    }

    public void initView() {
    }

    public void fillData(Object model, int position) {
    }
}
