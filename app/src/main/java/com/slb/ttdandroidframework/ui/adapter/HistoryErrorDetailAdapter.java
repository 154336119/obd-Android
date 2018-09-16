package com.slb.ttdandroidframework.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;

import java.util.List;


public class HistoryErrorDetailAdapter extends BaseQuickAdapter<ErrorCodeEntity,BaseViewHolder> {


    public HistoryErrorDetailAdapter(List<ErrorCodeEntity> data) {
        super(R.layout.adapter_history_error_code_detail, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ErrorCodeEntity entity) {
        baseViewHolder.setText(R.id.TvPid,entity.getTitle());
        if(!TextUtils.isEmpty(entity.getDes())){
            baseViewHolder.setText(R.id.TvDes,entity.getDes());
        }
    }
}
