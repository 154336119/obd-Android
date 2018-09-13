package com.slb.ttdandroidframework.ui.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.hwangjr.rxbus.RxBus;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.presenter.AbstractBaseFragmentPresenter;
import com.slb.frame.ui.presenter.AbstractBasePresenter;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.event.RefreshMineVehicleListtEvent;
import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;
import com.slb.ttdandroidframework.http.bean.VehiclesEntity;
import com.slb.ttdandroidframework.http.callback.ActivityDialogCallback;
import com.slb.ttdandroidframework.http.callback.DialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyArrayResponse;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.ui.contract.HistoryContract;
import com.slb.ttdandroidframework.ui.contract.MineContract;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class HistoryPresenter extends AbstractBaseFragmentPresenter<HistoryContract.IView>
        implements HistoryContract.IPresenter<HistoryContract.IView>{
    @Override
    public void getHistoryErrorCode(ObdEntity obd , VehicleEntity vehicle, String startDate, String endDate) {
        if(obd == null){
            mView.showMsg("请选择设备");
            return;
        }
        if(vehicle == null){
            mView.showMsg("请选择车辆");
            return;
        }
        if(TextUtils.isEmpty(startDate)){
            mView.showMsg("请选择开始时间");
            return;
        }
        if(TextUtils.isEmpty(endDate)){
            mView.showMsg("请选择结束时间");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", Base.getUserEntity().getId());
        jsonObject.put("obdId", obd.getId());
        jsonObject.put("vehicleId", vehicle.getId());
        jsonObject.put("startDate", startDate);
        jsonObject.put("endDate", endDate);

        OkGo.<LzyResponse<List<HistoryErrorCodeEntity>>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+"api/command-log/history")
                .tag(this)
//                .upJson(jsonObject.toString())
                .params("userId",Base.getUserEntity().getId())
                .params("obdId",obd.getId())
                .params("vehicleId",vehicle.getId())
                .params("startDate",startDate)
                .params("endDate",endDate)
                .isMultipart(true)
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new DialogCallback<LzyResponse<List<HistoryErrorCodeEntity>>>(this.mView) {
                    @Override
                    public void onSuccess(Response<LzyResponse<List<HistoryErrorCodeEntity>>> response) {
                        Logger.d(response.body());
                    }
                });
    }
}
