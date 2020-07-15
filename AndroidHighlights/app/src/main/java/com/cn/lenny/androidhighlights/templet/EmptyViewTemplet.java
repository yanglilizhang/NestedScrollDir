package com.cn.lenny.androidhighlights.templet;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.cn.lenny.androidhighlights.R;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-12
 */
public class EmptyViewTemplet extends BaseViewTemplet {
    TextView mTipsText;

    public EmptyViewTemplet(Context mContext) {
        super(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.templet_common_empty;
    }

    @Override
    public void initView() {
        this.mTipsText = (TextView)this.findViewById(R.id.tv_tips);
    }

    @Override
    public void fillData(Object model, int position) {
        boolean isDisplay = true;
        this.mTipsText.setText("第" + position + "个位置的视图模板(viewType=" + this.viewType + ")未找到，请检查数据源");
        this.mLayoutView.setVisibility(isDisplay ? View.VISIBLE : View.GONE);

        try {
            int height = isDisplay ? this.getPxValueOfDp(50.0F) : 0;
            Object mLp;
            if (null != this.parent && this.parent instanceof AbsListView) {
                mLp = new AbsListView.LayoutParams(-1, height);
            } else {
                mLp = new android.view.ViewGroup.LayoutParams(-1, height);
            }

            this.mLayoutView.setLayoutParams((android.view.ViewGroup.LayoutParams)mLp);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
}