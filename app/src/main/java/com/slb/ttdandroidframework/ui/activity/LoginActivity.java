package com.slb.ttdandroidframework.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.MainActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.LoginContract;
import com.slb.ttdandroidframework.ui.presenter.LoginPresenter;
import com.slb.ttdandroidframework.util.ByteUtils;
import com.slb.ttdandroidframework.util.config.ObdConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import danfei.shulaibao.widget.common.ChooseEditText;
import danfei.shulaibao.widget.common.autoedt.ClearAutoCompleteEditText;

public class LoginActivity extends BaseMvpActivity<LoginContract.IView, LoginContract.IPresenter>
        implements LoginContract.IView{
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
        edtMobile.setText("154336119@qq.com");
        edtPwd.setText("123456");

//        edtMobile.setText("53255941@qq.com");
//        edtPwd.setText("123abc");
       byte[] subRawData =  ByteUtils.hexStr2BinArr("0141414100040000");
        String ss = ByteUtils.bytes2BinStr(new byte[]{subRawData[5]}).substring(1,8);
        ss.getBytes();
    }

    @OnClick({R.id.tvRegister, R.id.tvwForgotPwd, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvRegister:
                ActivityUtil.next(this, RegisterActivity.class);
                break;
            case R.id.tvwForgotPwd:
                ActivityUtil.next(this, MailboxVerificationActivity.class);
                break;
            case R.id.btnLogin:
                mPresenter.login(edtMobile.getText().toString(), edtPwd.getText().toString());
                break;
        }
    }

    @Override
    public void loginSuccess() {
        ActivityUtil.next(this, MainActivity.class);
        finish();
    }
}
