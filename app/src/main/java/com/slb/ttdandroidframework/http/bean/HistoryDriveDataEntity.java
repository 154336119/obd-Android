package com.slb.ttdandroidframework.http.bean;

/**
 * Created by juan on 2018/6/22.
 * 行车数据
 */

public class HistoryDriveDataEntity {
    String time;
    Float value;

    public HistoryDriveDataEntity(String time, Float value) {
        this.time = time;
        this.value = value;
    }

    public HistoryDriveDataEntity() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
