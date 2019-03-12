package com.slb.ttdandroidframework.ui.presenter;


import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.CarMakeEntity;
import com.slb.ttdandroidframework.http.bean.CarModelEntity;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.http.callback.DialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.http.service.ComServiceUrl;
import com.slb.ttdandroidframework.ui.contract.CarInfoContract;

import java.util.List;

/**
 * 李彬杰
 * 注释: 汽车相关信息
 */

public class CarInfoPresenter extends AbstractBasePresenter<CarInfoContract.IView>
        implements CarInfoContract.IPresenter<CarInfoContract.IView> {
    @Override
    public void addCar( CarModelEntity carModelEntity ,String year,String name) {

        if(carModelEntity == null){
            mView.showMsg(Base.getContext().getString(R.string.Please_enter_the_model));
            return;
        }
        if(year == null){
            mView.showMsg(Base.getContext().getString(R.string.Please_enter_the_vehicle_age));
            return;
        }
        if(name == null){
            mView.showMsg(Base.getContext().getString(R.string.Please_enter_the_vehicle_name));
            return;
        }
        String id = Base.getUserEntity().getId();
        OkGo.<LzyResponse<UserEntity>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ ComServiceUrl.addCar)//
                .tag(this)//
                .params("vehicleModelId",carModelEntity.getId())
                .params("name ",name)
                .params("year ",year)
                .params("userId",id)
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new DialogCallback<LzyResponse<UserEntity>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<UserEntity>> response) {
                        mView.addCarSuccess();
                    }
                });

    }

    @Override
    public void editCar(String vehicleId
            , CarModelEntity carModelEntity
            ,String year
            ,String name) {
        if(carModelEntity == null){
            mView.showMsg(Base.getContext().getString(R.string.Please_enter_the_model));
            return;
        }
        if(year == null){
            mView.showMsg(Base.getContext().getString(R.string.Please_enter_the_vehicle_age));
            return;
        }
        if(name == null){
            mView.showMsg(Base.getContext().getString(R.string.Please_enter_the_vehicle_name));
            return;
        }
        String id = Base.getUserEntity().getId();
        OkGo.<LzyResponse<UserEntity>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ ComServiceUrl.editCar)//
                .tag(this)//
                .params("name ",name)
                .params("year ",year)
                .params("vehicleModelId",carModelEntity.getId())
                .params("userId ",id)
                .params("vehicleId",vehicleId)
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new DialogCallback<LzyResponse<UserEntity>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<UserEntity>> response) {
                        mView.addCarSuccess();
                    }
                });
    }

    @Override
    public void delectCar(String vehicleId) {
        String id = Base.getUserEntity().getId();
        OkGo.<LzyResponse<UserEntity>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ ComServiceUrl.delectCar)//
                .tag(this)//
                .params("userId ",id)
                .params("vehicleId",vehicleId)
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new DialogCallback<LzyResponse<UserEntity>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<UserEntity>> response) {
                        mView.delectCarSuccess();
                    }
                });
    }

    @Override
    public void getCarBrandList() {
        OkGo.<LzyResponse<List<CarMakeEntity>>>get(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ "api/vehicle/make")//
                .tag(this)
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new DialogCallback<LzyResponse<List<CarMakeEntity>>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<List<CarMakeEntity>>> response) {
                        if(mView!=null){
                            mView.getCarBrandListSuccess(response.body().data);
                        }
                    }
                });
    }

    @Override
    public void getCarModeList(String id) {
        OkGo.<LzyResponse<List<CarModelEntity>>>get(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ "api/vehicle/model/"+id)//
                .tag(this)
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new DialogCallback<LzyResponse<List<CarModelEntity>>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<List<CarModelEntity>>> response) {
                        mView.getCarModeListSuccess(response.body().data);
                    }
                });
    }
    }

