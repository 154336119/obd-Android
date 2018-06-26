package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.http.bean.MoudleFiveEntity;

import java.util.List;


public class ModuleFiveAdapter extends BaseQuickAdapter<MoudleFiveEntity,BaseViewHolder> {


    public ModuleFiveAdapter(List<MoudleFiveEntity> data) {
        super(R.layout.adapter_module_five, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final MoudleFiveEntity entity) {


    }

}
