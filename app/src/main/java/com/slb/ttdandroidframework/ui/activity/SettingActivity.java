package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.util.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.slb.ttdandroidframework.util.config.BizcContant.SP_USER;

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
        return getString(R.string.Settings);
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

    @OnClick({R.id.RlName, R.id.RlPw,R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RlName:
                ActivityUtil.next(this, ModNickNameActivity.class);
                break;
            case R.id.RlPw:
                ActivityUtil.next(this, ModPasswordActivity.class);
                break;
            case  R.id.btnLogout:
                SharedPreferencesUtils.setParam(this, SP_USER,"");
                ActivityUtil.nextActivityWithClearTop(this, LoginActivity.class);
                break;
        }
    }
}
