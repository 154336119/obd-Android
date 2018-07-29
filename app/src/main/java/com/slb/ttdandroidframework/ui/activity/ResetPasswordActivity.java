package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.hwangjr.rxbus.RxBus;
import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ResetEvent;
import com.slb.ttdandroidframework.ui.contract.ResetPasswordContract;
import com.slb.ttdandroidframework.ui.presenter.ResetPasswordPresenter;
import com.slb.ttdandroidframework.util.config.BizcContant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseMvpActivity<ResetPasswordContract.IView, ResetPasswordContract.IPresenter>
        implements ResetPasswordContract.IView {


    @BindView(R.id.edtMobile)
    EditText edtMobile;
    @BindView(R.id.edtPwd)
    EditText edtPwd;
    @BindView(R.id.btnComfirm)
    Button btnComfirm;

    public String mEmail;
    public String mCode;

    @Override
    public void getIntentExtras() {
        super.getIntentExtras();
        mEmail = getIntent().getStringExtra(BizcContant.PARA_EMAIL);
        mCode = getIntent().getStringExtra(BizcContant.PARA_CODE);
    }

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

    @Override
    public void resetSuccess() {
        RxBus.get().post(new ResetEvent());
        finish();
    }

    @OnClick(R.id.btnComfirm)
    public void onViewClicked() {
        String pwd = edtMobile.getText().toString();
        String againPwd = edtPwd.getText().toString();
        if(TextUtils.isEmpty(pwd)){
            showToastMsg("请输入密码");
            return;
        }
        if(TextUtils.isEmpty(againPwd)){
            showToastMsg("请再次输入密码");
            return;
        }
        if(!pwd.equals(againPwd)){
            showToastMsg("密码不一致");
            return;
        }

        mPresenter.reset(mEmail,pwd,mCode);
    }
}
