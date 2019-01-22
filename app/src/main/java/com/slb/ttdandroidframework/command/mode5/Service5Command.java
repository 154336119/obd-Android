package com.slb.ttdandroidframework.command.mode5;

import com.github.pires.obd.commands.ObdCommand;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ModeSixEntity;
import com.slb.ttdandroidframework.http.bean.MoudleFiveEntity;
import com.slb.ttdandroidframework.util.ByteUtils;
import com.slb.ttdandroidframework.util.config.Mode5Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabin on 2018-09-07.
 */

public class Service5Command extends ObdCommand {
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    String unit;
    public Service5Command(String cmd,String unit) {
        super(cmd);
        this.unit = unit;
    }


    @Override
    protected void performCalculations() {
        System.out.println("do calculation");
    }

    @Override
    public String getFormattedResult() {
        List<String> subRawDataList = new ArrayList<>();
        if(rawData.length() == 8){
            for ( int i=0; i<rawData.length(); i+=8){
                //返回的16进制数据7个字节为一组
                byte[] subRawData = ByteUtils.hexStr2BinArr(rawData.substring(i,i+8));
                System.out.print("开始解析: "+ByteUtils.bin2HexStr(subRawData));
                String testId = rawData.substring(2,4);
                System.out.print(", testId: "+testId);
                byte b = ByteUtils.bitToByte("0"+ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(1,8));
                String componentId = ByteUtils.byte2HexString(b);
                System.out.print(", O2id: "+ componentId);
                int testResult = ( ByteUtils.byteToShort1(new byte[]{subRawData[3]}) & 0x0FFFF );
                System.out.print(", testValue("+ByteUtils.bytes2HexString(new byte[]{subRawData[3]})+"): "+ testResult );
            }
        }else if(rawData.length() == 12){
            for ( int i=0; i<rawData.length(); i+=12){
                //返回的16进制数据7个字节为一组
                byte[] subRawData = ByteUtils.hexStr2BinArr(rawData.substring(i,i+12));
                System.out.print("开始解析: "+ByteUtils.bin2HexStr(subRawData));
                String testId = rawData.substring(2,4);
                System.out.print(", testId: "+testId);
                byte b = ByteUtils.bitToByte("0"+ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(1,8));
                String componentId = ByteUtils.byte2HexString(b);
                System.out.print(", O2id: "+ componentId);
                int testResult = ( ByteUtils.byteToShort1(new byte[]{subRawData[3]}) & 0x0FFFF );
                System.out.print(", testValue("+ByteUtils.bytes2HexString(new byte[]{subRawData[3]})+"): "+ testResult );
                int min = ( ByteUtils.byteToShort1(new byte[]{subRawData[4]}) & 0x0FFFF );
                System.out.print(", min("+ByteUtils.bytes2HexString(new byte[]{subRawData[4]})+"): "+ min  );
                int max = ( ByteUtils.byteToShort1(new byte[]{subRawData[5]}) & 0x0FFFF );
                System.out.print(", max("+ByteUtils.bytes2HexString(new byte[]{subRawData[5]})+"): "+ max  );
            }
        }

        return rawData;
    }

    @Override
    public String getCalculatedResult() {
        return "calculatedResult";
    }

    @Override
    public String getName() {
        String num = (cmd.substring(cmd.length()-5,cmd.length()-3));
        if(num.equals("01")){
            return Base.getContext().getString(R.string.Rich_to_lean_sensorthreshold_voltage);
        }else if(num.equals("02")){
            return Base.getContext().getString(R.string.Lean_to_rich_sensor_threshold_voltage);
        }else if(num.equals("03")){
            return Base.getContext().getString(R.string.Low_sensor_voltage_for_switch_time_calculation);
        }else if(num.equals("04")){
            return Base.getContext().getString(R.string.High_sensor_voltage_for_switch_time_calculation);
        }else if(num.equals("05")){
            return Base.getContext().getString(R.string.Rich_to_lean_sensor_switch_time);
        }else if(num.equals("06")){
            return Base.getContext().getString(R.string.Lean_to_lean_sensor_switch_time);
        }else if(num.equals("07")){
            return Base.getContext().getString(R.string.Minimum_sensor_voltage_for_test_cycle);
        }else if(num.equals("08")){
            return Base.getContext().getString(R.string.Maximum_sensor_voltage_for_test_cycle);
        }else if(num.equals("09")){
            return Base.getContext().getString(R.string.Time_between_sensor_transitions);
        }else if(num.equals("0a")){
            return Base.getContext().getString(R.string.Sensor_period);
        }
        return "Command:"+cmd;
    }

    public MoudleFiveEntity getMoudleFiveEntity(){
        MoudleFiveEntity moudleFiveEntity = new MoudleFiveEntity();
        if(rawData.length() == 8){
            for ( int i=0; i<rawData.length(); i+=8){
                //返回的16进制数据7个字节为一组
                byte[] subRawData = ByteUtils.hexStr2BinArr(rawData.substring(i,i+8));
                System.out.print("开始解析: "+ByteUtils.bin2HexStr(subRawData));
                moudleFiveEntity.setNum(cmd.substring(cmd.length()-5,cmd.length()-3));
                System.out.print(", testId: "+cmd.substring(cmd.length()-5,cmd.length()-3));
                byte b = ByteUtils.bitToByte("0"+ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(1,8));
                String componentId = ByteUtils.byte2HexString(b);
                System.out.print(", O2id: "+ componentId);
                moudleFiveEntity.setName(this.getName());
                int testResult = ( ByteUtils.byteToShort1(new byte[]{subRawData[3]}) & 0x0FFFF );
                System.out.print(", testValue("+ByteUtils.bytes2HexString(new byte[]{subRawData[3]})+"): "+ testResult );
//                moudleFiveEntity.setValue(testResult+"");
                moudleFiveEntity.setValue(Mode5Util.getRealValue(moudleFiveEntity.getNum(),testResult)+"");
                moudleFiveEntity.setUnit(unit);
                moudleFiveEntity.setState(true);
            }
        }else if(rawData.length() == 12){
            for ( int i=0; i<rawData.length(); i+=12){
                //返回的16进制数据7个字节为一组
                byte[] subRawData = ByteUtils.hexStr2BinArr(rawData.substring(i,i+12));
                System.out.print("开始解析: "+ByteUtils.bin2HexStr(subRawData));
                String testId = rawData.substring(2,4);
                System.out.print(", testId: "+cmd.substring(cmd.length()-5,cmd.length()-3));
                moudleFiveEntity.setNum(cmd.substring(cmd.length()-5,cmd.length()-3));
                byte b = ByteUtils.bitToByte("0"+ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(1,8));
                String componentId = ByteUtils.byte2HexString(b);
                System.out.print(", O2id: "+ componentId);
                moudleFiveEntity.setName(this.getName());
                int testResult = ( ByteUtils.byteToShort1(new byte[]{subRawData[3]}) & 0x0FFFF );
                System.out.print(", testValue("+ByteUtils.bytes2HexString(new byte[]{subRawData[3]})+"): "+ testResult );
                moudleFiveEntity.setValue(Mode5Util.getRealValue(moudleFiveEntity.getNum(),testResult)+"");
                int min = ( ByteUtils.byteToShort1(new byte[]{subRawData[4]}) & 0x0FFFF );
                System.out.print(", min("+ByteUtils.bytes2HexString(new byte[]{subRawData[4]})+"): "+ min  );
                moudleFiveEntity.setMin(Mode5Util.getRealValue(moudleFiveEntity.getNum(),min)+"");
                int max = ( ByteUtils.byteToShort1(new byte[]{subRawData[5]}) & 0x0FFFF );
                System.out.print(", max("+ByteUtils.bytes2HexString(new byte[]{subRawData[5]})+"): "+ max  );
                moudleFiveEntity.setMax(Mode5Util.getRealValue(moudleFiveEntity.getNum(),max)+"");
                moudleFiveEntity.setUnit(unit);
                if(testResult<=max && testResult>min){
                    moudleFiveEntity.setState(true);
                }else{
                    moudleFiveEntity.setState(false);
                }
            }
        }
        return moudleFiveEntity;
    }
}
