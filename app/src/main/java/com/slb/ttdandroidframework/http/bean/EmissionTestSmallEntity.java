package com.slb.ttdandroidframework.http.bean;

/**
 * Created by juan on 2018/6/22.
 */

public class EmissionTestSmallEntity {
    String name;
    Boolean isOK = false;
    Boolean isShowState = true;


    public EmissionTestSmallEntity(String name) {
        this.name = name;
    }

    public EmissionTestSmallEntity() {
    }

    public EmissionTestSmallEntity(String name, Boolean isShowState) {
        this.name = name;
        this.isShowState = isShowState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOK() {
        return isOK;
    }

    public void setOK(Boolean OK) {
        isOK = OK;
    }

    public Boolean getShowState() {
        return isShowState;
    }

    public void setShowState(Boolean showState) {
        isShowState = showState;
    }
}
