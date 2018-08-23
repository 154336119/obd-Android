package com.slb.ttdandroidframework.event;

/**
 * Created by juan on 2018/7/29.
 */

public class ObdServiceStateEvent {
    boolean isConnect;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public ObdServiceStateEvent(boolean isConnect) {
        this.isConnect = isConnect;
    }
}
