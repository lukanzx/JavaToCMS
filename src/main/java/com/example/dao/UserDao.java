package com.example.dao;

import com.example.entity.User;

import java.util.List;

public interface UserDao {

    // 增加一个用户
    boolean addUser(User user) throws Exception;

    // 根据用户的id删除一个用户
    void deleteUser(int userId) throws Exception;

    // 根据用户的id查找一个用户
    User findUserById(int userId) throws Exception;

    // 返回所有用户信息
    List<User> findAllUsers() throws Exception;

    // 根据用户名和密码查找全部用户
    List<User> findUserByUsernameAndPassword(String username, String password) throws Exception;

    // 根据用户名查找全部用户
    List<User> findUserByUsername(String username) throws Exception;

    // 根据用户类型查找全部用户
    List<User> findUserByUserType(String userType) throws Exception;

    // 根据用户状态查找全部用户
    List<User> findUserByUserStatus(String userStatus) throws Exception;

    // 根据用户名和用户类型查找全部用户
    List<User> findUserByUsernameAndUserType(String username, String userType) throws Exception;

    // 根据用户名和用户状态查找全部用户
    List<User> findUserByUsernameAndUserStatus(String username, String userStatus) throws Exception;

    // 根据用户类型和用户状态查找全部用户
    List<User> findUserByUserTypeAndUserStatus(String userType, String userStatus) throws Exception;

    // 根据用户名和用户类型和用户状态查找全部用户
    List<User> findUserByUsernameAndUserTypeAndUserStatus(String username, String userType, String userStatus) throws Exception;

    // 根据id 修改 全部用户信息,修改的字段有 用户名 密码 用户类型 用户状态 性别 邮箱 电话号码
    void updateUser(User user) throws Exception;

    void updateUserAnyInfo(User user) throws Exception;

    // 根据用户的id,获取用户的头像的图片的id,使用图像的id,获取图像的路径
    String getAvatarPathByUserId(int userId) throws Exception;

}
