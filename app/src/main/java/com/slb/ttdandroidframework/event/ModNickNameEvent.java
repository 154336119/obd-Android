package com.slb.ttdandroidframework.event;

/**
 * Created by juan on 2018/8/1.
 */

public class ModNickNameEvent {
    String name;

    public ModNickNameEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
