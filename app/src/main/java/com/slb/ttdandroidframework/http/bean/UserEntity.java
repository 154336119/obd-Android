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
     * queryData : null
     * created : 2017-11-06 02:36:55
     * updated : 2017-11-06 03:02:34
     * userId : 39
     * loginName : 18483606213
     * userName : youpan
     * userType : “”lastLogin
     * userState : USER_STATE_REGISTR
     * orgName : org_test_3
     * mobile : 18483606213
     * password : “”
     * avatar : “”
     * remark : “”
     * parentAccount : “”
     * lastLogin : 2017-11-06 03:04:23
     * lastLoginIp : 192.168.1.110
     * lastLoginMac :
     */
    public int id;
    private String created;
    private String updated;
    private Integer userId;
    private String loginName;
    private String userName;
    private String userType;
    private String userState;
    private String orgName;
    private String mobile;
    private String unencryptedMobile;
    private String password;
    private String avatar;
    private String remark;
    private String parentAccount;
    private String lastLogin;
    private String lastLoginIp;
    private String lastLoginMac;
    private Boolean login;
    private String au;

    public String getUnencryptedMobile() {
        return unencryptedMobile;
    }

    public void setUnencryptedMobile(String unencryptedMobile) {
        this.unencryptedMobile = unencryptedMobile;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(String parentAccount) {
        this.parentAccount = parentAccount;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginMac() {
        return lastLoginMac;
    }

    public void setLastLoginMac(String lastLoginMac) {
        this.lastLoginMac = lastLoginMac;
    }

    public String getAu() {
        return au;
    }

    public void setAu(String au) {
        this.au = au;
    }


    public UserEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.created);
        dest.writeString(this.updated);
        dest.writeValue(this.userId);
        dest.writeString(this.loginName);
        dest.writeString(this.userName);
        dest.writeString(this.userType);
        dest.writeString(this.userState);
        dest.writeString(this.orgName);
        dest.writeString(this.mobile);
        dest.writeString(this.unencryptedMobile);
        dest.writeString(this.password);
        dest.writeString(this.avatar);
        dest.writeString(this.remark);
        dest.writeString(this.parentAccount);
        dest.writeString(this.lastLogin);
        dest.writeString(this.lastLoginIp);
        dest.writeString(this.lastLoginMac);
        dest.writeValue(this.login);
        dest.writeString(this.au);
    }

    protected UserEntity(Parcel in) {
        this.created = in.readString();
        this.updated = in.readString();
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.loginName = in.readString();
        this.userName = in.readString();
        this.userType = in.readString();
        this.userState = in.readString();
        this.orgName = in.readString();
        this.mobile = in.readString();
        this.unencryptedMobile = in.readString();
        this.password = in.readString();
        this.avatar = in.readString();
        this.remark = in.readString();
        this.parentAccount = in.readString();
        this.lastLogin = in.readString();
        this.lastLoginIp = in.readString();
        this.lastLoginMac = in.readString();
        this.login = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.au = in.readString();
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
