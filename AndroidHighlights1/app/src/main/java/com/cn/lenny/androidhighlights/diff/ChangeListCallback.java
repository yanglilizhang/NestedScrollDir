package com.cn.lenny.androidhighlights.diff;

import android.util.SparseArray;

import androidx.recyclerview.widget.RecyclerView;

/**
 * =====================================================
 * All Right Reserved
 * Date:2019/5/16
 * Author:lenny
 * Description:记录dif比较结果，便于绑定数据时的判断处理
 * =====================================================
 */
public class ChangeListCallback extends ListAdapterListUpdateCallback {
    public static final int VALUE_IF_KEY_NOT_FOUND = -1;
    private SparseArray<Integer> changedPositions = new SparseArray<>();

    public ChangeListCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    /**
     * 删除视图的情视图不会有刷新行为
     * @param position
     * @param count
     */
    @Override
    public void onRemoved(int position, int count) {
        super.onRemoved(position, count);
    }

    /**不会影响刷新行为
     * 移动视图
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public void onMoved(int fromPosition, int toPosition) {
        super.onMoved(fromPosition, toPosition);
    }

    @Override
    public void onChanged(int position, int count, Object payload) {
        recordChanged(position,count);
        super.onChanged(position, count, payload);
    }

    @Override
    public void onInserted(int position, int count) {
        recordChanged(position, count);
        super.onInserted(position, count);
    }

    private void recordChanged(int position, int count) {
        int  tempPosition = position;
        for (int i = 0; i <count; i++) {
            changedPositions.put(tempPosition,tempPosition);
            tempPosition++;
        }
    }

    void removeChangedPosition(int position){
        changedPositions.remove(position);
    }

     boolean hasPositionChanged(int position){
       return VALUE_IF_KEY_NOT_FOUND != changedPositions.get(position, VALUE_IF_KEY_NOT_FOUND);
    }

}
