package com.slb.ttdandroidframework;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.slb.frame.utils.AppConfig;
import com.slb.ttdandroidframework.http.bean.UserEntity;

/**
 * 刁剑
 * 2017/11/1
 * 注释:
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Base.initialize(this);
        initLogUtils();
        initOkgo();
        initOkgo();
    }


    private void initLogUtils(){
        Logger.init("OBD")
                .hideThreadInfo()
                .logLevel(LogLevel.FULL)
                .methodOffset(2);
    }
    private void initOkgo() {
        OkGo.init(this);
        OkGo.getInstance();
    }

}
