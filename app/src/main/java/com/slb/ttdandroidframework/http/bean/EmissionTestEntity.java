package com.slb.ttdandroidframework.http.bean;

import java.util.List;

/**
 * Created by juan on 2018/6/22.
 */

public class EmissionTestEntity {
    String name;
    List<EmissionTestSmallEntity> list;

    public EmissionTestEntity(String name, List<EmissionTestSmallEntity> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmissionTestSmallEntity> getList() {
        return list;
    }

    public void setList(List<EmissionTestSmallEntity> list) {
        this.list = list;
    }
}
