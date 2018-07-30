package com.slb.ttdandroidframework.event;

import com.github.pires.obd.commands.ObdCommand;

/**
 * Created by juan on 2018/7/28.
 * 选择命令
 */

public class ChoiseComEvent {
    private ObdCommand command;

    public ChoiseComEvent(ObdCommand command) {
        this.command = command;
    }

    public ObdCommand getCommand() {
        return command;
    }

    public void setCommand(ObdCommand command) {
        this.command = command;
    }
}
