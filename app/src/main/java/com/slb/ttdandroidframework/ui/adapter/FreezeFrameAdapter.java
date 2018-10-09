package com.slb.ttdandroidframework.ui.adapter;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.weight.MyListView;

import java.util.List;


public class FreezeFrameAdapter extends BaseQuickAdapter<FreezeFrameEntity,BaseViewHolder> {


    public FreezeFrameAdapter(List<FreezeFrameEntity> data) {
        super(R.layout.adapter_freeze_frame, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final FreezeFrameEntity entity) {
        MyListView myListView = baseViewHolder.getView(R.id.MyListView);
        FreezeFrameMyListAdapter myListAdapter = new FreezeFrameMyListAdapter(mContext);
        CheckBox cb = (CheckBox)baseViewHolder.getView(R.id.ck);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    baseViewHolder.setVisible(R.id.llDetail,true);
                }else{
                    baseViewHolder.setVisible(R.id.llDetail,false);
                }
            }
        });

        if(!TextUtils.isEmpty(entity.getDes())){
            baseViewHolder.setText(R.id.TvDes,entity.getDes());
        }
        if(!TextUtils.isEmpty(entity.getPid())){
            baseViewHolder.setText(R.id.TvPID,entity.getPid());
        }

//        List<FreezeFrameInsideEntity> newList = new ArrayList<>();
//        //测试
//        for(int i=0;i<3;i++){
//            FreezeFrameInsideEntity emissionTestSmallEntity = new FreezeFrameInsideEntity();
//            newList.add(emissionTestSmallEntity);
//        }
        myListAdapter.setList(entity.getmInsideList());
        myListView.setAdapter(myListAdapter);

    }

}
