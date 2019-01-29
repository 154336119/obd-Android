package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ChoiseCarNumEntity;
import com.slb.ttdandroidframework.http.bean.ChoiseObdEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;

import java.util.List;


public class HisroryChoiseCarNumAdapter extends BaseQuickAdapter<VehicleEntity,BaseViewHolder> {
    private int selPosition=0;

    public HisroryChoiseCarNumAdapter(List<VehicleEntity> data) {
        //有点问题 先注释
        super(R.layout.adapter_history_choise, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final VehicleEntity entity) {
        baseViewHolder.setText(R.id.Rb,entity.getName());
        if (selPosition==baseViewHolder.getLayoutPosition())
        {
            baseViewHolder.setChecked(R.id.Rb,true);
            baseViewHolder.setTextColor(R.id.Rb,R.color.color_049CE2);
        }else
        {
            baseViewHolder.setChecked(R.id.Rb,false);
            baseViewHolder.setTextColor(R.id.Rb,R.color.color_636363);
        }
////        if(entity.getBulletinTotalPeople()!=null){
////            if(entity.getBulletinReadTotalPeople()!=null){
////                int num = entity.getBulletinTotalPeople() - entity.getBulletinReadTotalPeople();
////                baseViewHolder.setText(R.id.TvUnReadCount,"未查看("+num+")人");
////            }else{
////                baseViewHolder.setText(R.id.TvUnReadCount,"未查看("+entity.getBulletinTotalPeople()+")人");
////
////            }
////        }
    }
    public   void setItemSel(int selPosition)
    {
        this.selPosition=selPosition;
        notifyDataSetChanged();
    }
}
