package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.contract.ModNameContract;
import com.slb.ttdandroidframework.ui.presenter.ModNamePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改昵称
 */
public class ModNickNameActivity extends BaseMvpActivity<ModNameContract.IView, ModNameContract.IPresenter>
        implements ModNameContract.IView {

    @BindView(R.id.edtSerialNumber)
    EditText edtSerialNumber;
    @BindView(R.id.btnComfirm)
    Button btnComfirm;

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.Change_Username);
    }

    @Override
    public void success() {
        showMsg("修改成功");
        finish();
    }

    @Override
    public ModNameContract.IPresenter initPresenter() {
        return new ModNamePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mod_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnComfirm)
    public void onViewClicked() {
        mPresenter.modName(edtSerialNumber.getText().toString());
    }
}
