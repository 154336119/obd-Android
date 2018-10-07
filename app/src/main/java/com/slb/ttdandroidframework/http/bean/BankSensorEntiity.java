package com.slb.ttdandroidframework.http.bean;

import java.util.List;

/**
 * Created by juan on 2018/10/7.
 */

public class BankSensorEntiity {
    String bankSensorName;
    List<MoudleFiveEntity> list;

    public String getBankSensorName() {
        return bankSensorName;
    }

    public void setBankSensorName(String bankSensorName) {
        this.bankSensorName = bankSensorName;
    }

    public List<MoudleFiveEntity> getList() {
        return list;
    }

    public void setList(List<MoudleFiveEntity> list) {
        this.list = list;
    }
}
