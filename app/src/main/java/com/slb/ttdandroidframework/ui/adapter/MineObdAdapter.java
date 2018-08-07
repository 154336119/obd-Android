package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;

import java.util.List;


public class MineObdAdapter extends BaseQuickAdapter<ObdEntity,BaseViewHolder> {


    public MineObdAdapter(List<ObdEntity> data) {
        super(R.layout.adapter_mine_car_list, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ObdEntity entity) {
        baseViewHolder.setText(R.id.TvName,entity.getPickerViewText());
    }

}
