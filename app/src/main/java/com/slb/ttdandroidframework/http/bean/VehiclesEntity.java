package com.slb.ttdandroidframework.http.bean;

import java.util.List;

/**
 * 列表对象
 *
 {
 "msg":"",
 "data":{
 "vehicles":[
 {
 "licenseNo":"哈哈",
 "year":"2018-7-6",
 "model":"呼呼",
 "remark":null,
 "vin":"湖区",
 "id":"000000006505835c01650e948c460006",
 "make":"就",
 "version":0
 }
 ]
 },
 "success":true
 }
 */
public class VehiclesEntity {
    public List<VehicleEntity> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
    }

    private List<VehicleEntity> vehicles;

}
