package com.slb.ttdandroidframework.weight.hellocharts.formatter;

import com.slb.ttdandroidframework.weight.hellocharts.model.SubcolumnValue;

public interface ColumnChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SubcolumnValue value);

}
