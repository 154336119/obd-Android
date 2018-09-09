package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.ModPasswordContract;
import com.slb.ttdandroidframework.ui.presenter.ModPwPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModPasswordActivity extends BaseMvpActivity<ModPasswordContract.IView, ModPasswordContract.IPresenter>
        implements ModPasswordContract.IView {
    @BindView(R.id.edCurPw)
    EditText edCurPw;
    @BindView(R.id.edtNewPw)
    EditText edtNewPw;
    @BindView(R.id.edtAgainPw)
    EditText edtAgainPw;
    @BindView(R.id.btnComfirm)
    Button btnComfirm;
    @Override
    protected String setToolbarTitle() {
        return "设置";
    }
    @Override
    public void resetSuccess() {
        showMsg("修改成功");
        finish();
    }

    @Override
    public ModPasswordContract.IPresenter initPresenter() {
        return new ModPwPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mod_pw;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnComfirm)
    public void onViewClicked() {
        mPresenter.reset(edCurPw.getText().toString(),edtNewPw.getText().toString(),edtAgainPw.getText().toString());
    }
}
