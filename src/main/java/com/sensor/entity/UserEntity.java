package com.sensor.entity;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserEntity {

    @NotNull(message = "不能为空")
    private long userId;
    private String userCode;
    //@NotNull(message="不能为空")
    private String userName;
    private String nickName;

    @NotBlank
    private String userPwd;
    private Date createDate;
    private Date updateDate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "UserEntity [userId=" + userId + ", userCode=" + userCode + ", userName=" + userName + ", nickName="
                + nickName + ", userPwd=" + userPwd + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
    }

}
