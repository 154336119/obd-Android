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

public class Command4E extends  RuntimeCommand {
    @Override
    public String getName() {
//        return "清除故障码后的运行时长";
        return "Time since diagnostic trouble codes cleared";
    }
}
