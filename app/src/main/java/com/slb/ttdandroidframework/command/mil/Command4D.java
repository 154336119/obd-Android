package com.slb.ttdandroidframework.command.mil;

import com.github.pires.obd.commands.engine.RuntimeCommand;

/**
 * Created by mabin on 2018-09-07.
 */

public class Command4D extends  RuntimeCommand {
    @Override
    public String getName() {
//        return "故障灯亮时发动机的运行时长";
        return "Time run by the engine while MIL is activated";
    }
}
