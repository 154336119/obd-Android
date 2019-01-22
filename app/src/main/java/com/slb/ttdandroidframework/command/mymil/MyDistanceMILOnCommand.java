package com.slb.ttdandroidframework.command.mymil;

import com.github.pires.obd.commands.control.DistanceMILOnCommand;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;

/**
 * Created by juan on 2018/12/3.
 */

public class MyDistanceMILOnCommand extends DistanceMILOnCommand {
    @Override
    public String getName() {
        return Base.getContext().getString(R.string.Distance_with_Check_Engine_Light_On);
    }
    public String getResultUnit() {
        return this.useImperialUnits?"miles":"km";
    }
}
