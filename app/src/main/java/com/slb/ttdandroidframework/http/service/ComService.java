package com.slb.ttdandroidframework.http.service;

import com.slb.frame.http2.retrofit.HttpResult;
import com.slb.ttdandroidframework.http.bean.UserEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ComService {


    /**
     * 用户-注册
     */
    @FormUrlEncoded
    @POST("regist")
    Observable<HttpResult<Object, Object>> regist(@Field("email") String email ,
                                                      @Field("password ") String password ,
                                                      @Field("verifyCode  ") String verifyCode );

    /**
     * 用户-获取邮箱注册验证码
     */
    @FormUrlEncoded
    @POST("regist/verifycode")
    Observable<HttpResult<Object, Object>> registVerifycode(@Field("email") String email);

    /**
     * 用户-登录
     */
    @FormUrlEncoded
    @POST("ttd-fund-user/userInfo/managerAppLogin")
    Observable<HttpResult<UserEntity, Object>> login(@Field("loginName") String loginName,
                                                     @Field("password") String password);

    /**
     * 用户-发短信验证码
     */
    @FormUrlEncoded
    @POST("ttd-fund-user/organizationStructure/sendValidationSms")
    Observable<HttpResult<Object, Object>> sendMsgCode(@Field("userEmail") String userEmail);

}
