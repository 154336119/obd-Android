package com.slb.ttdandroidframework.command.mode6;

import com.github.pires.obd.commands.protocol.AvailablePidsCommand;
import com.github.pires.obd.commands.protocol.AvailablePidsCommand_01_20;
import com.github.pires.obd.enums.AvailableCommandNames;

public class AvailableMidsCommand_01_20 extends AvailablePidsCommand {
    public AvailableMidsCommand_01_20() {
        super("06 00");
    }

    public AvailableMidsCommand_01_20(AvailableMidsCommand_01_20 other) {
        super(other);
    }

    public String getName() {
        return AvailableCommandNames.PIDS_01_20.getValue();
    }
}
