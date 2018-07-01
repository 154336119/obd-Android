package com.slb.ttdandroidframework.ui.contract;


import com.slb.frame.ui.presenter.IBaseFragmentPresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

/**
 * 李彬杰
 * Created on 2017/1/5.
 * 常规的列表
 */

public class MyComFragmentListContract {
    public interface IView extends IBaseLoadingDialogView {
    }
    public interface IPresenter<T> extends IBaseFragmentPresenter<T> {
    }
}
