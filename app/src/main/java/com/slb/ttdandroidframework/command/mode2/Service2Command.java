package com.slb.ttdandroidframework.command.mode2;

import com.github.pires.obd.commands.ObdCommand;

/**
 * Created by mabin on 2018-09-07.
 */

public class Service2Command extends ObdCommand {
    int rpm;

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public Service2Command(String cmd) {
        super(cmd);
    }

    protected void performCalculations() {
        this.rpm = (((Integer)this.buffer.get(2)).intValue() * 256 + ((Integer)this.buffer.get(3)).intValue()) / 4;
    }
    @Override
    public String getFormattedResult() {
        return "";
    }

    @Override
    public String getCalculatedResult() {
        return "calculatedResult";
    }

    @Override
    public String getName() {
        return "Command:"+cmd;
    }
}
