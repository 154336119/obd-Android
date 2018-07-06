package com.slb.ttdandroidframework.weight.hellocharts.listener;


import com.slb.ttdandroidframework.weight.hellocharts.model.SubcolumnValue;

public interface ColumnChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value);

}
