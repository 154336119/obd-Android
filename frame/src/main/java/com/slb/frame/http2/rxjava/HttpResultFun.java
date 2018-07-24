package com.slb.frame.http2.rxjava;

import com.slb.frame.http2.exception.ResultException;
import com.slb.frame.http2.retrofit.HttpDataResutl;
import com.slb.frame.http2.retrofit.HttpResult;

import rx.functions.Func1;

/**
 * 描述：服务器统一请求返回-预处理
 * Created by Lee
 * on 2016/10/13.
 */
public class HttpResultFun<T,A> implements Func1<HttpResult<T,A>,HttpDataResutl<T,A>> {
    @Override
    public HttpDataResutl<T, A> call(HttpResult<T, A> taHttpResult) {
        if(taHttpResult.getStatus() != 0){
            throw new ResultException(taHttpResult.getStatus(),taHttpResult.getMessage());
        }
        return taHttpResult.getResult();
    }
}
