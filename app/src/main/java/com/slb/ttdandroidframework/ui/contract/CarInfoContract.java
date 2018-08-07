package com.slb.ttdandroidframework.ui.contract;


import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;

/**
 * 李彬杰
 * Created on 2017/1/5.
 * 我的汽车
 */

public class CarInfoContract {
    public interface IView extends IBaseLoadingDialogView {
        void addCarSuccess();
        void edidCarSuccess();
        void delectCarSuccess();
    }
    public interface IPresenter<T> extends IBasePresenter<T> {
       void addCar(String licenseNo , String vin, String make, String model, String year);
        void editCar(String vehicleId
                ,String licenseNo
                ,String vin
                ,String make
                ,String model
                ,String year);
        void delectCar(String obdId);
    }
}
