package com.slb.ttdandroidframework.command.mil;

import com.github.pires.obd.commands.control.DtcNumberCommand;

/**
 * Created by juan on 2018/10/15.
 */

public class MyDtcNumberCommand extends DtcNumberCommand {
    @Override
    public String getName() {
        return "Light state";
    }

    @Override
    public String getFormattedResult() {
        if(super.getFormattedResult().contains("ON")){
            return "On";
        }else{
            return "Off";
        }
    }
}
