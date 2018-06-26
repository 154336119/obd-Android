package com.slb.ttdandroidframework.ui.adapter;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.EmissionTestEntity;
import com.slb.ttdandroidframework.http.bean.EmissionTestSmallEntity;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;
import com.slb.ttdandroidframework.weight.MyListView;

import java.util.ArrayList;
import java.util.List;


public class EmissionTestAdapter extends BaseQuickAdapter<EmissionTestEntity,BaseViewHolder> {


    public EmissionTestAdapter(List<EmissionTestEntity> data) {
        super(R.layout.adapter_emission_test, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final EmissionTestEntity entity) {
        MyListView myListView = baseViewHolder.getView(R.id.MyListView);
        EmissionTestMyListAdapter myListAdapter = new EmissionTestMyListAdapter(mContext);
//        if(!TextUtils.isEmpty(entity.getApplicant())){
//            baseViewHolder.setText(R.id.TvName,entity.getApplicant());
//        }
        List<EmissionTestSmallEntity> newList = new ArrayList<>();
        //测试
        for(int i=0;i<3;i++){
            EmissionTestSmallEntity emissionTestSmallEntity = new EmissionTestSmallEntity();
            newList.add(emissionTestSmallEntity);
        }
        myListAdapter.setList(newList);
        myListView.setAdapter(myListAdapter);
    }

}
