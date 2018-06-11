package com.slb.frame.utils.proxy;

/**
 * 刁剑
 * Created on 2016/11/4.
 * 注释:摇一摇的动态代理接口
 */

public interface IShakeProxy {
    void initView();
    void setSensorManager();
    void closeSensorManager();
}
