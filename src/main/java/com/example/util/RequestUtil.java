package com.example.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;

public class RequestUtil {

    // 读取请求并转换为json对象
    public static JSONObject readRequestToJson(HttpServletRequest request) {
        StringBuilder requestBody = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
        try {
            JSONObject jsonRequest = JSONObject.parseObject(requestBody.toString());
            return jsonRequest;
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
        return null;
    }
}
