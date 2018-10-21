package com.slb.ttdandroidframework.ui.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.BankSensorEntiity;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.http.bean.MoudleFiveEntity;
import com.slb.ttdandroidframework.weight.MyListView;

import java.util.List;


public class ModuleFiveAdapter extends BaseQuickAdapter<BankSensorEntiity,BaseViewHolder> {

    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    public ModuleFiveAdapter(List<BankSensorEntiity> data) {
        super(R.layout.adapter_module_five, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final BankSensorEntiity entity) {
        CheckBox cb = (CheckBox)baseViewHolder.getView(R.id.ck);
        MyListView myListView = baseViewHolder.getView(R.id.MyListView);
        ModeFiveMyListAdapter myListAdapter = new ModeFiveMyListAdapter(mContext);
        myListAdapter.setList(entity.getList());
        myListView.setAdapter(myListAdapter);
        cb.setTag(mData.indexOf(entity));
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                int pos = (int) buttonView.getTag();
                if(isChecked){
                    baseViewHolder.setVisible(R.id.MyListView,true);
                    mCheckStates.put(pos, true);
                }else{
                    baseViewHolder.setVisible(R.id.MyListView,false);
                    mCheckStates.delete(pos);
                }
            }
        });
        if(!TextUtils.isEmpty(entity.getBankSensorName())){
            baseViewHolder.setText(R.id.TvBankSensor,entity.getBankSensorName());
        }
        for(MoudleFiveEntity moudleFiveEntity : entity.getList()){
            if(!moudleFiveEntity.isState()){
                baseViewHolder.setTextColor(R.id.TvState,Color.RED);
                baseViewHolder.setText(R.id.TvState,"错误");
                return;
            }
        }
        baseViewHolder.setTextColor(R.id.TvState,Color.GREEN);
        baseViewHolder.setText(R.id.TvState,"正常");
    }

}
