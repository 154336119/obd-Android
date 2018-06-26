package com.slb.ttdandroidframework.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ErrorCodeEntity;

import java.util.List;


public class ErrorCodeAdapter extends BaseQuickAdapter<ErrorCodeEntity,BaseViewHolder> {


    public ErrorCodeAdapter(List<ErrorCodeEntity> data) {
        super(R.layout.adapter_error_code, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ErrorCodeEntity entity) {
        CheckBox cb = (CheckBox)baseViewHolder.getView(R.id.ck);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    baseViewHolder.setVisible(R.id.TvContent,true);
                }else{
                    baseViewHolder.setVisible(R.id.TvContent,false);
                }
            }
        });
//        if(entity.getBulletinTotalPeople()!=null){
//            if(entity.getBulletinReadTotalPeople()!=null){
//                int num = entity.getBulletinTotalPeople() - entity.getBulletinReadTotalPeople();
//                baseViewHolder.setText(R.id.TvUnReadCount,"未查看("+num+")人");
//            }else{
//                baseViewHolder.setText(R.id.TvUnReadCount,"未查看("+entity.getBulletinTotalPeople()+")人");
//
//            }
//        }
    }

}
