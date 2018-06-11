package com.slb.frame.http2.retrofit;

import java.util.List;

/**
 * 描述：Entity - 服务器返回data里面的内容
 * Created by Lee
 * on 2016/10/13.
 */
public class HttpDataResutl<T,A> {
    private int isArray;
    private T bean;
    private List<A> list;
    private int total;
    private int totalPage;
    private int currentPage;

    public int getIsArray() {
        return isArray;
    }

    public void setIsArray(int isArray) {
        this.isArray = isArray;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public List<A> getList() {
        return list;
    }

    public void setList(List<A> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
