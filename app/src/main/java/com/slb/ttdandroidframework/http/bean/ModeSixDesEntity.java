package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ModeSixDesEntity implements Parcelable {
   String description ;
   String compId;
   String testId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.compId);
        dest.writeString(this.testId);
    }

    public ModeSixDesEntity() {
    }

    protected ModeSixDesEntity(Parcel in) {
        this.description = in.readString();
        this.compId = in.readString();
        this.testId = in.readString();
    }

    public static final Parcelable.Creator<ModeSixDesEntity> CREATOR = new Parcelable.Creator<ModeSixDesEntity>() {
        @Override
        public ModeSixDesEntity createFromParcel(Parcel source) {
            return new ModeSixDesEntity(source);
        }

        @Override
        public ModeSixDesEntity[] newArray(int size) {
            return new ModeSixDesEntity[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }
}
