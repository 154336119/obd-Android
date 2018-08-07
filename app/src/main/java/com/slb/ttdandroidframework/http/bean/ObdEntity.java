package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.contrarywind.interfaces.IPickerViewData;

public class ObdEntity implements IPickerViewData, Parcelable {
    /**
     * productModel : 刚刚给
     * serialsNumber : 光顾
     * remark :
     * id : 000000006505835c01650e78d7020004
     * version : 0
     */

    private String productModel;
    private String serialsNumber;
    private String remark;
    private String id;
    private int version;

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getSerialsNumber() {
        return serialsNumber;
    }

    public void setSerialsNumber(String serialsNumber) {
        this.serialsNumber = serialsNumber;
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
    public String getPickerViewText() {
        return getProductModel();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productModel);
        dest.writeString(this.serialsNumber);
        dest.writeString(this.remark);
        dest.writeString(this.id);
        dest.writeInt(this.version);
    }

    public ObdEntity() {
    }

    protected ObdEntity(Parcel in) {
        this.productModel = in.readString();
        this.serialsNumber = in.readString();
        this.remark = in.readString();
        this.id = in.readString();
        this.version = in.readInt();
    }

    public static final Parcelable.Creator<ObdEntity> CREATOR = new Parcelable.Creator<ObdEntity>() {
        @Override
        public ObdEntity createFromParcel(Parcel source) {
            return new ObdEntity(source);
        }

        @Override
        public ObdEntity[] newArray(int size) {
            return new ObdEntity[size];
        }
    };
}
