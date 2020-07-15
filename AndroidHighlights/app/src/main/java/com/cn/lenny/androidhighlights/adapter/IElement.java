package com.cn.lenny.androidhighlights.adapter;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-11
 *
 * Description:元素比较
 *  注意：此处必须真切的返回数据体的内容，不能以对象地址作为内容。
 *  若以对象作为比较内容，可能出现数据体内容一样.
 *  发生数据绑定行为，造成卡顿。根据目前平均的测算结果，数据比较耗时
 *  120ms
 */
public interface IElement {
    /**
     * 数据内容
     * @return 返回该数据体区别于其他数据体的内容
     */
    String diffContent();
}
