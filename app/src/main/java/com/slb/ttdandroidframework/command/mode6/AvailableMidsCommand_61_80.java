package com.slb.ttdandroidframework.command.mode6;

import com.github.pires.obd.commands.protocol.AvailablePidsCommand;
import com.github.pires.obd.enums.AvailableCommandNames;

public class AvailableMidsCommand_61_80 extends AvailablePidsCommand {
    public AvailableMidsCommand_61_80() {
        super("06 80");
    }

    public AvailableMidsCommand_61_80(AvailableMidsCommand_61_80 other) {
        super(other);
    }

    public String getName() {
        return AvailableCommandNames.PIDS_21_40.getValue();
    }
}
