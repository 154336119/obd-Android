package com.slb.ttdandroidframework.ui.presenter;


import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.http.bean.CarBrandEntity;
import com.slb.ttdandroidframework.http.bean.CarModelEntity;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.http.callback.DialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.http.service.ComServiceUrl;
import com.slb.ttdandroidframework.ui.contract.CarInfoContract;
import com.slb.ttdandroidframework.ui.contract.ObdInfoContract;

import java.util.List;

/**
 * 李彬杰
 * 注释: 汽车相关信息
 */

public class CarInfoPresenter extends AbstractBasePresenter<CarInfoContract.IView>
        implements CarInfoContract.IPresenter<CarInfoContract.IView> {
    @Override
    public void addCar(String licenseNo, String vin, String make, String model, String year) {
        if(TextUtils.isEmpty(licenseNo)){
            mView.showMsg("请输入车牌号");
            return;
        }
        if(TextUtils.isEmpty(vin)){
            mView.showMsg("请输入车辆编号");
            return;
        }
        if(TextUtils.isEmpty(make)){
            mView.showMsg("请输入品牌");
            return;
        }
        if(TextUtils.isEmpty(model)){
            mView.showMsg("请输入型号");
            return;
        }
        if(TextUtils.isEmpty(year)){
            mView.showMsg("请输入年代");
            return;
        }
        String id = Base.getUserEntity().getId();
        OkGo.<LzyResponse<UserEntity>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ ComServiceUrl.addCar)//
                .tag(this)//
                .params("licenseNo",licenseNo)
                .params("vin",vin)
                .params("make",make)
                .params("model",model)
                .params("year",year)
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
    public void editCar(String vehicleId, String licenseNo, String vin, String make, String model, String year) {
        if(TextUtils.isEmpty(licenseNo)){
            mView.showMsg("请输入车牌号");
            return;
        }
        if(TextUtils.isEmpty(vin)){
            mView.showMsg("请输入车辆编号");
            return;
        }
        if(TextUtils.isEmpty(make)){
            mView.showMsg("请输入品牌");
            return;
        }
        if(TextUtils.isEmpty(model)){
            mView.showMsg("请输入型号");
            return;
        }
        if(TextUtils.isEmpty(year)){
            mView.showMsg("请输入年代");
            return;
        }
        String id = Base.getUserEntity().getId();
        OkGo.<LzyResponse<UserEntity>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ ComServiceUrl.editCar)//
                .tag(this)//
                .params("licenseNo",licenseNo)
                .params("vin",vin)
                .params("make",make)
                .params("model",model)
                .params("year",year)
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
    public void getCArBrandListSuccess() {
        OkGo.<LzyResponse<List<CarBrandEntity>>>get(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ "api/vehicle/make")//
                .tag(this)
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new DialogCallback<LzyResponse<List<CarBrandEntity>>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<List<CarBrandEntity>>> response) {
                        mView.getCArBrandListSuccess(response.body().data);
                    }
                });
    }

    @Override
    public void getCarModeListSuccess(String id) {
        OkGo.<LzyResponse<List<CarModelEntity>>>get(DnsFactory.getInstance().getDns().getCommonBaseUrl()+ "/api/vehicle/model/"+id)//
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

