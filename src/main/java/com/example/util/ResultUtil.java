package com.example.util;

import com.alibaba.fastjson.JSONObject;

public class ResultUtil {

    public static String jsonResponseSuccess() {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("response", "success");
        return jsonResponse.toString();
    } // {"response":"success}

    public static String jsonResponseFailed() {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("response", "failed");
        return jsonResponse.toString();
    }// {"response":"failed}

}
