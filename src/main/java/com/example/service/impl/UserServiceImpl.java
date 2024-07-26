package com.example.service.impl;

import com.example.entity.User;
import com.example.dao.UserDao;
import com.example.dao.impl.UserDoaImpl;
import com.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDoaImpl();

    // 增加一个用户
    public boolean addUser(User user) throws Exception {
        try {
            return userDao.addUser(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户的id删除一个用户
    public void deleteUser(int userId) throws Exception {
        try {
            userDao.deleteUser(userId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户的id查找一个用户
    public User findUserById(int userId) throws Exception {
        try {
            return userDao.findUserById(userId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 返回所有用户信息
    public List<User> findAllUsers() throws Exception {
        try {
            return userDao.findAllUsers();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户名和密码查找全部用户
    public List<User> findUserByUsernameAndPassword(String username, String password) throws Exception {
        try {
            return userDao.findUserByUsernameAndPassword(username, password);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户名查找全部用户
    public List<User> findUserByUsername(String username) throws Exception {
        try {
            return userDao.findUserByUsername(username);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户类型查找全部用户
    public List<User> findUserByUserType(String userType) throws Exception {
        try {
            return userDao.findUserByUserType(userType);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户状态查找全部用户
    public List<User> findUserByUserStatus(String userStatus) throws Exception {
        try {
            return userDao.findUserByUserStatus(userStatus);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户名和用户类型查找全部用户
    public List<User> findUserByUsernameAndUserType(String username, String userType) throws Exception {
        try {
            return userDao.findUserByUsernameAndUserType(username, userType);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户名和用户状态查找全部用户
    public List<User> findUserByUsernameAndUserStatus(String username, String userStatus) throws Exception {
        try {
            return userDao.findUserByUsernameAndUserStatus(username, userStatus);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户类型和用户状态查找全部用户
    public List<User> findUserByUserTypeAndUserStatus(String userType, String userStatus) throws Exception {
        try {
            return userDao.findUserByUserTypeAndUserStatus(userType, userStatus);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户名和用户类型和用户状态查找全部用户
    public List<User> findUserByUsernameAndUserTypeAndUserStatus(String username, String userType, String userStatus) throws Exception {
        try {
            return userDao.findUserByUsernameAndUserTypeAndUserStatus(username, userType, userStatus);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据id 修改 全部用户信息,修改的字段有 用户名 密码 用户类型 用户状态 性别 邮箱 电话号码
    public void updateUser(User user) throws Exception {
        try {
            userDao.updateUser(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void updateUserAnyInfo(User user) throws Exception {
        try {
            userDao.updateUserAnyInfo(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户的id,获取用户的头像的图片的id,使用图像的id,获取图像的路径
    public String getAvatarPathByUserId(int userId) throws Exception {
        try {
            return userDao.getAvatarPathByUserId(userId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}

