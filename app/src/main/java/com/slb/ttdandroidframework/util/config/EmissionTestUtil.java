package com.slb.ttdandroidframework.util.config;

import com.slb.ttdandroidframework.command.mode2.Service2Command;
import com.slb.ttdandroidframework.http.bean.EmissionTestSmallEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 2018/9/16.
 */

public class EmissionTestUtil {

    public static List<EmissionTestSmallEntity> getEmissionTestSmallListForIndex(int i){
        List<EmissionTestSmallEntity> list = null;
        switch(i){
            case 0:
                list = getEmissionTestSmallList01();
                break;
            case 1:
                list = getEmissionTestSmallList02();
                break;
            case 2:
                list = getEmissionTestSmallList03();
                break;
        }
        return list;
    }
    public static List<EmissionTestSmallEntity> getEmissionTestSmallList01(){
        List<EmissionTestSmallEntity> list = new ArrayList<>();
        list.add(new EmissionTestSmallEntity("Check Engine Light is Off",false));
        list.add(new EmissionTestSmallEntity("0 Comfirmed Trouble Codes",false));
        list.add(new EmissionTestSmallEntity("0 Pending Trouble Codes",false));
        return list;
    }

    public static List<EmissionTestSmallEntity> getEmissionTestSmallList02(){
        List<EmissionTestSmallEntity> list = new ArrayList<>();
        list.add(new EmissionTestSmallEntity("Misfire"));
        list.add(new EmissionTestSmallEntity("Fuel System"));
        list.add(new EmissionTestSmallEntity("Components"));
        return list;
    }



    public static List<EmissionTestSmallEntity> getEmissionTestSmallList03(){
        List<EmissionTestSmallEntity> list = new ArrayList<>();
        list.add(new EmissionTestSmallEntity("Catalyst"));
        list.add(new EmissionTestSmallEntity("Heated Catalyst"));
        list.add(new EmissionTestSmallEntity("Evaporative System"));
        list.add(new EmissionTestSmallEntity("Secondary Air System"));
        list.add(new EmissionTestSmallEntity("A/C Refrigerant"));
        list.add(new EmissionTestSmallEntity("Oxygen Sensor"));
        list.add(new EmissionTestSmallEntity("Oxygen Sensor Heater"));
        list.add(new EmissionTestSmallEntity("EGR System"));
        return list;
    }
}
