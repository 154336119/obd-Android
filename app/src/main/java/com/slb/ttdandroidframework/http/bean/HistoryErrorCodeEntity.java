package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juan on 2018/6/22.
 * 历史故障
 */

public class HistoryErrorCodeEntity implements Parcelable {

    /**
     * faultTime : 2018-08-04 23:01:02
     * pid :
     * remark :
     * id : 00000000658e655f01658eddf9c70000
     * category : dtc_confirm
     * saveTime : 2018-08-31 15:23:57
     * version : 0
     */

    private String faultTime;
    private String pid;
    private String remark;
    private String id;
    private String category;
    private String saveTime;
    private int version;
    private ObdEntity obd;
    private VehicleEntity vehicle;
    private UserEntity user;

    public ObdEntity getObd() {
        return obd;
    }

    public void setObd(ObdEntity obd) {
        this.obd = obd;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.faultTime);
        dest.writeString(this.pid);
        dest.writeString(this.remark);
        dest.writeString(this.id);
        dest.writeString(this.category);
        dest.writeString(this.saveTime);
        dest.writeInt(this.version);
        dest.writeParcelable(this.obd, flags);
        dest.writeParcelable(this.vehicle, flags);
        dest.writeParcelable(this.user, flags);
    }

    public HistoryErrorCodeEntity() {
    }

    protected HistoryErrorCodeEntity(Parcel in) {
        this.faultTime = in.readString();
        this.pid = in.readString();
        this.remark = in.readString();
        this.id = in.readString();
        this.category = in.readString();
        this.saveTime = in.readString();
        this.version = in.readInt();
        this.obd = in.readParcelable(ObdEntity.class.getClassLoader());
        this.vehicle = in.readParcelable(VehicleEntity.class.getClassLoader());
        this.user = in.readParcelable(UserEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<HistoryErrorCodeEntity> CREATOR = new Parcelable.Creator<HistoryErrorCodeEntity>() {
        @Override
        public HistoryErrorCodeEntity createFromParcel(Parcel source) {
            return new HistoryErrorCodeEntity(source);
        }

        @Override
        public HistoryErrorCodeEntity[] newArray(int size) {
            return new HistoryErrorCodeEntity[size];
        }
    };
}
