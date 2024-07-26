package com.example.config;

import com.example.util.ExceptionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties prop = new Properties();

    static {
        InputStream input = null;
        try {
            String filename = "application.properties";
            input = ConfigReader.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find " + filename);
            }
            prop.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load properties file: " + ex.getMessage(), ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    ExceptionUtil.printException(e);
                }
            }
        }
    }

    public static String getMysqlUrl() {
        return prop.getProperty("mysql.url");
    }

    public static String getMysqlUser() {
        return prop.getProperty("mysql.user");
    }

    public static String getMysqlPassword() {
        return prop.getProperty("mysql.password");
    }

    public static String getRedisHost() {
        return prop.getProperty("redis.host");
    }

    public static String getRedisPort() {
        return prop.getProperty("redis.port");
    }

    public static String getRedisPassword() {
        return prop.getProperty("redis.password");
    }

    public static String getUploadImagePath() {
        return prop.getProperty("uploadeImages.path");
    }

}
