package com.slb.ttdandroidframework.command.mil;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.engine.RuntimeCommand;

/**
 * Created by mabin on 2018-09-07.
 */

public class Command0141 extends ObdCommand {
    public Command0141(String cmd) {
        super(cmd);
    }
    @Override
    public String getName() {
//        return "清除故障码后的运行时长";
        return cmd;
    }

    @Override
    protected void performCalculations() {

    }

    @Override
    public String getFormattedResult() {
        return null;
    }

    @Override
    public String getCalculatedResult() {
        return null;
    }
}
