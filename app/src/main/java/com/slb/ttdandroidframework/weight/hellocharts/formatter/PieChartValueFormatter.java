package com.slb.ttdandroidframework.weight.hellocharts.formatter;

import com.slb.ttdandroidframework.weight.hellocharts.model.SliceValue;

public interface PieChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SliceValue value);
}
