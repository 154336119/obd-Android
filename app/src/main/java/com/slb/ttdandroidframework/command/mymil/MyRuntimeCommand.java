package com.slb.ttdandroidframework.command.mymil;

import com.github.pires.obd.commands.engine.RuntimeCommand;
import com.slb.frame.utils.DateUtils;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;

/**
 * Created by juan on 2018/12/3.
 */

public class MyRuntimeCommand extends RuntimeCommand {
    public String getFormattedResult() {
        String hh = String.format("%02d", new Object[]{Integer.valueOf(this.value / 3600)});
        String mm = String.format("%02d", new Object[]{Integer.valueOf(this.value % 3600 / 60)});
        String ss = String.format("%02d", new Object[]{Integer.valueOf(this.value % 60)});
        return String.format("%sHours,%sMinutes,%sSeconds", new Object[]{hh, mm, ss});
    }
    @Override
    public String getName() {
//        return "清除故障码后的运行时长";
        return Base.getContext().getString(R.string.Run_Time_Since_Engine_Start);
    }

    private int value = 0;


    protected void performCalculations() {
        this.value = ((Integer)this.buffer.get(2)).intValue() * 256 + ((Integer)this.buffer.get(3)).intValue();
    }


    public String getCalculatedResult() {
        return String.valueOf(this.value);
    }

    public String getResultUnit() {
        return "s";
    }
}
