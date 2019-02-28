package com.slb.ttdandroidframework.ui.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.utils.DateUtils;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.FreezeFrameEntity;
import com.slb.ttdandroidframework.http.bean.ModeSixDesEntity;
import com.slb.ttdandroidframework.http.bean.ModeSixEntity;
import com.slb.ttdandroidframework.http.callback.ActivityDialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.weight.MyListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModeSixAdapter extends BaseQuickAdapter<ModeSixEntity,BaseViewHolder> {
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    public BaseActivity baseActivity;
    public ModeSixAdapter(List<ModeSixEntity> data, BaseActivity baseActivity) {
        super(R.layout.adapter_mode_six, data);
        this.baseActivity = baseActivity;
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
                    baseViewHolder.setVisible(R.id.llDetail,true);
                    mCheckStates.put(pos, true);
                    getDes(baseViewHolder,entity);
                }else{
                    baseViewHolder.setVisible(R.id.llDetail,false);
                    mCheckStates.delete(pos);
                }
            }
        });
        cb.setChecked(mCheckStates.get(mData.indexOf(entity), false));
        if(!TextUtils.isEmpty(entity.getMid())){
            baseViewHolder.setText(R.id.TvMID,"MID:"+entity.getMid());
        }
        if(!TextUtils.isEmpty(entity.getTid())){
            baseViewHolder.setText(R.id.TvTID,"TID:"+entity.getTid());
        }
        if(!TextUtils.isEmpty(entity.getMax())){
            baseViewHolder.setText(R.id.TvMax,mContext.getString(R.string.max)+entity.getMax() +entity.getUnit());
        }else{
            baseViewHolder.setText(R.id.TvMax,mContext.getString(R.string.max)+mContext.getString(R.string.nothing));
        }
        if(!TextUtils.isEmpty(entity.getMin())){
            baseViewHolder.setText(R.id.TvMin,mContext.getString(R.string.min)+entity.getMin() +entity.getUnit());
        }else{
            baseViewHolder.setText(R.id.TvMin,mContext.getString(R.string.min)+mContext.getString(R.string.nothing));
        }
        if(!TextUtils.isEmpty(entity.getValue())){
//            if(entity.isState()){
//                baseViewHolder.setTextColor(R.id.TvValue,R.color.white);
//            }else{
//                baseViewHolder.setTextColor(R.id.TvValue,R.color.google_red);
//            }
            baseViewHolder.setText(R.id.TvValue,mContext.getString(R.string.values)+entity.getValue()+entity.getUnit());
        }else{
            baseViewHolder.setText(R.id.TvValue,mContext.getString(R.string.values)+mContext.getString(R.string.nothing));
        }


        if(entity.isState()){
            baseViewHolder.setTextColor(R.id.TvState, Color.GREEN);
            baseViewHolder.setText(R.id.TvState,mContext.getString(R.string.normal));
        }else{
            baseViewHolder.setTextColor(R.id.TvState,Color.RED);
            baseViewHolder.setText(R.id.TvState,mContext.getString(R.string.failed));
        }
    }
    private void getDes(final BaseViewHolder baseViewHolder,final ModeSixEntity entity){
        OkGo.<LzyResponse<ModeSixDesEntity>>get(DnsFactory.getInstance().getDns().getCommonBaseUrl()+"api/command/model/"+entity.getMid()+"/"+entity.getTid())
                .tag(this)
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new ActivityDialogCallback<LzyResponse<ModeSixDesEntity>>(baseActivity) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ModeSixDesEntity>> response) {
                        if(response!=null && response.body().data!=null &&!TextUtils.isEmpty(response.body().data.getDescription())){
                            Logger.d("===========================:"+response.body().data.getDescription());
                            entity.setDes(response.body().data.getDescription());
                            baseViewHolder.setText(R.id.TvDes,entity.getDes());
                        }
                    }
                });
    }
}
