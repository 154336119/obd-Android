package com.slb.ttdandroidframework.command.mode5;

import com.github.pires.obd.commands.ObdCommand;
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
    public Service5Command(String cmd) {
        super(cmd);
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
            return "混合气从富油至贫油传感器阈值电压";
        }else if(num.equals("02")){
            return "混合气从贫油至富油传感器阈值电压";
        }else if(num.equals("03")){
            return "进行切换时间计算的传感器低电压";
        }else if(num.equals("04")){
            return "进行切换时间计算的传感器高电压";
        }else if(num.equals("05")){
            return "混合气从富油至贫油传感器切换时间";
        }else if(num.equals("06")){
            return "混合气从贫油至富油传感器切换时间";
        }else if(num.equals("07")){
            return "测试周期内的传感器最小电压";
        }else if(num.equals("08")){
            return "测试周期内的传感器最大电压";
        }else if(num.equals("09")){
            return "传感器过度时间";
        }else if(num.equals("0a")){
            return "传感器周期";
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
                moudleFiveEntity.setValue(testResult+"");
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
                moudleFiveEntity.setValue(testResult+"");
                int min = ( ByteUtils.byteToShort1(new byte[]{subRawData[4]}) & 0x0FFFF );
                System.out.print(", min("+ByteUtils.bytes2HexString(new byte[]{subRawData[4]})+"): "+ min  );
                moudleFiveEntity.setMin(min+"");
                int max = ( ByteUtils.byteToShort1(new byte[]{subRawData[5]}) & 0x0FFFF );
                System.out.print(", max("+ByteUtils.bytes2HexString(new byte[]{subRawData[5]})+"): "+ max  );
                moudleFiveEntity.setMax(max+"");
                if(testResult<max && testResult>min){
                    moudleFiveEntity.setState(true);
                }else{
                    moudleFiveEntity.setState(false);
                }
            }
        }
        return moudleFiveEntity;
    }
}
