package com.slb.ttdandroidframework.weight.hellocharts.listener;


import com.slb.ttdandroidframework.weight.hellocharts.model.PointValue;

public interface LineChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int lineIndex, int pointIndex, PointValue value);

}
