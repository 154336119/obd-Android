package com.slb.ttdandroidframework.ui.contract;


import com.slb.frame.ui.presenter.IBasePresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;
import com.slb.ttdandroidframework.http.bean.CarMakeEntity;
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
        void getCarBrandListSuccess(List<CarMakeEntity> list);
        void getCarModeListSuccess(List<CarModelEntity> list);
        void addCarSuccess();
        void edidCarSuccess();
        void delectCarSuccess();
    }
    public interface IPresenter<T> extends IBasePresenter<T> {
        void getCarBrandList();
        void getCarModeList(String id);
       void addCar(CarModelEntity carModelEntity ,String year,String name);
        void editCar(String vehicleId
                ,CarModelEntity carModelEntity
                ,String year
                ,String name);
        void delectCar(String obdId);
    }
}
