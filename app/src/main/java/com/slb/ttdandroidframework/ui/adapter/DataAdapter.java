package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.DataEntity;

import java.util.List;


public class DataAdapter extends BaseQuickAdapter<DataEntity,BaseViewHolder> {

    public boolean isChoiseData() {
        return isChoiseData;
    }

    public void setChoiseData(boolean choiseData) {
        isChoiseData = choiseData;
    }

    public boolean isChoiseData;

    public DataAdapter(List<DataEntity> data) {
        super(R.layout.adapter_data, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final DataEntity entity) {
        if(isChoiseData){
            baseViewHolder.setText(R.id.TvDes,entity.getTitle());
        }else{
            baseViewHolder.setText(R.id.TvDes,entity.getTitle());
            baseViewHolder.setText(R.id.TvData,entity.getValue());
        }
    }

}
