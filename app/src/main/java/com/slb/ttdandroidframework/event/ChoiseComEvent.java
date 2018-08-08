package com.slb.ttdandroidframework.event;

import com.github.pires.obd.commands.ObdCommand;

/**
 * Created by juan on 2018/7/28.
 * 选择命令
 */

public class ChoiseComEvent {
    private String commandName;

    public ChoiseComEvent(String commandName, boolean isAdd) {
        this.commandName = commandName;
        this.isAdd = isAdd;
    }

    //true 添加，false 删除
    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    private boolean isAdd;

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
}
