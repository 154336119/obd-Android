package com.slb.ttdandroidframework.http.okhttpclient;

import android.text.TextUtils;


import com.slb.frame.http2.retrofit.HttpLoggingInterceptor;
import com.slb.frame.utils.security.Security;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.BuildConfig;


import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * 描述：
 * Created by Lee
 * on 2016/9/19.
 */
public class CodeOkhttpClient {
    private  static OkHttpClient.Builder mClientBuilder;
    private HttpLoggingInterceptor mHttpLoggingInterceptor;
    private static CodeOkhttpClient mInstance;
    private HeaderInterceptor mHeaderInterceptor;
    /**
     * 单例
     * @return
     */
    public static synchronized CodeOkhttpClient getInstance(){
        if(mInstance==null){
            mInstance = new CodeOkhttpClient();
        }
        return mInstance;
    }

    public static OkHttpClient.Builder getClientBuilder() {
        if(mInstance==null){
            mInstance = new CodeOkhttpClient();
        }
        String str = "dsad";
        return mClientBuilder;
    }

    public CodeOkhttpClient() {
        initInterceptor();
        mClientBuilder = new OkHttpClient.Builder();
        mClientBuilder.connectTimeout(60, TimeUnit.SECONDS);
        mClientBuilder.readTimeout(60, TimeUnit.SECONDS);
        mClientBuilder.writeTimeout(60, TimeUnit.SECONDS);
        mClientBuilder. addInterceptor(mHttpLoggingInterceptor)
        .addInterceptor(mHeaderInterceptor);
    }
    /**
     * 初始化拦截器
     */
    private void initInterceptor(){
        mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY
        );
//        mCodeInterceptor= new CodeInterceptor();
        mHeaderInterceptor=new HeaderInterceptor();
    }

    static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            String timestamp = String.format("%1$s-%2$d", UUID.randomUUID().toString(), System.currentTimeMillis());
            Request originalRequest = chain.request();
            Request compressedRequest = originalRequest.newBuilder()
                    .addHeader("os", "android")
                    .addHeader("version", BuildConfig.VERSION_NAME)
                    .addHeader("timestamp",timestamp)
                    .addHeader("access_token", Security.getMD5Value(timestamp))
                    .build();
            return chain.proceed(compressedRequest);
        }
    }
}
