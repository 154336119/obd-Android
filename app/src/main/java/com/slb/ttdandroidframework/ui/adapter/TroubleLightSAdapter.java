package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.http.bean.TroubleLightSEntity;

import java.util.List;

/**
 * 故障灯
 */
public class TroubleLightSAdapter extends BaseQuickAdapter<TroubleLightSEntity,BaseViewHolder> {


    public TroubleLightSAdapter(List<TroubleLightSEntity> data) {
        super(R.layout.adapter_trouble_lights, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final TroubleLightSEntity entity) {
        baseViewHolder.setText(R.id.TvTitle,entity.getName());
        baseViewHolder.setText(R.id.TvContent,entity.getValue());
    }

}
