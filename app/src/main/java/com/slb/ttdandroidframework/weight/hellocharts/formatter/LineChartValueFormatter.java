package com.slb.ttdandroidframework.weight.hellocharts.formatter;


import com.slb.ttdandroidframework.weight.hellocharts.model.PointValue;

public interface LineChartValueFormatter {

    public int formatChartValue(char[] formattedValue, PointValue value);
}
