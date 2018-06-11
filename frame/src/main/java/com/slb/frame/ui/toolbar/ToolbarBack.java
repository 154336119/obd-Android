package com.slb.frame.ui.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.slb.frame.R;

/**
 * 作者:刁剑
 * 时间:2016/9/26
 * 注释:
 */
public class ToolbarBack implements IToolBar {
    @Override
    public void toolbarConfigure(final AppCompatActivity activity,Toolbar toolbar,String title) {
//        toolbar.setNavigationIcon(R.mipmap.back);
        activity.setSupportActionBar(toolbar);
        LayoutInflater.from(activity).inflate(R.layout.include_toolbar_back, toolbar);
        TextView mToolBarTitleLabel= (TextView) toolbar.findViewById(R.id.mToolbarTitleLabel);
        mToolBarTitleLabel.setText(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }
}
