package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.DataEntity;
import com.slb.ttdandroidframework.http.bean.EmissionTestEntity;
import com.slb.ttdandroidframework.http.bean.EmissionTestSmallEntity;
import com.slb.ttdandroidframework.weight.MyListView;

import java.util.ArrayList;
import java.util.List;


public class DataAdapter extends BaseQuickAdapter<DataEntity,BaseViewHolder> {


    public DataAdapter(List<DataEntity> data) {
        super(R.layout.adapter_data, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final DataEntity entity) {
        baseViewHolder.setText(R.id.TvData,entity.getTitle());
        baseViewHolder.setText(R.id.TvDes,entity.getTitle());
    }

}
