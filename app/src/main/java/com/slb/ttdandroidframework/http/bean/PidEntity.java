package com.slb.ttdandroidframework.http.bean;

/**
 * Created by juan on 2018/9/1.
 */

public class PidEntity {

    /**
     * description : Fuel Pressure Regulator 1 Control Circuit High
     * pid : P0092
     * remark :
     * id : 6c0e0ccc-97bc-11e8-af97-d473c06bd8f8
     * category : dtc
     * version : 1
     */

    private String description;
    private String pid;
    private String remark;
    private String id;
    private String category;
    private int version;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
