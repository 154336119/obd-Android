package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.contrarywind.interfaces.IPickerViewData;

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
    private String year;
    private String model;
    private String remark;
    private String vin;
    private String id;
    private String make;
    private int version;

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
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
        dest.writeString(this.year);
        dest.writeString(this.model);
        dest.writeString(this.remark);
        dest.writeString(this.vin);
        dest.writeString(this.id);
        dest.writeString(this.make);
        dest.writeInt(this.version);
    }

    public VehicleEntity() {
    }

    protected VehicleEntity(Parcel in) {
        this.licenseNo = in.readString();
        this.year = in.readString();
        this.model = in.readString();
        this.remark = in.readString();
        this.vin = in.readString();
        this.id = in.readString();
        this.make = in.readString();
        this.version = in.readInt();
    }

    public static final Parcelable.Creator<VehicleEntity> CREATOR = new Parcelable.Creator<VehicleEntity>() {
        @Override
        public VehicleEntity createFromParcel(Parcel source) {
            return new VehicleEntity(source);
        }

        @Override
        public VehicleEntity[] newArray(int size) {
            return new VehicleEntity[size];
        }
    };

    @Override
    public String getPickerViewText() {
        return getLicenseNo();
    }
}
