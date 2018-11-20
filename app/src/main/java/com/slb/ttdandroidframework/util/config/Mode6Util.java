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

public class Mode6Util {
    public static String get01_20SupportTid(int i){
        String tid  = null ;
        switch (i){
            case 1:
                return "01";
            case 2:
                return "02";
            case 3:
                return "03";
            case 4:
                return "04";
            case 5:
                return "05";
            case 6:
                return "06";
            case 7:
                return "07";
            case 8:
                return "08";
            case 9:
                return "09";
            case 10:
                return "0A";
            case 11:
                return "0B";
            case 12:
                return "0C";
            case 13:
                return "0D";
            case 14:
                return "0E";
            case 15:
                return "0F";
            case 16:
                return "10";
            case 17:
                return "11";
            case 18:
                return "12";
            case 19:
                return "13";
            case 20:
                return "14";
            case 21:
                return "15";
            case 22:
                return "16";
            case 23:
                return "17";
            case 24:
                return "18";
            case 25:
                return "19";
            case 26:
                return "1A";
            case 27:
                return "1B";
            case 28:
                return "1C";
            case 29:
                return "1D";
            case 30:
                return "1E";
            case 31:
                return "1F";
            case 32:
                return "20";
        }
        return tid;
    }

    public static String get21_40SupportTid(int i){
        String tid  = null ;
        switch (i){
            case 1:
                return "21";
            case 2:
                return "22";
            case 3:
                return "23";
            case 4:
                return "24";
            case 5:
                return "25";
            case 6:
                return "26";
            case 7:
                return "27";
            case 8:
                return "28";
            case 9:
                return "29";
            case 10:
                return "2A";
            case 11:
                return "2B";
            case 12:
                return "2C";
            case 13:
                return "2D";
            case 14:
                return "2E";
            case 15:
                return "2F";
            case 16:
                return "30";
            case 17:
                return "31";
            case 18:
                return "32";
            case 19:
                return "33";
            case 20:
                return "34";
            case 21:
                return "35";
            case 22:
                return "36";
            case 23:
                return "37";
            case 24:
                return "38";
            case 25:
                return "39";
            case 26:
                return "3A";
            case 27:
                return "3B";
            case 28:
                return "3C";
            case 29:
                return "3D";
            case 30:
                return "3E";
            case 31:
                return "3F";
            case 32:
                return "40";
        }
        return tid;
    }

    public static String get41_60SupportTid(int i){
        String tid  = null ;
        switch (i){
            case 1:
                return "41";
            case 2:
                return "42";
            case 3:
                return "43";
            case 4:
                return "44";
            case 5:
                return "45";
            case 6:
                return "46";
            case 7:
                return "47";
            case 8:
                return "48";
            case 9:
                return "49";
            case 10:
                return "4A";
            case 11:
                return "4B";
            case 12:
                return "4C";
            case 13:
                return "4D";
            case 14:
                return "4E";
            case 15:
                return "4F";
            case 16:
                return "50";
            case 17:
                return "51";
            case 18:
                return "52";
            case 19:
                return "53";
            case 20:
                return "54";
            case 21:
                return "55";
            case 22:
                return "56";
            case 23:
                return "57";
            case 24:
                return "58";
            case 25:
                return "59";
            case 26:
                return "5A";
            case 27:
                return "5B";
            case 28:
                return "5C";
            case 29:
                return "5D";
            case 30:
                return "5E";
            case 31:
                return "5F";
            case 32:
                return "60";
        }
        return tid;
    }
}
