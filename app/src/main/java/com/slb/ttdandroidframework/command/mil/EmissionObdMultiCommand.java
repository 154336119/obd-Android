package com.slb.ttdandroidframework.command.mil;

import android.text.TextUtils;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.control.DtcNumberCommand;
import com.github.pires.obd.commands.control.PendingTroubleCodesCommand;
import com.github.pires.obd.commands.control.TroubleCodesCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.slb.ttdandroidframework.http.bean.EmissionTestEntity;
import com.slb.ttdandroidframework.http.bean.EmissionTestSmallEntity;
import com.slb.ttdandroidframework.http.bean.TroubleLightSEntity;
import com.slb.ttdandroidframework.util.ByteUtils;
import com.slb.ttdandroidframework.util.config.EmissionTestUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 2018/10/7.
 */

public class EmissionObdMultiCommand {

    private ArrayList<ObdCommand> commands;

    /**
     * Default ctor.
     */
    public EmissionObdMultiCommand() {
        this.commands = new ArrayList<>();
    }

    /**
     * Add ObdCommand to list of ObdCommands.
     *
     * @param command a {@link ObdCommand} object.
     */
    public void add(ObdCommand command) {
        this.commands.add(command);
    }

    /**
     * Removes ObdCommand from the list of ObdCommands.
     *
     * @param command a {@link ObdCommand} object.
     */
    public void remove(ObdCommand command) {
        this.commands.remove(command);
    }

    /**
     * Iterate all commands, send them and read response.
     *
     * @param in  a {@link InputStream} object.
     * @param out a {@link OutputStream} object.
     * @throws IOException            if any.
     * @throws InterruptedException if any.
     */
    public void sendCommands(InputStream in, OutputStream out)
            throws IOException, InterruptedException {
        for (ObdCommand command : commands)
            command.run(in, out);
    }

    /**
     * <p>getFormattedResult.</p>
     *
     * @return a {@link String} object.
     */
    public String getFormattedResult() {
        StringBuilder res = new StringBuilder();
        for (ObdCommand command : commands)
            res.append(command.getFormattedResult()).append(",");
        return res.toString();
    }

    public List<EmissionTestEntity> getList(int troubleCodesNum,int pendingTroubleCodesNum){
        List<EmissionTestEntity> list = new ArrayList<>();
        List<EmissionTestSmallEntity> smallEntityList1 = EmissionTestUtil.getEmissionTestSmallList01();
        List<EmissionTestSmallEntity> smallEntityList2 = EmissionTestUtil.getEmissionTestSmallList02();
        List<EmissionTestSmallEntity> smallEntityList3 = EmissionTestUtil.getEmissionTestSmallList03();
        for (ObdCommand command : commands){
            String s = command.getName();
            String si = DtcNumberCommand.class.getName();
            if(command.getName().equals("01 41")){
                byte[] subRawData =  ByteUtils.hexStr2BinArr(command.getResult());
                subRawData = ByteUtils.subBytes(subRawData,subRawData.length-4,4);
                //初始化 - B段数据 - 3组
                char[] charArray02 = ByteUtils.bytes2BinStr(new byte[]{subRawData[1]}).substring(0,8).toCharArray();
                //取高四位
                String str = new String(charArray02) ;
                charArray02 = str.substring(3,7).toCharArray();//获取第二和第三个字符。
                 for(int i = 0;i<3;i++){
                    if(String.valueOf(charArray02[i]).equals("1")){
                        smallEntityList2.get(i).setOK(true);
                    }
                }
                //初始化 - C段数据 - 8组
                char[] charArray03 = ByteUtils.bytes2BinStr(new byte[]{subRawData[3]}).substring(0,8).toCharArray();
                for(int i = 0;i<8;i++){
                    if(String.valueOf(charArray03[i]).equals("1")){
                        smallEntityList3.get(i).setOK(true);
                    }
                }
            }else if(command.getName().equals(AvailableCommandNames.DTC_NUMBER.getValue())){
                if(command.getFormattedResult().contains("ON")){
                    smallEntityList1.get(0).setName( "Check Engine Light is On");
                    smallEntityList1.get(0).setOK(true);
                }
            }
        }
        if(troubleCodesNum>0){
            smallEntityList1.get(1).setOK(false);
        }else{
            smallEntityList1.get(1).setOK(true);
        }
        if(pendingTroubleCodesNum>0){
            smallEntityList1.get(2).setOK(false);
        }else{
            smallEntityList1.get(2).setOK(true);
        }
        smallEntityList1.get(1).setName(troubleCodesNum+" Comfirmed Trouble Codes");
        smallEntityList1.get(2).setName(pendingTroubleCodesNum + " Pending Trouble Codes");
        list.add(new EmissionTestEntity("Smog Readiness Status",smallEntityList1));
        list.add(new EmissionTestEntity("Common I/M Monitors",smallEntityList2));
        list.add(new EmissionTestEntity("Spark Engine I/M Monitors",smallEntityList3));
        return list;
    }


}
