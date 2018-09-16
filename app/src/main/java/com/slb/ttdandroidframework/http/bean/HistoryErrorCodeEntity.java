package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by juan on 2018/6/22.
 * 历史故障
 */

public class HistoryErrorCodeEntity implements Parcelable {

    /**
     * datetime : 2018-09-11
     * dtcSize : 2
     */

    private String datetime;
    private int dtcSize;
    private List<HistoryErrorCodeInsideEntity> dtcs;

    public List<HistoryErrorCodeInsideEntity> getDtcs() {
        return dtcs;
    }

    public void setDtcs(List<HistoryErrorCodeInsideEntity> dtcs) {
        this.dtcs = dtcs;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getDtcSize() {
        return dtcSize;
    }

    public void setDtcSize(int dtcSize) {
        this.dtcSize = dtcSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.datetime);
        dest.writeInt(this.dtcSize);
        dest.writeTypedList(this.dtcs);
    }

    public HistoryErrorCodeEntity() {
    }

    protected HistoryErrorCodeEntity(Parcel in) {
        this.datetime = in.readString();
        this.dtcSize = in.readInt();
        this.dtcs = in.createTypedArrayList(HistoryErrorCodeInsideEntity.CREATOR);
    }

    public static final Parcelable.Creator<HistoryErrorCodeEntity> CREATOR = new Parcelable.Creator<HistoryErrorCodeEntity>() {
        @Override
        public HistoryErrorCodeEntity createFromParcel(Parcel source) {
            return new HistoryErrorCodeEntity(source);
        }

        @Override
        public HistoryErrorCodeEntity[] newArray(int size) {
            return new HistoryErrorCodeEntity[size];
        }
    };
}
