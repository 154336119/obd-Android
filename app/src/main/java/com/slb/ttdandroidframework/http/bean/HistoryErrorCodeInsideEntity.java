package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juan on 2018/6/22.
 * 历史故障
 */

public class HistoryErrorCodeInsideEntity implements Parcelable {


    /**
     * obdId : 00000000650eb26a016514a661940019
     * obdSn : 哟哟哟
     * vehicleLicenseNo : 哈哈
     * faultTime : 2018-09-11
     * pid :
     * remark : ”“
     * id : 0000000065b88e950165c922db090006
     * vehicleId : 000000006505835c01650e948c460006
     * category : dtc_confirm
     * userId : 0000000064bc27e70164c10956110001
     */

    private String obdId;
    private String obdSn;
    private String vehicleLicenseNo;
    private String faultTime;
    private String pid;
    private String remark;
    private String id;
    private String vehicleId;
    private String category;
    private String userId;

    public String getObdId() {
        return obdId;
    }

    public void setObdId(String obdId) {
        this.obdId = obdId;
    }

    public String getObdSn() {
        return obdSn;
    }

    public void setObdSn(String obdSn) {
        this.obdSn = obdSn;
    }

    public String getVehicleLicenseNo() {
        return vehicleLicenseNo;
    }

    public void setVehicleLicenseNo(String vehicleLicenseNo) {
        this.vehicleLicenseNo = vehicleLicenseNo;
    }

    public String getFaultTime() {
        return faultTime;
    }

    public void setFaultTime(String faultTime) {
        this.faultTime = faultTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.obdId);
        dest.writeString(this.obdSn);
        dest.writeString(this.vehicleLicenseNo);
        dest.writeString(this.faultTime);
        dest.writeString(this.pid);
        dest.writeString(this.remark);
        dest.writeString(this.id);
        dest.writeString(this.vehicleId);
        dest.writeString(this.category);
        dest.writeString(this.userId);
    }

    public HistoryErrorCodeInsideEntity() {
    }

    protected HistoryErrorCodeInsideEntity(Parcel in) {
        this.obdId = in.readString();
        this.obdSn = in.readString();
        this.vehicleLicenseNo = in.readString();
        this.faultTime = in.readString();
        this.pid = in.readString();
        this.remark = in.readString();
        this.id = in.readString();
        this.vehicleId = in.readString();
        this.category = in.readString();
        this.userId = in.readString();
    }

    public static final Parcelable.Creator<HistoryErrorCodeInsideEntity> CREATOR = new Parcelable.Creator<HistoryErrorCodeInsideEntity>() {
        @Override
        public HistoryErrorCodeInsideEntity createFromParcel(Parcel source) {
            return new HistoryErrorCodeInsideEntity(source);
        }

        @Override
        public HistoryErrorCodeInsideEntity[] newArray(int size) {
            return new HistoryErrorCodeInsideEntity[size];
        }
    };
}
