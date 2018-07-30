package com.slb.ttdandroidframework.http.bean;

/**
 * Created by juan on 2018/6/22.
 */

public class DataEntity {
    private String title;
    private String value;

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
}
