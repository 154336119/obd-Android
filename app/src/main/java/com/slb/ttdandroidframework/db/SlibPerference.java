package com.slb.ttdandroidframework.db;

import android.content.Context;
import android.content.SharedPreferences;

public class SlibPerference {
    private final String fileName = "slib.slibPerference";
    SharedPreferences mShared;
    Context mContext;
    public static final String MECHANISMNAME= "login_mechanismname";
    public static final String MOBILE="login_mobile";
    public static final String PWD="login_password";
    public static final String USERID="login_user_id";
    public static final String USERINFO="userinfo";

    public SlibPerference(Context context){
        this.mContext = context;
        mShared = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public void shardUserId(long userid){
        shardLong(USERID,userid);
    }
    public long getUserId(){
        return getShardLong(USERID,0);
    }
    public void shardMechanismname(String str){
        shardString(MECHANISMNAME,str);
    }
    public void shardMobilde(String mobile){
        shardString(MOBILE,mobile);
    }
    public void shardPwd(String pwd){
        shardString(PWD,pwd);
    }
    public String getMechanismname(){
        return getShardString(MECHANISMNAME,"");
    }
    public String getMobilde(){
        return getShardString(MOBILE,"");
    }
    public String getPWD(){
        return getShardString(PWD,"");
    }
    public void shardBoolean(String str, Boolean bool) {

        SharedPreferences.Editor edit = mShared.edit();
        edit.putBoolean(str, bool);
        edit.commit();
    }


    /**
     *
     *
     * @param str
     * @param bool
     */
    public Boolean getShardBoolean(String str, Boolean bool) {
        return mShared.getBoolean(str, bool);
    }

    public void shardString(String str, String str1) {
        SharedPreferences.Editor edit = mShared.edit();
        edit.putString(str, str1);
        edit.commit();
    }
    public void shardUserInfo(String userinfo){
        shardString(userinfo,USERINFO);
    }
    public String getUserInfo(){
        return getShardString(USERINFO,"");
    }

    /**
     *
     *
     * @param key
     * @param value
     */
    public void shardFloat(String key, float value) {
        SharedPreferences.Editor edit = mShared.edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    /**
     *
     * @param key
     * @param dft
     * @return
     */
    public Float getShardFloat(String key, float dft) {

        return mShared.getFloat(key, dft);

    }

    public String getShardString(String str, String str1) {

        return mShared.getString(str, str1);

    }

    public void shardInt(String str, int d) {
        SharedPreferences.Editor edit = mShared.edit();
        edit.putInt(str, d);
        edit.commit();
    }

    /**
     *
     *
     * @param
     * @param
     */
    public int getShardInt(String key, int dft) {

        return mShared.getInt(key, dft);

    }

    public void shardLong(String str, long d) {
        SharedPreferences.Editor edit = mShared.edit();
        edit.putLong(str, d);
        edit.commit();
    }

    public long getShardLong(String str, long d) {
        return mShared.getLong(str, d);
    }
}
