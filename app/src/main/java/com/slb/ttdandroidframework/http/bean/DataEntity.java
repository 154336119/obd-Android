package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juan on 2018/6/22.
 */

public class DataEntity implements Parcelable {
    private String title;
    private String value;
    private String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private boolean isCheck;
    public DataEntity(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public DataEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        dest.writeString(this.title);
        dest.writeString(this.value);
        dest.writeString(this.des);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
    }

    protected DataEntity(Parcel in) {
        this.title = in.readString();
        this.value = in.readString();
        this.des = in.readString();
        this.isCheck = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DataEntity> CREATOR = new Parcelable.Creator<DataEntity>() {
        @Override
        public DataEntity createFromParcel(Parcel source) {
            return new DataEntity(source);
        }

        @Override
        public DataEntity[] newArray(int size) {
            return new DataEntity[size];
        }
    };
}
