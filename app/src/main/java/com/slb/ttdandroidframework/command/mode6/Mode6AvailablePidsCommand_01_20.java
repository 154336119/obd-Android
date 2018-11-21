package com.slb.ttdandroidframework.command.mode6;

import com.github.pires.obd.commands.ObdCommand;
import com.slb.ttdandroidframework.util.ByteUtils;
import com.slb.ttdandroidframework.util.config.Mode6Util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabin on 2018-09-07.
 */

public class Mode6AvailablePidsCommand_01_20 extends ObdCommand {

    public Mode6AvailablePidsCommand_01_20() {
        super("06 00");
    }

    @Override
    protected void performCalculations() {

    }

    @Override
    public String getFormattedResult() {
        //计算可用的Test ID
        String availableRawData = getCalculatedResult();
        StringBuffer sb = new StringBuffer();
        for(int start=0; start<availableRawData.length(); start+=2){
            String hex = availableRawData.substring(start,start+2);
            String binary = ByteUtils.hexStr2BinStr(hex);
            sb.append(binary);
        }

       List<String> availablePids = new ArrayList<>();
        for ( int i=0; i<sb.length(); i++){
            if ( sb.charAt(i) == '1' ){
                String pid =  Mode6Util.get01_20SupportTid(i+1);
                availablePids.add(pid);
            }
        }
        return StringUtils.join(availablePids.toArray(),",");
    }

    @Override
    public String getCalculatedResult() {
//        return "C0000001";
        return rawData.substring(rawData.length()-8,rawData.length());
    }

    @Override
    public String getName() {
        return "Mode5AvailablePidsCommand_01_20";
    }
}
