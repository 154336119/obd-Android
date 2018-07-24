package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.MailboxVerificationContract;
import com.slb.ttdandroidframework.ui.presenter.MailboxVerificationPresenter;
import com.slb.ttdandroidframework.weight.CountTimerButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码 - 邮箱验证
 */
public class MailboxVerificationActivity extends BaseMvpActivity<MailboxVerificationContract.IView, MailboxVerificationContract.IPresenter>
        implements MailboxVerificationContract.IView {

    @BindView(R.id.edtMobile)
    EditText edtMobile;
    @BindView(R.id.edtPwd)
    EditText edtPwd;
    @BindView(R.id.btnComfirm)
    Button btnComfirm;
    @BindView(R.id.BtnGetCode)
    CountTimerButton BtnGetCode;

    @Override
    protected String setToolbarTitle() {
        return "邮箱验证";
    }

    @Override
    public MailboxVerificationContract.IPresenter initPresenter() {
        return new MailboxVerificationPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mailbox_verification;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void showCountdown() {
        BtnGetCode.startCountTimer();
    }

    @OnClick({R.id.BtnGetCode, R.id.btnComfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.BtnGetCode:
                break;
            case R.id.btnComfirm:
                ActivityUtil.next(this, ResetPasswordActivity.class);
                break;
        }
    }
}
