package com.example.util;

import com.example.config.ConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = ConfigReader.getMysqlUrl();
    private static final String USER = ConfigReader.getMysqlUser();
    private static final String PASSWORD = ConfigReader.getMysqlPassword();
    private static Connection COON;

    static {
        try {
            // tomcat 启动的时候需要添加这个:Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            COON = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 获取数据库连接
    public static Connection getConnection() throws Exception {
        System.out.println("DBUtil@Connection 进入函数");
        synchronized (DBUtil.class) {
            System.out.println("DBUtil@Connection synchronized");
            //检查COON是否不为空，并且至少在1秒内有效
            if (COON != null && COON.isValid(1)) {
                System.out.println("返回可行的coon");
                System.out.println("DBUtil@Connection 返回 COON");
                return COON;
            } else {
                //如果现有连接不为空但无效，请关闭它
                if (COON != null) {
                    DBUtil.closeConnection(COON);
                }
                // 创建新的链接
                COON = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        }
        System.out.println("DBUtil@Connection return 之前返回 COON");
        return COON;
    }

    // 关闭链接的函数
    public static void closeConnection(Connection conn) throws Exception {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

}
