package com.slb.ttdandroidframework;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.slb.frame.http2.retrofit.HttpLoggingInterceptor;
import com.slb.frame.utils.AppConfig;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.ui.SharedPreferencesUtil;
import com.slb.ttdandroidframework.ui.activity.DeviceActivity;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.slb.ttdandroidframework.util.io.AbstractGatewayService;
import com.slb.ttdandroidframework.util.io.ObdGatewayService;
import com.tencent.bugly.Bugly;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

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
        initOkGo();
        Bugly.init(getApplicationContext(), "60be1128c4", false);
        SharedPreferencesUtil.getInstance(this, BizcContant.SP_OBD);
    }


    private void initLogUtils(){
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.addLogAdapter(new DiskLogAdapter());
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
    private void initOkGo() {
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失


        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }

    public static AbstractGatewayService getService() {
        return service;
    }

    public static AbstractGatewayService service;

    public static ServiceConnection getServiceConn() {
        return serviceConn;
    }

    public static ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            service = ((AbstractGatewayService.AbstractGatewayServiceBinder) binder).getService();
            service.setContext(Base.getContext());
            try {
                service.startService();
            } catch (IOException ioe) {
//                btStatusTextView.setText(getString(R.string.status_bluetooth_error_connecting));
//                doUnbindService();
            }
        }
        @Override
        protected Object clone() throws CloneNotSupportedException {return super.clone();}
        @Override
        public void onServiceDisconnected(ComponentName className) {}
    };

}
