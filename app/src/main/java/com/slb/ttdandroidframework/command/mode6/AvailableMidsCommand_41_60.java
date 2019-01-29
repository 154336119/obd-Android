package com.slb.ttdandroidframework.command.mode6;

import com.github.pires.obd.commands.protocol.AvailablePidsCommand;
import com.github.pires.obd.enums.AvailableCommandNames;

public class AvailableMidsCommand_41_60 extends AvailablePidsCommand {
    public AvailableMidsCommand_41_60() {
        super("06 40");
    }

    public AvailableMidsCommand_41_60(AvailableMidsCommand_41_60 other) {
        super(other);
    }

    public String getName() {
        return AvailableCommandNames.PIDS_21_40.getValue();
    }
}
