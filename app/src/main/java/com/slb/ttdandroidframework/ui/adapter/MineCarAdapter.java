package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.DataEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;

import java.util.List;


public class MineCarAdapter extends BaseQuickAdapter<VehicleEntity,BaseViewHolder> {


    public MineCarAdapter(List<VehicleEntity> data) {
        super(R.layout.adapter_mine_car_list, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final VehicleEntity entity) {
        baseViewHolder.setText(R.id.TvName,entity.getPickerViewText());
    }

}
