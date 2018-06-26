package com.slb.ttdandroidframework.ui.contract;


import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

/**
 * 李彬杰
 * Created on 2017/1/5.
 * 尾气检测
 */

public class EmissionTestContract {
    public interface IView extends IBaseLoadingDialogView {
    }
    public interface IPresenter<T> extends IBasePresenter<T> {
    }
}
