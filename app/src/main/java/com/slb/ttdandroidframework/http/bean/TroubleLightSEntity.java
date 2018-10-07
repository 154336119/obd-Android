package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juan on 2018/6/24.
 * 故障灯
 */

public class TroubleLightSEntity implements Parcelable {
    String name;
    String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    public TroubleLightSEntity() {
    }

    protected TroubleLightSEntity(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<TroubleLightSEntity> CREATOR = new Parcelable.Creator<TroubleLightSEntity>() {
        @Override
        public TroubleLightSEntity createFromParcel(Parcel source) {
            return new TroubleLightSEntity(source);
        }

        @Override
        public TroubleLightSEntity[] newArray(int size) {
            return new TroubleLightSEntity[size];
        }
    };
}
