package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 刁剑
 * 2017/11/1
 * 注释:
 */
public class UserEntity implements Parcelable, Serializable {


    /**
     * password : 123456
     * emailValidateCode : a271c4
     * nickname :
     * remark : “”
     * avatar :
     * id : 0000000064bc27e70164c10956110001
     * emailValidated : true
     * version : 0
     * email : 154336119@qq.com
     * emailValidateCodeTime : 2018-07-22 16:08:06
     */

    private String password;
    private String emailValidateCode;
    private String nickname;
    private String remark;
    private String avatar;
    private String id;
    private boolean emailValidated;
    private int version;
    private String email;
    private String emailValidateCodeTime;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailValidateCode() {
        return emailValidateCode;
    }

    public void setEmailValidateCode(String emailValidateCode) {
        this.emailValidateCode = emailValidateCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEmailValidated() {
        return emailValidated;
    }

    public void setEmailValidated(boolean emailValidated) {
        this.emailValidated = emailValidated;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailValidateCodeTime() {
        return emailValidateCodeTime;
    }

    public void setEmailValidateCodeTime(String emailValidateCodeTime) {
        this.emailValidateCodeTime = emailValidateCodeTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.password);
        dest.writeString(this.emailValidateCode);
        dest.writeString(this.nickname);
        dest.writeString(this.remark);
        dest.writeString(this.avatar);
        dest.writeString(this.id);
        dest.writeByte(this.emailValidated ? (byte) 1 : (byte) 0);
        dest.writeInt(this.version);
        dest.writeString(this.email);
        dest.writeString(this.emailValidateCodeTime);
        dest.writeString(this.token);
    }

    public UserEntity() {
    }

    protected UserEntity(Parcel in) {
        this.password = in.readString();
        this.emailValidateCode = in.readString();
        this.nickname = in.readString();
        this.remark = in.readString();
        this.avatar = in.readString();
        this.id = in.readString();
        this.emailValidated = in.readByte() != 0;
        this.version = in.readInt();
        this.email = in.readString();
        this.emailValidateCodeTime = in.readString();
        this.token = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
