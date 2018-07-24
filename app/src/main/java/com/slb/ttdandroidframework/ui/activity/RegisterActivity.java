package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.RegisterContract;
import com.slb.ttdandroidframework.ui.presenter.RegisterPresenter;
import com.slb.ttdandroidframework.weight.CountTimerButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseMvpActivity<RegisterContract.IView, RegisterContract.IPresenter> implements RegisterContract.IView{
    @BindView(R.id.edtMobile)
    EditText edtMobile;
    @BindView(R.id.edtPwd)
    EditText edtPwd;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.edtVCode)
    EditText edtVCode;
    @BindView(R.id.BtnGetCode)
    CountTimerButton BtnGetCode;

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public RegisterContract.IPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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

    @OnClick({R.id.btnRegister,R.id.BtnGetCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                mPresenter.register(edtMobile.getText().toString(),edtPwd.getText().toString(),edtVCode.getText().toString());
                break;
            case R.id.BtnGetCode:
                mPresenter.getVcode(edtMobile.getText().toString());
                break;
        }
    }

    @Override
    public void getVcodeSuccess() {
        BtnGetCode.startCountTimer();
    }

    @Override
    public void registerSuccess() {
        showToastMsg("注册成功");
        finish();
    }
}
