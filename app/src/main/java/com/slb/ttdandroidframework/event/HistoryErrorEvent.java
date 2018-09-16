package com.slb.ttdandroidframework.event;

import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;

import java.util.List;

/**
 * Created by juan on 2018/8/1.
 */

public class HistoryErrorEvent {
    public HistoryErrorEvent(List<HistoryErrorCodeEntity> mList) {
        this.mList = mList;
    }

    public List<HistoryErrorCodeEntity> getmList() {
        return mList;
    }

    public void setmList(List<HistoryErrorCodeEntity> mList) {
        this.mList = mList;
    }

    List<HistoryErrorCodeEntity> mList;
}
