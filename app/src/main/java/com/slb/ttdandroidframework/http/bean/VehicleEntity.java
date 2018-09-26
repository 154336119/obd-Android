package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;


public class VehicleEntity implements Parcelable , IPickerViewData {

    /**
     * licenseNo : 哈哈
     * year : 2018-7-6
     * model : 呼呼
     * remark :
     * vin : 湖区
     * id : 000000006505835c01650e948c460006
     * make : 就
     * version : 0
     */

    private String licenseNo;
    private CarModelEntity model;
    private String remark;
    private String vin;
    private String id;
    private int version;


    @Override
    public String getPickerViewText() {
        return getLicenseNo();
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public CarModelEntity getModel() {
        return model;
    }

    public void setModel(CarModelEntity model) {
        this.model = model;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        dest.writeString(this.licenseNo);
        dest.writeParcelable(this.model, flags);
        dest.writeString(this.remark);
        dest.writeString(this.vin);
        dest.writeString(this.id);
        dest.writeInt(this.version);
    }

    public VehicleEntity() {
    }

    protected VehicleEntity(Parcel in) {
        this.licenseNo = in.readString();
        this.model = in.readParcelable(CarModelEntity.class.getClassLoader());
        this.remark = in.readString();
        this.vin = in.readString();
        this.id = in.readString();
        this.version = in.readInt();
    }

    public static final Creator<VehicleEntity> CREATOR = new Creator<VehicleEntity>() {
        @Override
        public VehicleEntity createFromParcel(Parcel source) {
            return new VehicleEntity(source);
        }

        @Override
        public VehicleEntity[] newArray(int size) {
            return new VehicleEntity[size];
        }
    };
}
