package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.LoginContract;
import com.slb.ttdandroidframework.ui.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import danfei.shulaibao.widget.common.ChooseEditText;
import danfei.shulaibao.widget.common.autoedt.ClearAutoCompleteEditText;

public class LoginActivity extends BaseMvpActivity<LoginContract.IView, LoginContract.IPresenter> {
    @BindView(R.id.edtMobile)
    EditText edtMobile;
    @BindView(R.id.edtPwd)
    EditText edtPwd;
    @BindView(R.id.tvwForgotPwd)
    TextView tvwForgotPwd;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public LoginContract.IPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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

    @OnClick({R.id.tvRegister, R.id.tvwForgotPwd, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvRegister:
                break;
            case R.id.tvwForgotPwd:
                break;
            case R.id.btnLogin:
                mPresenter.login(edtMobile.getText().toString(), edtPwd.getText().toString());
                break;
        }
    }
}
