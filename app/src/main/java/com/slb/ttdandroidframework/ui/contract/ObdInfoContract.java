package com.slb.ttdandroidframework.ui.contract;


import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

/**
 * 李彬杰
 * Created on 2017/1/5.
 * 设备
 */

public class ObdInfoContract {
    public interface IView extends IBaseLoadingDialogView {
        void addObdSuccess();
        void edidObdCarSuccess();
        void delectObdSuccess();
    }
    public interface IPresenter<T> extends IBasePresenter<T> {
       void addObd(String serialsNumber ,String productModel );
       void editObd(String obdId
               ,String serialsNumber
               ,String productModel);
       void delectObd(String obdId);
    }
}
