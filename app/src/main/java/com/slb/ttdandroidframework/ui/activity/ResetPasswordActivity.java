package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.DeviceContract;
import com.slb.ttdandroidframework.ui.contract.ResetPasswordContract;
import com.slb.ttdandroidframework.ui.presenter.DevicePresenter;
import com.slb.ttdandroidframework.ui.presenter.ResetPasswordPresenter;

import butterknife.ButterKnife;

public class ResetPasswordActivity extends BaseMvpActivity<ResetPasswordContract.IView, ResetPasswordContract.IPresenter>
        implements ResetPasswordContract.IView{


    @Override
    public ResetPasswordContract.IPresenter initPresenter() {
        return new ResetPasswordPresenter();
    }

    @Override
    protected String setToolbarTitle() {
        return "重置密码";
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
