package com.slb.ttdandroidframework.command.mil;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.engine.RuntimeCommand;

/**
 * Created by mabin on 2018-09-07.
 */

public class Command4D extends ObdCommand {
    @Override
    public String getName() {
//        return "故障灯亮时发动机的运行时长";
        return "Time run by the engine while MIL is activated";
    }
    public Command4D() {
        super("01 4D");
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
