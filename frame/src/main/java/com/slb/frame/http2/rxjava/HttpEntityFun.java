package com.slb.frame.http2.rxjava;

import com.slb.frame.http2.exception.ResultException;
import com.slb.frame.http2.retrofit.HttpResult;
import rx.functions.Func1;
/**
 * 描述：服务器统一请求返回bean-预处理
 * Created by Lee
 * on 2016/10/13.
 */
public class HttpEntityFun<T,A> implements Func1<HttpResult<T,A> ,T> {
    @Override
    public T call(HttpResult<T, A> taHttpResult) {
        if(taHttpResult.getCode() != 0){
           throw new ResultException(taHttpResult.getCode(),taHttpResult.getMsg());
        }
        return taHttpResult.getData().getBean();
    }
}
