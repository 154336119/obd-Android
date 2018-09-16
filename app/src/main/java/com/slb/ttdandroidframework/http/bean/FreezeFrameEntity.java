package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by juan on 2018/6/23.
 */

public class FreezeFrameEntity implements Parcelable {
    private String pid;
    private String des;
    private List<FreezeFrameInsideEntity> mInsideList;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<FreezeFrameInsideEntity> getmInsideList() {
        return mInsideList;
    }

    public void setmInsideList(List<FreezeFrameInsideEntity> mInsideList) {
        this.mInsideList = mInsideList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pid);
        dest.writeString(this.des);
        dest.writeTypedList(this.mInsideList);
    }

    public FreezeFrameEntity() {
    }

    protected FreezeFrameEntity(Parcel in) {
        this.pid = in.readString();
        this.des = in.readString();
        this.mInsideList = in.createTypedArrayList(FreezeFrameInsideEntity.CREATOR);
    }

    public static final Parcelable.Creator<FreezeFrameEntity> CREATOR = new Parcelable.Creator<FreezeFrameEntity>() {
        @Override
        public FreezeFrameEntity createFromParcel(Parcel source) {
            return new FreezeFrameEntity(source);
        }

        @Override
        public FreezeFrameEntity[] newArray(int size) {
            return new FreezeFrameEntity[size];
        }
    };
}
