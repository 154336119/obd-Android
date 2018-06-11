package com.slb.frame.ui.toolbar;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.slb.frame.R;
/**
 * 作者:刁剑
 * 时间:2016/9/26
 * 注释:首页toolbar的设置
 */
public class ToolbarHome implements IToolBar {
    @Override
    public void toolbarConfigure(final AppCompatActivity activity, Toolbar toolbar, String title) {
        //设置标题
        toolbar.setTitle("");
        LayoutInflater.from(activity).inflate(R.layout.include_toolbar_home, toolbar);
        TextView mToolBarTitleLabel= (TextView) toolbar.findViewById(R.id.mToolbarTitleLabel);
        mToolBarTitleLabel.setText(title);
        //添加左边侧滑栏
        toolbar.setNavigationIcon(R.mipmap.ic_ab_drawer);
        DrawerLayout drawerLayout= (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        new ActionBarDrawerToggle(activity,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        //设置与系统activity交互
        activity.setSupportActionBar(toolbar);
    }
}
