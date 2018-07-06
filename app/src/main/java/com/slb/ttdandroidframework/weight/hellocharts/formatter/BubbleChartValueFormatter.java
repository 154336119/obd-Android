package com.slb.ttdandroidframework.weight.hellocharts.formatter;

import com.slb.ttdandroidframework.weight.hellocharts.model.BubbleValue;

public interface BubbleChartValueFormatter {

    public int formatChartValue(char[] formattedValue, BubbleValue value);
}
