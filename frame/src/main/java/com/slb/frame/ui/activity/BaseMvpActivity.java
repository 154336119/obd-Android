package com.slb.frame.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.slb.frame.ui.presenter.IBasePresenter;

/**
 * 描述：
 * Created by Lee
 * on 2016/9/14.
 */
public abstract class BaseMvpActivity<V,T extends IBasePresenter> extends BaseActivity {
    public T mPresenter;
    public abstract T initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mPresenter.attach((V)this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.deattach();
    }

    public void showMsg(String msg) {
        showToastMsg(msg);
    }

    public void showLoadingDialog(String msg) {
        showWaitDialog(msg);
    }

    public void loadingDialogDismiss() {
        hideWaitDialog();
    }
}
