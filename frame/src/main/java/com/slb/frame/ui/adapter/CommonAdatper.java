package com.slb.frame.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 描述：
 * Created by Lee
 * on 2016/9/14.
 */
public abstract class CommonAdatper<T> extends BaseAdapter implements CommonAdapterOptible<T>{
    /**
     * LayoutInflater对象（父类可以直接用）
     */
    protected final LayoutInflater mLayoutInflater;
    /**
     * Context对象引用（父类可以直接用）
     */
    protected final Context mContext;
    /**
     * Adapter的列表对象，注意是用final修饰的，被初始化之后，不能重新赋值，便于保证List<T>的唯一性
     */
    protected  List<T> mList;
    public CommonAdatper(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public void setList(List<T> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void addList(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }
}
