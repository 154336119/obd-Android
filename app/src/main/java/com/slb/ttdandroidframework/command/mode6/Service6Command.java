package com.slb.ttdandroidframework.command.mode6;

import com.github.pires.obd.commands.ObdCommand;
import com.slb.ttdandroidframework.http.bean.ModeSixEntity;
import com.slb.ttdandroidframework.util.ByteUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabin on 2018-09-07.
 */

public class Service6Command extends ObdCommand {
    public Service6Command(String cmd) {
        super(cmd);
    }

    @Override
    protected void performCalculations() {
        System.out.println("do calculation");
    }

    @Override
    public String getFormattedResult() {
        List<String> subRawDataList = new ArrayList<>();
        for ( int i=0; i<rawData.length(); i+=14){
            //返回的16进制数据7个字节为一组
            byte[] subRawData = ByteUtils.hexStr2BinArr(rawData.substring(i,i+14));
            System.out.print("开始解析: "+ByteUtils.bin2HexStr(subRawData));
            String testId = rawData.substring(2,4);
            System.out.print(", testId: "+testId);
            byte b = ByteUtils.bitToByte("0"+ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(1,8));
            String componentId = ByteUtils.byte2HexString(b);
            System.out.print(", componentId: "+ componentId);
            int limitType = Integer.parseInt(ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(0,1)); //第二个字节的MSB表示limitType 1是最小值 0是最大值
            System.out.print(", limittype: "+ (limitType==0?"最大值":"最小值") );

            int testResult = ( ByteUtils.byteToShort(new byte[]{subRawData[3],subRawData[4]}) & 0x0FFFF );
            System.out.print(", testValue("+ByteUtils.bytes2HexString(new byte[]{subRawData[3],subRawData[4]})+"): "+ testResult );
            int testLimit = ( ByteUtils.byteToShort(new byte[]{subRawData[5],subRawData[6]}) & 0x0FFFF );
            System.out.print(", testLimit("+ByteUtils.bytes2HexString(new byte[]{subRawData[5],subRawData[6]})+"): "+ testLimit  );
            if ( limitType == 0 ){
                System.out.println(",测试结果:"+ (testResult<testLimit?"success":"fail")); //如果当前值小于最大值则成功
            }else{
                System.out.println(",测试结果:"+ (testResult>testLimit?"success":"fail"));//如果当前值大于最小值则成功
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
        return "Command:"+cmd;
    }

    public List<ModeSixEntity> getList(){
        List<ModeSixEntity> modeSixEntityList = new ArrayList<>();
        for ( int i=0; i<rawData.length(); i+=14){
            ModeSixEntity entity = new ModeSixEntity();
            //返回的16进制数据7个字节为一组
            byte[] subRawData = ByteUtils.hexStr2BinArr(rawData.substring(i,i+14));
            System.out.print("开始解析: "+ByteUtils.bin2HexStr(subRawData));
            String testId = rawData.substring(2,4);
            System.out.print(", testId: "+testId);
            entity.setTid(testId);
            byte b = ByteUtils.bitToByte("0"+ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(1,8));
            String componentId = ByteUtils.byte2HexString(b);
            System.out.print(", componentId: "+ componentId);
            entity.setCid(componentId);
            int limitType = Integer.parseInt(ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(0,1)); //第二个字节的MSB表示limitType 1是最小值 0是最大值
            System.out.print(", limittype: "+ (limitType==0?"最大值":"最小值") );
            int testResult = ( ByteUtils.byteToShort(new byte[]{subRawData[3],subRawData[4]}) & 0x0FFFF );
            System.out.print(", testValue("+ByteUtils.bytes2HexString(new byte[]{subRawData[3],subRawData[4]})+"): "+ testResult );
            entity.setValue(testResult+"");

            int testLimit = ( ByteUtils.byteToShort(new byte[]{subRawData[5],subRawData[6]}) & 0x0FFFF );
            System.out.print(", testLimit("+ByteUtils.bytes2HexString(new byte[]{subRawData[5],subRawData[6]})+"): "+ testLimit  );
            if ( limitType == 0 ){
                System.out.println(",测试结果:"+ (testResult<testLimit?"success":"fail")); //如果当前值小于最大值则成功
                entity.setState(testResult<testLimit?true:false);
                entity.setMax(testLimit+"");
            }else{
                System.out.println(",测试结果:"+ (testResult>testLimit?"success":"fail"));//如果当前值大于最小值则成功
                entity.setState(testResult>testLimit?true:false);
                entity.setMin(testLimit+"");
            }
            modeSixEntityList.add(entity);
        }
        return modeSixEntityList;
    }
}
