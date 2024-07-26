package com.example.dao.impl;

import com.example.util.DBUtil;
import com.example.entity.User;
import com.example.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.util.DBUtil.getConnection;

public class UserDoaImpl implements UserDao {

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setUserType(rs.getString("user_type"));
        user.setUserStatus(rs.getString("user_status"));
        user.setGender(rs.getString("gender"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAvatarId((Integer) rs.getObject("avatar_id"));
        user.setCreationTime(rs.getTimestamp("creation_time"));
        return user;
    }

    // 增加一个一个用户
    public boolean addUser(User user) throws Exception {
        String sql = "INSERT INTO user (username, password, user_type, user_status, gender, email, phone_number, avatar_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getUserType());
            stmt.setString(4, user.getUserStatus());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getPhoneNumber());
            stmt.setObject(8, user.getAvatarId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    // 根据用户的id删除一个用户
    public void deleteUser(int userId) throws Exception {
        try {
            String sql = "DELETE FROM user WHERE user_id = ?";
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    // 根据用户的id查找一个用户
    public User findUserById(int userId) throws Exception {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return null;
    }

    // 返回所有用户信息
    public List<User> findAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return users;
    }

    // 根据用户名和密码查找全部用户
    public List<User> findUserByUsernameAndPassword(String username, String password) throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Use while loop instead of if statement
                    users.add(extractUserFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return users;
    }

    // 根据用户名查找全部用户
    public List<User> findUserByUsername(String username) throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Use while loop instead of if statement
                    users.add(extractUserFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return users;
    }

    // 根据用户类型查找一个用户
    public List<User> findUserByUserType(String userType) throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE user_type = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Use while loop instead of if statement
                    users.add(extractUserFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return users;
    }

    // 根据用户状态查找一个用户
    public List<User> findUserByUserStatus(String userStatus) throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE user_status = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userStatus);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Use while loop instead of if statement
                    users.add(extractUserFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return users;
    }

    // 根据用户名和用户类型查找一个用户
    public List<User> findUserByUsernameAndUserType(String username, String userType) throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE username = ? AND user_type = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, userType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    while (rs.next()) { // Use while loop instead of if statement
                        users.add(extractUserFromResultSet(rs));
                    }
                }
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return users;
    }

    // 根据用户名和用户状态查找全部用户
    public List<User> findUserByUsernameAndUserStatus(String username, String userStatus) throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE username = ? AND user_status = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, userStatus);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Use while loop instead of if statement
                    users.add(extractUserFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return users;
    }

    // 根据用户类型和用户状态查找全部用户
    public List<User> findUserByUserTypeAndUserStatus(String userType, String userStatus) throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE user_type = ? AND user_status = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userType);
            stmt.setString(2, userStatus);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Use while loop instead of if statement
                    users.add(extractUserFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return users;
    }

    // 根据用户名和用户类型和用户状态查找全部用户
    public List<User> findUserByUsernameAndUserTypeAndUserStatus(String username, String userType, String userStatus) throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE username = ? AND user_type = ? AND user_status = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, userType);
            stmt.setString(3, userStatus);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(extractUserFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return users;
    }

    // 根据id 修改 一个用户信息,
    // 修改的字段有 用户名 密码 用户类型 用户状态 性别 邮箱 电话号码
    public void updateUser(User user) throws Exception {
        String sql = "UPDATE user SET username = ?, password = ?, user_type = ?, user_status = ?, gender = ?, email = ?, phone_number = ? WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getUserType());
            stmt.setString(4, user.getUserStatus());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getPhoneNumber());
            stmt.setInt(8, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    // 根据id 修改 一个用户信息任意可能得信息,
    // 修改的字段可能有 用户名 密码 用户类型 用户状态 性别 邮箱 电话号码 用户头像
    /*
     * User user 里面,只有 user_id 是一定存在的,其他字段可能是空值 "",
     * 如果某个字段为空,代表这个字段不进行修改,
     * 如果这个字段不为空,就代表这个字段需要进行修改,
     * 一个非常灵活的,动态的函数,
     */
    public void updateUserAnyInfo(User user) throws Exception {
        System.out.println("UserDaoimpl@updateUserAnyInfo 函数被调用");
        try {
            StringBuilder sql = new StringBuilder("UPDATE user SET ");
            List<Object> parameters = new ArrayList<>();
            boolean first = true;

            if (!user.getUsername().isEmpty()) {
                sql.append("username = ?, ");
                parameters.add(user.getUsername());
            }
            if (!user.getPassword().isEmpty()) {
                sql.append("password = ?, ");
                parameters.add(user.getPassword());
            }
            if (!user.getUserType().isEmpty()) {
                sql.append("user_type = ?, ");
                parameters.add(user.getUserType());
            }
            if (!user.getUserStatus().isEmpty()) {
                sql.append("user_status = ?, ");
                parameters.add(user.getUserStatus());
            }
            if (!user.getGender().isEmpty()) {
                sql.append("gender = ?, ");
                parameters.add(user.getGender());
            }
            if (!user.getEmail().isEmpty()) {
                sql.append("email = ?, ");
                parameters.add(user.getEmail());
            }
            if (!user.getPhoneNumber().isEmpty()) {
                sql.append("phone_number = ?, ");
                parameters.add(user.getPhoneNumber());
            }
            if (user.getAvatarId() != null) { // Assuming null means no update
                sql.append("avatar_id = ?, ");
                parameters.add(user.getAvatarId());
            }


            // Remove the last comma and space
            if (sql.toString().endsWith(", ")) {
                sql = new StringBuilder(sql.substring(0, sql.length() - 2));
            }

            sql.append(" WHERE user_id = ?");
            parameters.add(user.getUserId());

            System.out.println("sql: " + sql);

            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                for (int i = 0; i < parameters.size(); i++) {
                    stmt.setObject(i + 1, parameters.get(i));
                }
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new Exception(e);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据用户的id,获取用户的头像的图片的id,使用图像的id,获取图像的路径
    public String getAvatarPathByUserId(int userId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String avatarPath = null;

        try {

            conn = DBUtil.getConnection();
            String sql = "SELECT i.image_url FROM user u INNER JOIN images i ON u.avatar_id = i.image_id WHERE u.user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                avatarPath = rs.getString("image_url");
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return avatarPath;
    }
}
