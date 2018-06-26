package com.slb.ttdandroidframework.ui.adapter;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;

import java.util.List;


public class FreezeFrameAdapter extends BaseQuickAdapter<FreezeFrameEntity,BaseViewHolder> {


    public FreezeFrameAdapter(List<FreezeFrameEntity> data) {
        super(R.layout.adapter_freeze_frame, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final FreezeFrameEntity entity) {


    }

}
