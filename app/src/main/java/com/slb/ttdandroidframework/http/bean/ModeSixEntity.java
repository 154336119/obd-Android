package com.slb.ttdandroidframework.http.bean;

/**
 * Created by juan on 2018/9/26.
 */

public class ModeSixEntity {
    private String tid;
    private String cid;
    private String max;
    private String min;
    private String value;
    private boolean state;
    private String des;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    private String mid;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ModeSixEntity{" +
                "tid='" + tid + '\'' +
                ", cid='" + cid + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", value='" + value + '\'' +
                ", state=" + state +
                '}';
    }
}
