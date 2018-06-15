package com.slb.ttdandroidframework.http;

import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.service.ComService;
import com.slb.ttdandroidframework.http.service.client.RetrofitComClient;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on 2017/11/1.
 */

public class RetrofitSerciveFactory {
    private static ConcurrentMap<Class,Object> mService = new ConcurrentHashMap();

    /**
     * 提供ComService服务
     * @return
     */
    public static ComService provideComService(){
        return provideService(DnsFactory.getInstance().getDns().getCommonBaseUrl(),ComService.class);
    }

    /**
     *
     * @param baseUrl
     * @param clz
     * @param <T>
     * @return
     */
    private static  <T>T provideService(String baseUrl, Class clz){
        Object object = mService.get(clz);
        if(object == null){
            object = mService.get(clz);
            if(object==null){
                if(baseUrl.equals(DnsFactory.getInstance().getDns().getCommonBaseUrl())){
                    object = RetrofitComClient.getInstance(baseUrl).createService(ComService.class);
                }
            }
            mService.put(clz,object);
        }
        return (T)object;
    }
}
