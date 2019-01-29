package com.slb.ttdandroidframework.command.mode6;

import com.github.pires.obd.commands.protocol.AvailablePidsCommand;
import com.github.pires.obd.enums.AvailableCommandNames;

public class AvailableMidsCommand_21_40 extends AvailablePidsCommand {
    public AvailableMidsCommand_21_40() {
        super("06 20");
    }

    public AvailableMidsCommand_21_40(AvailableMidsCommand_21_40 other) {
        super(other);
    }

    public String getName() {
        return AvailableCommandNames.PIDS_21_40.getValue();
    }
}
