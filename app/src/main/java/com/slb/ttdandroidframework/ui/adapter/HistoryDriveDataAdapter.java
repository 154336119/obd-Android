package com.slb.ttdandroidframework.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.HistoryDriveDataEntity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;


public class HistoryDriveDataAdapter extends BaseQuickAdapter<HistoryDriveDataEntity,BaseViewHolder> {


    public HistoryDriveDataAdapter(List<HistoryDriveDataEntity> data) {
        super(R.layout.adapter_drive_data, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final HistoryDriveDataEntity entity) {
        LineChartView lineChartView = (LineChartView)baseViewHolder.getView(R.id.LineChartView);
        generateValues();
        generateData(lineChartView);
        resetViewport(lineChartView);
    }


    private LineChartData data;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;
    private int numberOfLines = 1;
    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private void generateData(LineChartView chart) {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < 1; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < 5; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(ValueShape.CIRCLE);
            line.setCubic(false);
            line.setFilled(true);
            line.setHasLabels(false);
            line.setHasLabelsOnlyForSelected(false);
            line.setHasLines(false);
            line.setHasPoints(false);
            line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            lines.add(line);
        }

        data = new LineChartData(lines);

        if (true) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(false);
//            if (true) {
//                axisX.setName("5.22");
//                axisY.setName("Axis Y");
//            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);
    }

    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random()* 10f;
            }
        }
    }
    private void resetViewport(LineChartView chart) {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 15;
        v.left = 0;
        v.right = numberOfPoints - 1;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }
}
