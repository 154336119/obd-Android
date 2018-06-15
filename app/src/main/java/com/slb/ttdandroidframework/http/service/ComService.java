package com.slb.ttdandroidframework.http.service;

import com.slb.frame.http2.retrofit.HttpResult;
import com.slb.ttdandroidframework.http.bean.UserEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ComService {
    /**
     * 用户-登录
     */
    @FormUrlEncoded
    @POST("ttd-fund-user/userInfo/managerAppLogin")
    Observable<HttpResult<UserEntity, Object>> login(@Field("loginName") String loginName,
                                                                @Field("password") String password);
}
