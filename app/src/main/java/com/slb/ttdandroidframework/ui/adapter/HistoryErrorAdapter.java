package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;

import java.util.List;


public class HistoryErrorAdapter extends BaseQuickAdapter<HistoryErrorCodeEntity,BaseViewHolder> {


    public HistoryErrorAdapter(List<HistoryErrorCodeEntity> data) {
            super(R.layout.adapter_history_error_code, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final HistoryErrorCodeEntity entity) {
        if(entity.getDtcs()!=null &&entity.getDtcs().size()>0){
            String num = String.format("%0" + 2 + "d", entity.getDtcs().size());
            baseViewHolder.setText(R.id.TvNum,"故障码"+num+"个");
            baseViewHolder.setText(R.id.TvCarNum,entity.getDtcs().get(0).getVehicleLicenseNo());
        }else{
            baseViewHolder.setText(R.id.TvNum,"暂无");
        }
        baseViewHolder.setText(R.id.TvDate,entity.getDatetime());
    }
}
