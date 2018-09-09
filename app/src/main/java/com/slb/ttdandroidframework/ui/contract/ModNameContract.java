package com.slb.ttdandroidframework.ui.contract;


import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

/**
 * 李彬杰
 * Created on 2017/1/5.
 * 设置 - 修改昵称
 */

public class ModNameContract {
    public interface IView extends IBaseLoadingDialogView {
        void success();
    }
    public interface IPresenter<T> extends IBasePresenter<T> {
        void modName(String name);
    }
}
