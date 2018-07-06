package com.slb.ttdandroidframework.weight.hellocharts.listener;


import com.slb.ttdandroidframework.weight.hellocharts.model.BubbleValue;

public interface BubbleChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int bubbleIndex, BubbleValue value);

}
