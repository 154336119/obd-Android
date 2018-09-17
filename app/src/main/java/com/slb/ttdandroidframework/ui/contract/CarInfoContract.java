package com.slb.ttdandroidframework.ui.contract;


import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;
import com.slb.ttdandroidframework.http.bean.CarBrandEntity;
import com.slb.ttdandroidframework.http.bean.CarModelEntity;

import java.util.List;

/**
 * 李彬杰
 * Created on 2017/1/5.
 * 我的汽车
 */

public class CarInfoContract {
    public interface IView extends IBaseLoadingDialogView {
        //制造商
        void getCArBrandListSuccess(List<CarBrandEntity> list);
        void getCarModeListSuccess(List<CarModelEntity> list);
        void addCarSuccess();
        void edidCarSuccess();
        void delectCarSuccess();
    }
    public interface IPresenter<T> extends IBasePresenter<T> {
        void getCArBrandListSuccess();
        void getCarModeListSuccess(String id);
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
