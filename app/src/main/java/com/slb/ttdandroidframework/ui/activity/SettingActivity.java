package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by juan on 2018/9/5.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.RlName)
    RelativeLayout RlName;
    @BindView(R.id.RlPw)
    RelativeLayout RlPw;
    @Override
    protected String setToolbarTitle() {
        return "设置";
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.RlName, R.id.RlPw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RlName:
                ActivityUtil.next(this,ModNickNameActivity.class);
                break;
            case R.id.RlPw:
                ActivityUtil.next(this,ModPasswordActivity.class);
                break;
        }
    }
}
