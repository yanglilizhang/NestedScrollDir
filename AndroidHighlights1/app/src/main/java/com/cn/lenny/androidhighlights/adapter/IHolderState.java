package com.cn.lenny.androidhighlights.adapter;

import com.cn.lenny.androidhighlights.bean.ElementRecord;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-11
 *
 * Description:从内容和数据版本以及派发刷新三个维度比较数据的变化
 */
public interface IHolderState {
    /**
     * 获取代表该视图的内容
     * @return
     */
    ElementRecord content();
}
