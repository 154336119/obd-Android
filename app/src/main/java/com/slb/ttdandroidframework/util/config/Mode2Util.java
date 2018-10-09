package com.slb.ttdandroidframework.util.config;

import com.slb.ttdandroidframework.command.mode2.Service2Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 2018/9/16.
 * 04  PercentageObdCommand
 * 05  EngineCoolantTemperatureCommand
 * 0b  IntakeManifoldPressureCommand
 * 0c  RPMCommand
 * 0D  SpeedCommand
 * 11  ThrottlePositionCommand(PercentageObdCommand)
 * 10  MassAirFlowCommand
 */

public class Mode2Util {

    public static List<Service2Command> getService2CommandForIndex(int i){
        List<Service2Command> list = null;
        switch(i){
            case 0:
                list = getService2Command00();
                break;
            case 1:
                list = getService2Command01();
                break;
            case 2:
                list = getService2Command02();
                break;
            case 3:
                list = getService2Command03();
                break;
            case 4:
                list = getService2Command04();
                break;
            case 5:
                list = getService2Command05();
                break;
        }
        return list;
    }

    public static List<Service2Command> getService2Command00(){
        List<Service2Command> list = new ArrayList<>();
        list.add(new Service2Command("02 02 00"));
        list.add(new Service2Command("02 04 00"));
        list.add(new Service2Command("02 05 00"));
        list.add(new Service2Command("02 0b 00"));
        list.add(new Service2Command("02 0c 00"));
        return list;
    }

    public static List<Service2Command> getService2Command01(){
        List<Service2Command> list = new ArrayList<>();
        list.add(new Service2Command("02 02 01"));
        list.add(new Service2Command("02 04 01"));
        list.add(new Service2Command("02 05 01"));
        list.add(new Service2Command("02 0b 01"));
        list.add(new Service2Command("02 0c 01"));
        return list;
    }

    public static List<Service2Command> getService2Command02(){
        List<Service2Command> list = new ArrayList<>();
        list.add(new Service2Command("02 02 02"));
        list.add(new Service2Command("02 04 02"));
        list.add(new Service2Command("02 05 02"));
        list.add(new Service2Command("02 0b 02"));
        list.add(new Service2Command("02 0c 02"));
        return list;
    }

    public static List<Service2Command> getService2Command03(){
        List<Service2Command> list = new ArrayList<>();
        list.add(new Service2Command("02 02 03"));
        list.add(new Service2Command("02 04 03"));
        list.add(new Service2Command("02 05 03"));
        list.add(new Service2Command("02 0b 03"));
        list.add(new Service2Command("02 0c 03"));
        return list;
    }

    public static List<Service2Command> getService2Command04(){
        List<Service2Command> list = new ArrayList<>();
        list.add(new Service2Command("02 02 04"));
        list.add(new Service2Command("02 04 04"));
        list.add(new Service2Command("02 05 04"));
        list.add(new Service2Command("02 0b 04"));
        list.add(new Service2Command("02 0c 04"));
        return list;
    }

    public static List<Service2Command> getService2Command05(){
        List<Service2Command> list = new ArrayList<>();
        list.add(new Service2Command("02 02 05"));
        list.add(new Service2Command("02 04 05"));
        list.add(new Service2Command("02 05 05"));
        list.add(new Service2Command("02 0b 05"));
        list.add(new Service2Command("02 0c 05"));
        return list;
    }
}
