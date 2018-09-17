package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * Created by juan on 2018/9/17.
 */

public class CarModelEntity implements Parcelable ,IPickerViewData {

    /**
     * name : A3 e-tron
     * remark :
     * id : 402883e765e2c8230165e2c86b64002e
     * version : 0
     */

    private String name;
    private String remark;
    private String id;
    private int version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(this.remark);
        dest.writeString(this.id);
        dest.writeInt(this.version);
    }

    public CarModelEntity() {
    }

    protected CarModelEntity(Parcel in) {
        this.name = in.readString();
        this.remark = in.readString();
        this.id = in.readString();
        this.version = in.readInt();
    }

    public static final Parcelable.Creator<CarModelEntity> CREATOR = new Parcelable.Creator<CarModelEntity>() {
        @Override
        public CarModelEntity createFromParcel(Parcel source) {
            return new CarModelEntity(source);
        }

        @Override
        public CarModelEntity[] newArray(int size) {
            return new CarModelEntity[size];
        }
    };

    @Override
    public String getPickerViewText() {
        return name;
    }
}
