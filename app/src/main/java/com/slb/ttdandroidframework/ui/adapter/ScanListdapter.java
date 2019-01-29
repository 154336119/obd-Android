package com.slb.ttdandroidframework.ui.adapter;

import android.bluetooth.BluetoothDevice;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ObdEntity;

import java.util.List;


public class ScanListdapter extends BaseQuickAdapter<BluetoothDevice,BaseViewHolder> {


    public ScanListdapter(List<BluetoothDevice> data) {
        super(R.layout.adapter_mine_obd_list, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final BluetoothDevice entity) {
        baseViewHolder.setText(R.id.TvName,entity.getName());
    }

}
