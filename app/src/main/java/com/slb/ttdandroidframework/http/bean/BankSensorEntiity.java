package com.slb.ttdandroidframework.http.bean;

import java.util.List;

/**
 * Created by juan on 2018/10/7.
 */

public class BankSensorEntiity {
    String bankSensorName;
    List<ModeSixEntity> list;

    public String getBankSensorName() {
        return bankSensorName;
    }

    public void setBankSensorName(String bankSensorName) {
        this.bankSensorName = bankSensorName;
    }

    public List<ModeSixEntity> getList() {
        return list;
    }

    public void setList(List<ModeSixEntity> list) {
        this.list = list;
    }
}
