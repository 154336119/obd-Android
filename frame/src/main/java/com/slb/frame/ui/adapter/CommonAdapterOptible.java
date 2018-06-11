package com.slb.frame.ui.adapter;

import java.util.List;

/**
 * 描述：adpter操作接口
 * Created by Lee
 * on 2016/9/14.
 */
public interface CommonAdapterOptible<T> {
    /**
     * 设置 list数据
     * @param list
     */
    void setList(List<T> list);

    /**
     * 添加列表数据
     * @param list
     */
    void addList(List<T> list);

    /**
     * 移除item
     * @param position
     */
    void removeItem(int position);
}
