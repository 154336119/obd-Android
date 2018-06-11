package com.slb.frame.ui.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * 作者:刁剑
 * 时间:2016/9/26
 * 注释:
 */
public class ToolbarContext {
    private IToolBar iToolBar;
    public void setToolBar(IToolBar iToolBar){
        this.iToolBar=iToolBar;
    }
    public void configure(AppCompatActivity activity,Toolbar toolbar,String title){
        iToolBar.toolbarConfigure(activity,toolbar,title);
    }
}
