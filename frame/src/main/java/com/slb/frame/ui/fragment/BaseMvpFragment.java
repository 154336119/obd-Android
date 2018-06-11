package com.slb.frame.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.slb.frame.ui.presenter.IBaseFragmentPresenter;

/**
 * 描述：
 * Created by Lee
 * on 2016/9/14.
 */
public abstract class BaseMvpFragment<V,T extends IBaseFragmentPresenter> extends BaseFragment{
    public T mPresenter;
    public abstract T initPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mPresenter.attach((V)this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.deattach();
    }
}
