package com.slb.ttdandroidframework.weight.hellocharts.listener;


import com.slb.ttdandroidframework.weight.hellocharts.model.SliceValue;

public interface PieChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int arcIndex, SliceValue value);

}
