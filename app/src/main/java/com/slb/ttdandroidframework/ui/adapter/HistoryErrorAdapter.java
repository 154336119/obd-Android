package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.EmissionTestEntity;
import com.slb.ttdandroidframework.http.bean.EmissionTestSmallEntity;
import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;
import com.slb.ttdandroidframework.weight.MyListView;

import java.util.ArrayList;
import java.util.List;


public class HistoryErrorAdapter extends BaseQuickAdapter<HistoryErrorCodeEntity,BaseViewHolder> {


    public HistoryErrorAdapter(List<HistoryErrorCodeEntity> data) {
        super(R.layout.adapter_history_error_code, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final HistoryErrorCodeEntity entity) {

    }

}
