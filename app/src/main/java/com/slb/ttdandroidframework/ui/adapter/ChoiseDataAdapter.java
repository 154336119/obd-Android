package com.slb.ttdandroidframework.ui.adapter;

import android.util.SparseArray;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.RxBus;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ChoiseComEvent;
import com.slb.ttdandroidframework.http.bean.DataEntity;
import com.slb.ttdandroidframework.util.config.ObdConfig;

import java.util.ArrayList;
import java.util.List;


public class ChoiseDataAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public SparseArray<String> getmSparseArray() {
        return mSparseArray;
    }

    public void setmSparseArray(SparseArray<String> mSparseArray) {
        this.mSparseArray = mSparseArray;
    }

    private SparseArray<String> mSparseArray =new SparseArray<>();

    public ChoiseDataAdapter(List<String> data) {
        super(R.layout.adapter_choise_data, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final String entity) {
        baseViewHolder.setText(R.id.TvDes,entity);
        baseViewHolder.setChecked(R.id.CheckBox,(mSparseArray.get( mData.indexOf(entity))==null? false : true));
        baseViewHolder.setOnCheckedChangeListener(R.id.CheckBox, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    mSparseArray.put(mData.indexOf(entity),entity);
                    RxBus.get().post(new ChoiseComEvent(entity,true));
                }else{
                    mSparseArray.remove(mData.indexOf(entity));
                    RxBus.get().post(new ChoiseComEvent(entity,false));
                }

            }
        });
    }

}
