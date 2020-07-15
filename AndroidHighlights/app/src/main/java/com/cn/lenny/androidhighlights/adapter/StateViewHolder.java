package com.cn.lenny.androidhighlights.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.lenny.androidhighlights.bean.ElementRecord;

/**
 * @author fanleiliang
 * @version 1.0
 * @date 2019-10-11
 */
public class StateViewHolder extends RecyclerView.ViewHolder implements IHolderState{
    /**
     * 对应的数据信息
     */
    private ElementRecord elementRecord;
    public StateViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setElementRecord(ElementRecord elementRecord) {
        this.elementRecord = elementRecord;
    }

    @Override
    public ElementRecord content() {
        return elementRecord;
    }
}
