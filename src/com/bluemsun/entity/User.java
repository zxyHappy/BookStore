package com.bluemsun.entity;

public class User {
    private int userId;
    private String nickName;
    private String userName;
    private String Password;
    private String userPhoto;
    private int status;
    private int userStatus;
    private String Telephone;

    public User(){}

    public User(int userId,String nickName,String userName,String Password,String userPhoto,int status,int userStatus,String Telephone){
        this.userId = userId;
        this.nickName = nickName;
        this.userName = userName;
        this.Password = Password;
        this.userPhoto = userPhoto;
        this.status = status;
        this.userStatus = userStatus;
        this.Telephone = Telephone;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
}
