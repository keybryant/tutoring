package com.yuweilai.entity;

import java.util.Date;

public class UserBase {
    private String userId;

    private String account;

    private String password;

    private Date regTime;

    private String roleId;

    private Integer userState;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }
}