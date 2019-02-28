package com.slb.ttdandroidframework.command.mode6;

import com.slb.ttdandroidframework.command.ObdCommand;
import com.slb.ttdandroidframework.http.bean.ModeSixEntity;
import com.slb.ttdandroidframework.util.ByteUtils;
import com.slb.ttdandroidframework.util.config.Mode5Util;
import com.slb.ttdandroidframework.util.config.Mode6Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabin on 2018-09-07.
 */

public class Service6Command extends ObdCommand {
    String mid;
    public Service6Command(String cmd,String mid) {
        super(cmd);
        this.mid = mid;
    }

    public Service6Command(String cmd) {
        super(cmd);
    }

    @Override
    protected void performCalculations() {
        System.out.println("do calculation");
    }

    @Override
    public String getFormattedResult() {
//        List<String> subRawDataList = new ArrayList<>();
//        for ( int i=0; i<rawData.length(); i+=14){
//            //返回的16进制数据7个字节为一组
//            byte[] subRawData = ByteUtils.hexStr2BinArr(rawData.substring(i,i+14));
//            System.out.print("开始解析: "+ByteUtils.bin2HexStr(subRawData));
//            String testId = rawData.substring(2,4);
//            System.out.print(", testId: "+testId);
//            byte b = ByteUtils.bitToByte("0"+ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(1,8));
//            String componentId = ByteUtils.byte2HexString(b);
//            System.out.print(", componentId: "+ componentId);
//            int limitType = Integer.parseInt(ByteUtils.bytes2BinStr(new byte[]{subRawData[2]}).substring(0,1)); //第二个字节的MSB表示limitType 1是最小值 0是最大值
//            System.out.print(", limittype: "+ (limitType==0?"最大值":"最小值") );
//
//            int testResult = ( ByteUtils.byteToShort(new byte[]{subRawData[3],subRawData[4]}) & 0x0FFFF );
//            System.out.print(", testValue("+ByteUtils.bytes2HexString(new byte[]{subRawData[3],subRawData[4]})+"): "+ testResult );
//            int testLimit = ( ByteUtils.byteToShort(new byte[]{subRawData[5],subRawData[6]}) & 0x0FFFF );
//            System.out.print(", testLimit("+ByteUtils.bytes2HexString(new byte[]{subRawData[5],subRawData[6]})+"): "+ testLimit  );
//            if ( limitType == 0 ){
//                System.out.println(",测试结果:"+ (testResult<testLimit?"success":"fail")); //如果当前值小于最大值则成功
//            }else{
//                System.out.println(",测试结果:"+ (testResult>testLimit?"success":"fail"));//如果当前值大于最小值则成功
//            }
//
//        }
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
            System.out.print(", componentId: "+ ByteUtils.intToHex(componentId));
            entity.setCid(ByteUtils.intToHex(componentId));
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

    public List<ModeSixEntity> getNewList(){
        List<ModeSixEntity> modeSixEntityList = new ArrayList<>();
        //1.MID之前的都截掉
        int firstIndex = rawData.indexOf("46"+mid)+2;
        rawData = rawData.substring(firstIndex);
        //2.去分号
//        int fenhaoIndex = rawData.indexOf(":");
        StringBuilder sb = new StringBuilder(rawData);
//        sb.replace(fenhaoIndex-1, fenhaoIndex, "");
        List<String> fenhaoList = new ArrayList<>();
        for(int i=0;i<rawData.length();i++){
            if(rawData.charAt(i)==':')
                fenhaoList.add(sb.substring(i-1,i+1));
        }
        String sb1 =  sb.toString();
        for(String fenhao : fenhaoList){
            sb1 =sb1.replace(fenhao,"");
        }
         sb =  new StringBuilder(sb1);
        //3.tid分组18位一组
        List<String> tidList = new ArrayList<>();
        int num = sb.length()/18;
        int j = 0;
        for(int i=0;i<num;i++){
            String str = sb.substring(j,j+18);
            j = j+18;
            tidList.add(str);
        }


        for(int i=0;i<tidList.size();i++){
            ModeSixEntity entity = new ModeSixEntity();
            String tidRaw = tidList.get(i);
            //开始解析
            String tid;
            String uid;
            String testVale;
            String minValue;
            String maxValue;
            //mid
            entity.setMid(mid);
            //tid
            tid = tidRaw.substring(2,4);
            entity.setTid(tid);
            //Mode6Enum
            uid = tidRaw.substring(4,6);
            Mode6Enum mode6Enum = Mode6Enum.getBean(uid);
            //testVale
            testVale = getRealValue(tidRaw.substring(6,10),mode6Enum.getPer());
            entity.setValue(testVale);
            //minValue
            minValue = getRealValue(tidRaw.substring(10,14),mode6Enum.getPer());
            entity.setMin(minValue);
            //maxValue
            maxValue = getRealValue(tidRaw.substring(14,18),mode6Enum.getPer());
            entity.setMax(maxValue);
            //unit
            entity.setUnit(mode6Enum.getUnit());

            //des

            //成功失败
            if(Double.valueOf(testVale)>Double.valueOf(minValue) && Double.valueOf(testVale)<Double.valueOf(maxValue)){
                entity.setState(true);
            }else {
                entity.setState(false);
            }

            modeSixEntityList.add(entity);
        }
        return modeSixEntityList;
    }

    private String getRealValue(String hexValue,Double per){
        int intTestValue = Integer.parseInt(hexValue,16);
        double perTestValue = intTestValue * per;
        perTestValue = (double)Math.round(perTestValue*100)/100;
        return String.valueOf(perTestValue);
    }
}
