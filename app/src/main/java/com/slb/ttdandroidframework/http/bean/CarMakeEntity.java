package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;


/**
 * Created by juan on 2018/9/17.
 */

public class CarMakeEntity implements Parcelable,IPickerViewData {

    /**
     * name : Volvo
     * logo : “”
     * remark :
     * id : 402883e765e2c8230165e2c8e3ea04a5
     * version : 0
     */

    private String name;
    private String logo;
    private String remark;
    private String id;
    private int version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
        dest.writeString(this.name);
        dest.writeString(this.logo);
        dest.writeString(this.remark);
        dest.writeString(this.id);
        dest.writeInt(this.version);
    }

    public CarMakeEntity() {
    }

    protected CarMakeEntity(Parcel in) {
        this.name = in.readString();
        this.logo = in.readString();
        this.remark = in.readString();
        this.id = in.readString();
        this.version = in.readInt();
    }

    public static final Parcelable.Creator<CarMakeEntity> CREATOR = new Parcelable.Creator<CarMakeEntity>() {
        @Override
        public CarMakeEntity createFromParcel(Parcel source) {
            return new CarMakeEntity(source);
        }

        @Override
        public CarMakeEntity[] newArray(int size) {
            return new CarMakeEntity[size];
        }
    };

    @Override
    public String getPickerViewText() {
        return name;
    }
}
