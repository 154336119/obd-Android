package com.slb.ttdandroidframework.command.mymil;

import com.github.pires.obd.commands.ObdCommand;
import com.slb.frame.utils.DateUtils;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;

/**
 * Created by mabin on 2018-09-07.
 */

public class Command4D extends ObdCommand {
    @Override
    public String getName() {
//        return "故障灯亮时发动机的运行时长";
        return Base.getContext().getString(R.string.Run_Time_with_Check_Engine_Light_On);
    }
    public Command4D() {
        super("01 4D");
    }
    private int value = 0;

    protected void performCalculations() {
        this.value = ((Integer)this.buffer.get(2)).intValue() * 256 + ((Integer)this.buffer.get(3)).intValue();
    }

    public String getFormattedResult() {
        return DateUtils.formatDuringDHM(this.value * 1000);

//        String dd = String.format("%02d", new Object[]{Integer.valueOf(this.value / 3600)});
//        String hh = String.format("%02d", new Object[]{Integer.valueOf(this.value / 3600)});
//        String mm = String.format("%02d", new Object[]{Integer.valueOf(this.value % 3600 / 60)});
//        String ss = String.format("%02d", new Object[]{Integer.valueOf(this.value % 60)});
//        return String.format("%s:%s:%s", new Object[]{hh, mm, ss});
    }

    public String getCalculatedResult() {
        return String.valueOf(this.value);
    }

    public String getResultUnit() {
        return "s";
    }
}
