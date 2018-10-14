package com.slb.ttdandroidframework.command.mil;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.engine.RuntimeCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.slb.ttdandroidframework.http.bean.ModeSixEntity;
import com.slb.ttdandroidframework.util.ByteUtils;

import java.util.ArrayList;
import java.util.List;

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
        return "Time since diagnostic trouble codes cleared";
    }

    private int value = 0;


    protected void performCalculations() {
        this.value = ((Integer)this.buffer.get(2)).intValue() * 256 + ((Integer)this.buffer.get(3)).intValue();
    }

    public String getFormattedResult() {
        String hh = String.format("%02d", new Object[]{Integer.valueOf(this.value / 3600)});
        String mm = String.format("%02d", new Object[]{Integer.valueOf(this.value % 3600 / 60)});
        String ss = String.format("%02d", new Object[]{Integer.valueOf(this.value % 60)});
        return String.format("%s:%s:%s", new Object[]{hh, mm, ss});
    }

    public String getCalculatedResult() {
        return String.valueOf(this.value);
    }

    public String getResultUnit() {
        return "s";
    }

}
