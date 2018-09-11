package com.slb.ttdandroidframework.http.bean;

/**
 * Created by juan on 2018/6/22.
 * 历史故障
 */

public class HistoryErrorCodeEntity {

    /**
     * faultTime : 2018-08-04 23:01:02
     * pid :
     * remark :
     * id : 00000000658e655f01658eddf9c70000
     * category : dtc_confirm
     * saveTime : 2018-08-31 15:23:57
     */

    private String faultTime;
    private String pid;
    private String remark;
    private String id;
    private String category;
    private String saveTime;

    public String getFaultTime() {
        return faultTime;
    }

    public void setFaultTime(String faultTime) {
        this.faultTime = faultTime;
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

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }
}
