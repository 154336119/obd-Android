package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ResetEvent;
import com.slb.ttdandroidframework.ui.contract.MailboxVerificationContract;
import com.slb.ttdandroidframework.ui.presenter.MailboxVerificationPresenter;
import com.slb.ttdandroidframework.util.IdentifyingCode;
import com.slb.ttdandroidframework.util.config.BizcContant;
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
    @BindView(R.id.IvCode)
    ImageView IvCode;

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
        IvCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
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

    @OnClick({R.id.BtnGetCode, R.id.btnComfirm,R.id.IvCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.BtnGetCode:
                mPresenter.getCode(edtMobile.getText().toString());
                break;
            case R.id.btnComfirm:
                String email = edtMobile.getText().toString();
                String code = edtPwd.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    showToastMsg("请输入邮箱");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showToastMsg("请输入验证码");
                    return;
                }

                String realCode=IdentifyingCode.getInstance().getCode().toLowerCase();
                if(!realCode.equals(code)){
                    showMsg("验证码错误");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(BizcContant.PARA_EMAIL, email);
                bundle.putString(BizcContant.PARA_CODE, code);
                ActivityUtil.next(this, ResetPasswordActivity.class, bundle, false);
                break;
            case R.id.IvCode:
                IvCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
        }
    }


    @Subscribe
    public void onResetEvent(ResetEvent event) {
        finish();
    }

    @Override
    protected boolean rxBusRegist() {
        return true;
    }

}
