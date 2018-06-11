package com.slb.frame.utils.proxy;

import java.lang.reflect.Method;

/**
 * 刁剑
 * Created on 2016/11/4.
 * 注释:动态代理通用接口
 */

public interface IDynamicProxy {
    //实例方法执行前执行的方法
    void after(Object proxy, Method method, Object[] args);
    //实例方法执行后执行的方法
    void before(Object proxy, Method method, Object[] args);
    //替换实例方法
    boolean replace(Object proxy, Method method, Object[] args);
}
