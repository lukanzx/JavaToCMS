/*
CREATE TABLE `user` (
  `user_id` INT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `user_type` ENUM('管理员', '普通用户') NOT NULL,
  `user_status` ENUM('正常', '禁用') NOT NULL,
  `gender` VARCHAR(10),
  `email` VARCHAR(255),
  `phone_number` VARCHAR(20),
  `avatar_id` INT DEFAULT NULL,
  `creation_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
*/

package com.example.entity;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String username;        // 用户名
    private String password;        // 密码
    private String userType;        // 用户类型
    private String userStatus;      // 用户状态,是否被封禁
    private String gender;          // 性别
    private String email;           // 邮箱
    private String phoneNumber;     // 电话号码
    private Integer avatarId;       // 头像的图片的id, Use Integer to allow null
    private Timestamp creationTime; // 创建的时间

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}