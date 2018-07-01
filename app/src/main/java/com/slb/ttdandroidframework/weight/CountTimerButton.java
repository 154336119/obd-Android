package com.slb.ttdandroidframework.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 刁剑
 * Created on 2017/11/15
 * 注释:
 */

@SuppressLint("AppCompatCustomView")
public class CountTimerButton extends TextView {
    private CountDownTimerUtils countDownTimerUtils;
    public CountTimerButton(Context context) {
        this(context,null);
    }

    public CountTimerButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        countDownTimerUtils=new CountDownTimerUtils(this,60*1000,1000);
    }
    public void startCountTimer(CountDownTimer countDownTimer){
//        countDownTimerUtils.start();
        countDownTimer.start();
    }
    public void startCountTimer(){
        countDownTimerUtils=new CountDownTimerUtils(this,60*1000,1000);
        countDownTimerUtils.start();
    }

    public void startCountTimer(String strTimeOutText){
        countDownTimerUtils=new CountDownTimerUtils(this,60*1000,1000);
        countDownTimerUtils.setTimeOutText(strTimeOutText);
        countDownTimerUtils.start();
    }
}
