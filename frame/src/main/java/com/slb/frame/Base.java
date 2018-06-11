package com.slb.frame;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 描述：存放全局变量
 * Created by Lee
 * on 2016/9/20.
 */
public class Base {
    private static Context mContext;
    
    public static void  initialize(@NonNull Context context) {
        mContext = context;
    }

    public static Context getContext() {
        synchronized (Base.class){
            if (Base.mContext == null){
                throw new NullPointerException("Call Base.initialize(context) within your Application onCreate() method.");
            }
            return Base.mContext.getApplicationContext();
        }
    }
    public static int PAGE_SIZE=10;
}
