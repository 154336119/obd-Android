package com.slb.ttdandroidframework.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.HistoryDriveDataEntity;
import com.slb.ttdandroidframework.weight.hellocharts.formatter.LineChartValueFormatter;
import com.slb.ttdandroidframework.weight.hellocharts.formatter.SimpleLineChartValueFormatter;
import com.slb.ttdandroidframework.weight.hellocharts.gesture.ContainerScrollType;
import com.slb.ttdandroidframework.weight.hellocharts.model.Axis;
import com.slb.ttdandroidframework.weight.hellocharts.model.AxisValue;
import com.slb.ttdandroidframework.weight.hellocharts.model.Line;
import com.slb.ttdandroidframework.weight.hellocharts.model.LineChartData;
import com.slb.ttdandroidframework.weight.hellocharts.model.PointValue;
import com.slb.ttdandroidframework.weight.hellocharts.model.ValueShape;
import com.slb.ttdandroidframework.weight.hellocharts.model.Viewport;
import com.slb.ttdandroidframework.weight.hellocharts.view.LineChartView;

import java.util.ArrayList;
import java.util.List;


public class HistoryDriveDataAdapter extends BaseQuickAdapter<HistoryDriveDataEntity,BaseViewHolder> {


    public HistoryDriveDataAdapter(List<HistoryDriveDataEntity> data) {
        super(R.layout.adapter_drive_data, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final HistoryDriveDataEntity entity) {
        List<DateForLine> list1 = new ArrayList<>();
        //测试
        list1.add(new DateForLine("201703040230","3.5"));
        list1.add(new DateForLine("201703040330","10.6"));
        list1.add(new DateForLine("201703040430","-20"));
        list1.add(new DateForLine("201703040530","-6"));
        list1.add(new DateForLine("201703040630","5.5"));
        list1.add(new DateForLine("201703040730","-10.5"));
        list1.add(new DateForLine("201703040830","-8.7"));
        list1.add(new DateForLine("201703040930","5.8"));
        list1.add(new DateForLine("201703041030","20"));
        LineChartView lineChartView = (LineChartView)baseViewHolder.getView(R.id.LineChartView);
        setKLine(1 ,list1,lineChartView);
    }

    public void setKLine(int type,List<DateForLine> list ,LineChartView chartView){
        //    折线集合（add多个line就会显示对条线）
        List chartlines = new ArrayList();
        //    横坐标集合，可以设置标注名称，就是x轴的值集合，可以是0-100，也可以是10000-20000
        List mAxisXValues = new ArrayList();
        //    点的集合，顾名思义一个point，就有他所对应的x y值，假如有个点的值是（5,100）x=5 y=100
        List pointValues = new ArrayList();
        //    折线，多条折线可以new多个线，要显示谁，就在line集合里add谁
        Line chartline = new Line();
        //    线上的数据，鸡肋但必须得用
        LineChartData lineChartData = new LineChartData();
        //    定义格式，小数点等等信息
        LineChartValueFormatter chartValueFormatter = new SimpleLineChartValueFormatter(2);
        //    X轴、Y轴
        Axis axisX = new Axis();
        Axis axisY = new Axis();
//        清空数据，将不影响下一次点击按钮 传过来一个新的list的显示
        chartlines.clear();
        mAxisXValues.clear();
        pointValues.clear();
        /*
         * 这个循环是循环为x轴（x底端轴线）增加值应该只能是float类型的，当然int可以
         */
        for(DateForLine date:list){
//            第一进来的值假如是“201703041230”，处理完之后应该是4*24+12
            String time=date.getTime();
            AxisValue axisValue=new AxisValue(parseHour2Ten(time));
//            这句话就关键了，你可以随意设置这个位置显示的东西，string类型的随意来
//            我这边想设置，几月几日几时
            axisValue.setLabel(subTimeDay(time)+"日"+subTimeHour(time)+"时");
            mAxisXValues.add(axisValue);
        }
//        x轴完事了，该布点了
        for(DateForLine date:list){
//            第一点的坐标假如是（4*24+12，25.5）  那么pointValue的参数就应该是这个
            String time=date.getTime();
            PointValue pointValue=new PointValue(parseHour2Ten(time),Float.parseFloat(date.getValue()));
//            这个点的标注信息，String的随便来
            if(type==1){
                pointValue.setLabel(date.getValue()+"℃");
//                温度显示小数
                chartline.setFormatter(chartValueFormatter);
            }else if(type==2){
                pointValue.setLabel(date.getValue()+"%PH");
//                温度显示整数就不用设置Formatter了
            }
//            把这个点添加到集合里去,等会显示用
            pointValues.add(pointValue);
        }
//        把点的集合放在线上，显示多条线 就用不同的line分别添加不同的values
        chartline.setValues(pointValues);
        chartline.setValues(pointValues);
        chartline.setShape(ValueShape.CIRCLE);
        chartline.setCubic(true);
        chartline.setFilled(true);
        chartline.setHasLabels(false);
        chartline.setHasLabelsOnlyForSelected(false);
        chartline.setHasLines(true);
        chartline.setHasPoints(true);
        chartline.setPointColor(Color.parseColor("#33B5E5"));
        chartlines.add(chartline);
//        显示几条线，就可以分别设置每一条线，然后add进来
        chartlines.add(chartline);
//        线的集合放在chart数据中，
        lineChartData.setLines(chartlines);

/*        x轴上面的值，就是刚才mAxisXValues的值
          5个参数分别是：
          1-例如4*24+12 显示为4日12时
          2-是否有x轴网格线
          3-x轴信息标注颜色是黑色
          4-x轴网格线白色
          5-x轴信息标注字体12
          6-x轴的大名
          Y轴也是这么设置，当你想点击不同的按钮，显示不同的信息时可用type进行处理
*/
        axisX.setValues(mAxisXValues)
                .setHasLines(false)
                .setTextColor(Color.parseColor("#00A4EF"))
                .setTextSize(12)
                .setInside(true);
        axisY.setHasLines(false)
                .setTextColor(Color.parseColor("#00A4EF"))
                .setTextSize(12);


//        X轴上的标注数量,点少的时候可以这么用，点多的时候，就不建议这么用了
//        axisX.setMaxLabelChars(8);
//        x 轴在底部
        lineChartData.setAxisXBottom(axisX);
//        x 轴在顶部
//        lineChartData.setAxisXTop(axisX);
//        y 轴在左，也可以右边
        lineChartData.setAxisYLeft(axisY);

//        这两句话设置折线上点的背景颜色，默认是有一个小方块，而且背景色和点的颜色一样
//        如果想要原来的效果可以不用这两句话，我的显示的是透明的
        lineChartData.setValueLabelBackgroundColor(Color.TRANSPARENT);
        lineChartData.setValueLabelBackgroundEnabled(false);


//        把数据放在chart里，设置完这句话其实就可以显示了
        chartView.setLineChartData(lineChartData);


//        设置行为属性，支持缩放、滑动以及平移，设置他就可以自己设置动作了
        chartView.setInteractive(true);
//        可放大
        chartView.setZoomEnabled(true);
//        我这边设置横向滚动
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
//        设置可视化视图样式，这里能做的东西非常多，
        final Viewport v = new Viewport(chartView.getMaximumViewport());
//        我设置两种。点击不同按钮时，y轴固定最大值最小值不一样
//        这里可以固定x轴，让y轴变化，也可以x轴y轴都固定，也就是固定显示在你设定的区间里的点point（x，y）
        if(type==1){
            v.top=50;
            v.bottom=-50;
        }else if(type==2){
            v.top=100;
            v.bottom=0;
        }
//        这句话非常关键，上面两种设置，来确定最大可视化样式
//        我们可以理解为，所有点放在linechart时，整个视图全看到时候的样子，也就是点很多很多，距离很紧密
        chartView.setMaximumViewport(v);
//        接着我们要设置，我们打开这个页面时显示的样子
//        如果你想所有，这两句话就不用了
//        但是我只显示5个点以内 刚才插入的点应该是...（4*24+8，-15.5）（4*24+9，-15.5）（4*24+10，-3.5）（4*24+11，20.0）（4*24+12，8.5）
//        x轴最右边就应该是x=4*24+12 的点 最左边就应该是x=4*24+8的点
//        当然这个非常灵活，也可以固定显示y轴 最小多少，最大多少
        v.left=parseHour2Ten(list.get(list.size()-5).getTime());
        v.right =parseHour2Ten(list.get(list.size()-1).getTime());
//        确定上两句话的设置
        chartView.setCurrentViewport(v);

//        还可以设置当前的动画效果，有兴趣的同学可以试一试
//        chartView.setViewportAnimationListener(new ChartAnimationListener() {
//            @Override
//            public void onAnimationStarted() {
//            }
//            @Override
//            public void onAnimationFinished() {
//                chartView.setMaximumViewport(v);
//                chartView.setViewportAnimationListener(null);
//
//            }
//        });
//        chartView.setCurrentViewport(v);

    }

    public class DateForLine {
        private String time;
        private String value;

        public DateForLine(String time, String value) {
            this.time = time;
            this.value = value;
        }

        public DateForLine() {
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    //    把我得到的时间格式处理成小时
    public String subTimeMonth(String time){
        return time.substring(4,6);
    }
    public String subTimeDay(String time){
        return time.substring(6,8);
    }
    public String subTimeHour(String time){
        return time.substring(8,10);
    }
    /*
     *假如回来的时间是"201703151955"  2017年3月19日19:55
     * 按照我的需求只要24小时内的值
     * 在x轴上这个时间的值应该是15*24+19
     */
    public int parseHour2Ten(String time){
        return Integer.parseInt(time.substring(6,8))*24+
                Integer.parseInt(time.substring(8,10));
    }
}
