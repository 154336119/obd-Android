package com.slb.ttdandroidframework.ui.adapter;

import android.util.SparseArray;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.RxBus;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.DataEntity;

import java.util.ArrayList;
import java.util.List;


public class ChoiseDataAdapter extends BaseQuickAdapter<DataEntity,BaseViewHolder> {
    public SparseArray<DataEntity> getmSparseArray() {
        return mSparseArray;
    }

    private SparseArray<DataEntity> mSparseArray;

    public ChoiseDataAdapter(List<DataEntity> data) {
        super(R.layout.adapter_choise_data, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final DataEntity entity) {
        baseViewHolder.setText(R.id.TvDes,entity.getTitle());
        baseViewHolder.setOnCheckedChangeListener(R.id.CheckBox, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    mSparseArray.put(mData.indexOf(entity),entity);
                    System.out.println("复选框以选中，选中的行数为：" + mData.indexOf(entity));
                    entity.setCheck(true);
                }else{
                    mSparseArray.remove(mData.indexOf(entity));
                    entity.setCheck(false);
                }

            }
        });
        baseViewHolder.setChecked(R.id.CheckBox,(mSparseArray.get( mData.indexOf(entity))==null? false : true));
    }

}
