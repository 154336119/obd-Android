package com.slb.ttdandroidframework.command.mode2;

import com.github.pires.obd.commands.ObdCommand;

/**
 * Created by mabin on 2018-09-07.
 */

public class Service2Command extends ObdCommand {

    public Service2Command(String cmd) {
        super(cmd);
    }

    @Override
    protected void performCalculations() {
        System.out.println("do calculation");
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
