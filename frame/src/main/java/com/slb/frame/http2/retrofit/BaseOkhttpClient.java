package com.slb.frame.http2.retrofit;


import com.slb.frame.Base;
import com.slb.frame.utils.AppUtils;
import com.slb.frame.utils.DeviceUtils;
import com.slb.frame.utils.security.Security;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述：暂时没有使用 ===》需要app module
 * Created by Lee
 * on 2016/9/19.
 */
public class BaseOkhttpClient {
    private  OkHttpClient.Builder mClientBuilder;
    private SingInterceptor mSingInterceptor;
    private HttpLoggingInterceptor mHttpLoggingInterceptor;
    private static BaseOkhttpClient mInstance;
    private AppUtils.AppInfo mAppInfo;
    /**
     * 单例
     * @return
     */
    public static synchronized BaseOkhttpClient getInstance(AppUtils.AppInfo appInfo){
        if(mInstance==null){
            mInstance = new BaseOkhttpClient(appInfo);
        }
        return mInstance;
    }

    public OkHttpClient.Builder getClientBuilder() {
        return mClientBuilder;
    }

    public BaseOkhttpClient(AppUtils.AppInfo appInfo) {
        mAppInfo = appInfo;
        initInterceptor();
        mClientBuilder = new OkHttpClient.Builder();
        mClientBuilder.addInterceptor(mSingInterceptor)
                .addInterceptor(mHttpLoggingInterceptor);

    }

    /**
     * 初始化拦截器
     */
    private void initInterceptor(){
        mSingInterceptor = new SingInterceptor();
        mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    }

    /**
     * request拦截器 - 统一传递参数的接口
     */
    class SingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            long time = System.currentTimeMillis();
            HttpUrl httpUrl = oldRequest.url().newBuilder()
                    .host(oldRequest.url().host())
                    .scheme(oldRequest.url().scheme())
                    .addQueryParameter("mac", DeviceUtils.getMacAddress(Base.getContext()))
                    .addQueryParameter("os", "1")
                    .addQueryParameter("version", mAppInfo.getVersionName())
                    .addQueryParameter("timestamp",time+"")
                    .addQueryParameter("access_token", Security.getMD5Value(time)).build();
            /** 添加公共参数后的request*/
            Request modifiedRequest = oldRequest.newBuilder()
                    .url(httpUrl)
                    .method(oldRequest.method(),oldRequest.body())
                    .build();
            Response response = chain.proceed(modifiedRequest);
            return response;
        }
    }

}
