package com.slb.ttdandroidframework.command.mymil;

import com.github.pires.obd.commands.ObdCommand;
import com.slb.frame.utils.DateUtils;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;

/**
 * Created by mabin on 2018-09-07.
 */

public class Command4E extends  ObdCommand {

    public Command4E() {
        super("01 4E");
    }

    @Override
    public String getName() {
//        return "清除故障码后的运行时长";
        return Base.getContext().getString(R.string.Run_Time_Since_Trouble_Codes_Cleared);
    }

    private int value = 0;


    protected void performCalculations() {
        this.value = ((Integer)this.buffer.get(2)).intValue() * 256 + ((Integer)this.buffer.get(3)).intValue();
    }

    public String getFormattedResult() {
        return DateUtils.formatDuringDHM(this.value * 1000);
    }

    public String getCalculatedResult() {
        return String.valueOf(this.value);
    }

    public String getResultUnit() {
        return "s";
    }

}
