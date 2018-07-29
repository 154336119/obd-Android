package com.slb.ttdandroidframework.http.service;

import com.slb.frame.http2.retrofit.HttpResult;
import com.slb.ttdandroidframework.http.bean.UserEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public class ComServiceUrl {
    /**
     * 用户-注册
     */
    public static String regist = "regist";

    /**
     * 用户-获取邮箱注册验证码
     */
    public static String verifycode = "regist/verifycode";

    /**
     * 用户-登录
     */
    public static String login = "login";

    /**
     * 用户-获取用户信息
     */
    public static String getUserInfo = "api/user";

    /**
     * 用户-获取重置密码邮箱验证码
     */
    public static String verifycodeReset= "regist/verifycode/reset";

    /**
     * 用户-用户重置密码
     */
    public static String registReset = "regist/reset";

//    /**
//     * 用户-获取邮箱注册验证码
//     */
//    @FormUrlEncoded
//    @POST("regist/verifycode")
//    Observable<HttpResult<Object, Object>> registVerifycode(@Field("email") String email);
//
//    /**
//     * 用户-登录
//     */
//    @FormUrlEncoded
//    @POST("ttd-fund-user/userInfo/managerAppLogin")
//    Observable<HttpResult<UserEntity, Object>> login(@Field("loginName") String loginName,
//                                                     @Field("password") String password);
//
//    /**
//     * 用户-发短信验证码
//     */
//    @FormUrlEncoded
//    @POST("ttd-fund-user/organizationStructure/sendValidationSms")
//    Observable<HttpResult<Object, Object>> sendMsgCode(@Field("userEmail") String userEmail);

}
