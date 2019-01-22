package com.slb.ttdandroidframework.command.mymil;

import android.content.Context;

import com.github.pires.obd.commands.control.DtcNumberCommand;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;

/**
 * Created by juan on 2018/10/15.
 */

public class MyDtcNumberCommand extends DtcNumberCommand {

    @Override
    public String getName() {
        return Base.getContext().getString(R.string.Your_Check_Engine_Light_MIL_is_ON_OFF);
    }

    @Override
    public String getFormattedResult() {
        if(super.getFormattedResult().contains("ON")){
            return Base.getContext().getString(R.string.unit1_on);
        }else{
            return Base.getContext().getString(R.string.unit1_off);
        }
    }
}
