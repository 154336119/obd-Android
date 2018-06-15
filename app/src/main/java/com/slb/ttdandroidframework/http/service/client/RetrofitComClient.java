package com.slb.ttdandroidframework.http.service.client;


import com.slb.frame.http2.retrofit.BaseRetrofitClient;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.http.okhttpclient.CodeOkhttpClient;

/**
 * 描述：用于APP请求的Client
 * Created by Lee
 * on 2016/9/20.
 */
public class RetrofitComClient extends BaseRetrofitClient {
    private static RetrofitComClient mInstance;
    public static RetrofitComClient getInstance(String baseUrl){
        if(mInstance == null){
            synchronized (RetrofitComClient.class){
                if(mInstance == null){
                    mInstance = new RetrofitComClient(baseUrl);
                }
            }
        }
        return mInstance;
    }

    public RetrofitComClient(String url) {
//        attchBaseUrl(url, Base.getContext(), MyOkhttpClient.getClientBuilder());
        attchBaseUrl(url, Base.getContext(), CodeOkhttpClient.getClientBuilder());
    }
}
