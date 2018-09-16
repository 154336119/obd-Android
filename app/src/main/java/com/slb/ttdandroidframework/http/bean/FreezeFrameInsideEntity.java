package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juan on 2018/6/23.
 */

public class FreezeFrameInsideEntity implements Parcelable {
    private String des;
    private String value;
    private String util;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUtil() {
        return util;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.des);
        dest.writeString(this.value);
        dest.writeString(this.util);
    }

    public FreezeFrameInsideEntity() {
    }

    protected FreezeFrameInsideEntity(Parcel in) {
        this.des = in.readString();
        this.value = in.readString();
        this.util = in.readString();
    }

    public static final Parcelable.Creator<FreezeFrameInsideEntity> CREATOR = new Parcelable.Creator<FreezeFrameInsideEntity>() {
        @Override
        public FreezeFrameInsideEntity createFromParcel(Parcel source) {
            return new FreezeFrameInsideEntity(source);
        }

        @Override
        public FreezeFrameInsideEntity[] newArray(int size) {
            return new FreezeFrameInsideEntity[size];
        }
    };
}
