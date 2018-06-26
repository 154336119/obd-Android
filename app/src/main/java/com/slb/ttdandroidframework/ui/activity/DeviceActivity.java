package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.MainActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.DeviceContract;
import com.slb.ttdandroidframework.ui.contract.LoginContract;
import com.slb.ttdandroidframework.ui.presenter.DevicePresenter;
import com.slb.ttdandroidframework.ui.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceActivity extends BaseMvpActivity<DeviceContract.IView, DeviceContract.IPresenter>
        implements DeviceContract.IView{

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public DeviceContract.IPresenter initPresenter() {
        return new DevicePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_device;
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
