package com.slb.ttdandroidframework;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONArray;
import com.slb.frame.cache.ACache;
import com.slb.ttdandroidframework.http.bean.UserEntity;


/**
 * 刁剑
 * 2017/11/1
 * 注释:
 */

public class Base {
    /** 测试环境*/
    public static final int DEBUG = 999;
    /** 预发布环境*/
    public static final int LIVE = 998;
    /** 线上环境*/
    public static final int RELEASE = 997;
    private static Context mContext;
    /** APP环境：debug、live、release */
    private static int mEnvironment;

    private static UserEntity mUserEntity;

    public static void  initialize(@NonNull Context context) {
        mContext = context;
    }

    public static Context getContext() {
        synchronized (Base.class){
            if (Base.mContext == null){
                throw new NullPointerException("Call Base.initialize(context) within your Application onCreate() method.");
            }
            return Base.mContext;
        }
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserEntity getUserEntity() {
        if(mUserEntity == null){
            if(ACache.get(getContext()).getAsObject("userEnity") !=null){
                return (UserEntity)ACache.get(getContext()).getAsObject("userEnity");
            }
        }
        return mUserEntity;
    }

    /**
     * 保存用户信息信息
     * @return
     */
    public static void setUserEntity(final UserEntity mUserEntity) {
        ACache.get(getContext()).put("userEnity",mUserEntity);
        Base.mUserEntity = mUserEntity;
    }



}
