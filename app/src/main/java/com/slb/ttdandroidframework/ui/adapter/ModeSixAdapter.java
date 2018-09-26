package com.slb.ttdandroidframework.ui.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.http.bean.ModeSixEntity;
import com.slb.ttdandroidframework.weight.MyListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModeSixAdapter extends BaseQuickAdapter<ModeSixEntity,BaseViewHolder> {
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    public ModeSixAdapter(List<ModeSixEntity> data) {
        super(R.layout.adapter_mode_six, data);
    }

    @Override
    public void setNewData(List<ModeSixEntity> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ModeSixEntity entity) {
        MyListView myListView = baseViewHolder.getView(R.id.MyListView);
        FreezeFrameMyListAdapter myListAdapter = new FreezeFrameMyListAdapter(mContext);
        CheckBox cb = (CheckBox)baseViewHolder.getView(R.id.ck);
        cb.setTag(mData.indexOf(entity));
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                int pos = (int) buttonView.getTag();
                if(isChecked){
                    baseViewHolder.setVisible(R.id.Rl01,true);
                    mCheckStates.put(pos, true);
                }else{
                    baseViewHolder.setVisible(R.id.Rl01,false);
                    mCheckStates.delete(pos);
                }
            }
        });
        cb.setChecked(mCheckStates.get(mData.indexOf(entity), false));
        if(!TextUtils.isEmpty(entity.getTid())){
            baseViewHolder.setText(R.id.TvTID,"TID:"+entity.getTid());
        }
        if(!TextUtils.isEmpty(entity.getCid())){
            baseViewHolder.setText(R.id.TvCID,"CID:"+entity.getCid());
        }
        if(!TextUtils.isEmpty(entity.getMax())){
            baseViewHolder.setText(R.id.TvMax,"最大值:"+entity.getMax());
        }
        if(!TextUtils.isEmpty(entity.getMin())){
            baseViewHolder.setText(R.id.TvMin,"最小值:"+entity.getMin());
        }
        if(!TextUtils.isEmpty(entity.getValue())){
            baseViewHolder.setText(R.id.TvValue,"测试值:"+entity.getValue());
        }
        if(entity.isState()){
            baseViewHolder.setTextColor(R.id.TvState, Color.GREEN);
            baseViewHolder.setText(R.id.TvState,"成功");
        }else{
            baseViewHolder.setTextColor(R.id.TvState,Color.RED);
            baseViewHolder.setText(R.id.TvState,"失败");
        }
    }
}
