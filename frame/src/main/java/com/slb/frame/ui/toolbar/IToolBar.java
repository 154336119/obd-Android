package com.slb.frame.ui.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * 作者:刁剑
 * 时间:2016/9/26
 * 注释:
 */
public interface IToolBar {
    /**
     * 配置toolbar属性
     */
    void toolbarConfigure(AppCompatActivity activity, Toolbar toolbar, String title);
}
