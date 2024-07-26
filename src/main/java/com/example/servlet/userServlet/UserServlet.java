package com.example.servlet.userServlet;

import com.example.util.ExceptionUtil;
import com.example.util.RequestUtil;
import com.example.util.ResultUtil;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    // GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.findAllUsers(response);
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    // POST,{"request":"Login"}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            JSONObject jsonRequest;
            jsonRequest = RequestUtil.readRequestToJson(request);
            if (jsonRequest == null) {
                out.println("jsonRequest为空");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } else {
                out.println("jsonRequest不为空");
            }

            String req = jsonRequest.getString("request");
            out.println("UserServlet@doPost 接收到的请求是" + req);

            if ("updateOneUser".equals(req)) {
                this.updateUser(response, jsonRequest);
            } else if ("getUserInfo".equals(req)) {
                this.findUserById(response, jsonRequest);
            } else if ("deleteSomeUsers".equals(req)) {
                this.deleteUser(response, jsonRequest);
            } else if ("addOneUser".equals(req)) {
                this.addUser(response, jsonRequest);
            } else if ("searchUsers".equals(req)) {
                this.searchUsers(response, jsonRequest);
            } else if ("Login".equals(req)) {
                this.findUserByUsernameAndPassword(response, jsonRequest);
            } else if ("findAllUsers".equals(req)) {
                this.findAllUsers(response);
            } else if ("findUserById".equals(req)) {
                this.findUserById(response, jsonRequest);
            } else if ("deleteOneUser".equals(req)) {
                this.deleteOneUser(response, jsonRequest);
            } else if ("addUser".equals(req)) {
                this.addUser(response, jsonRequest);
            } else {
                out.println("请求不匹配");
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }

    }

    protected void searchUsers(HttpServletResponse response, JSONObject jsonRequest) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String username = jsonRequest.getString("user_name").replaceAll("\\s+", "");
        String userType = jsonRequest.getString("user_type").replaceAll("\\s+", "");
        String userStatus = jsonRequest.getString("user_status").replaceAll("\\s+", "");

        List<User> userList;

        try {
            if (!username.isEmpty() && !userType.isEmpty() && !userStatus.isEmpty()) {
                userList = userService.findUserByUsernameAndUserTypeAndUserStatus(username, userType, userStatus);
            } else if (!username.isEmpty() && !userType.isEmpty()) {
                userList = userService.findUserByUsernameAndUserType(username, userType);
            } else if (!username.isEmpty() && !userStatus.isEmpty()) {
                userList = userService.findUserByUsernameAndUserStatus(username, userStatus);
            } else if (!userType.isEmpty() && !userStatus.isEmpty()) {
                userList = userService.findUserByUserTypeAndUserStatus(userType, userStatus);
            } else if (!username.isEmpty()) {
                userList = userService.findUserByUsername(username);
            } else if (!userType.isEmpty()) {
                userList = userService.findUserByUserType(userType);
            } else if (!userStatus.isEmpty()) {
                userList = userService.findUserByUserStatus(userStatus);
            } else {
                //response.getWriter().write(ResultUtil.jsonResponseFailed())
                List<Map<String, Object>> userData = new ArrayList<>();
                Map<String, Object> responseMap = new LinkedHashMap<>();
                responseMap.put("code", 0);
                responseMap.put("msg", "");
                responseMap.put("count", 0);
                responseMap.put("data", userData);
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(responseMap);
                response.getWriter().write(jsonResponse);
                return;
            }

            List<Map<String, Object>> userData = new ArrayList<>();
            for (User user : userList) {
                Map<String, Object> userMap = new LinkedHashMap<>();
                userMap.put("user_id", user.getUserId());
                userMap.put("avatar_id", userService.getAvatarPathByUserId(user.getUserId()));
                userMap.put("username", user.getUsername());
                userMap.put("user_type", user.getUserType());
                userMap.put("user_status", user.getUserStatus());
                userMap.put("gender", user.getGender());
                userMap.put("email", user.getEmail());
                userMap.put("phone_number", user.getPhoneNumber());
                userMap.put("creation_time", user.getCreationTime().toString());
                userData.add(userMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", userList.size());
            responseMap.put("data", userData);
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseMap);

            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    private void updateUser(HttpServletResponse response, JSONObject jsonRequest) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            int user_id = jsonRequest.getInteger("user_id");
            String userName = jsonRequest.getString("user_name").replaceAll("\\s+", "");
            String userPass = jsonRequest.getString("user_password").replaceAll("\\s+", "");
            String userType = jsonRequest.getString("user_type").replaceAll("\\s+", "");

            String userSex = jsonRequest.getString("user_sex").replaceAll("\\s+", "");
            String userEmail = jsonRequest.getString("user_email").replaceAll("\\s+", "");
            String userPhone = jsonRequest.getString("user_phone").replaceAll("\\s+", "");
            String userStatus = jsonRequest.getString("user_status").replaceAll("\\s+", "");
            String userAvatar = jsonRequest.getString("user_avatar").replaceAll("\\s+", "");

            User user = new User();
            user.setUserId(user_id);
            user.setUsername(userName);
            user.setPassword(userPass);
            user.setEmail(userEmail);
            user.setPhoneNumber(userPhone);

            try {
                user.setAvatarId(Integer.parseInt(userAvatar));
            } catch (Exception e) {
            }

            if (userType.equals("不修改")) {
                user.setUserType("");
            } else {
                user.setUserType(userType);
            }
            if (userSex.equals("不修改")) {
                user.setGender("");
            } else {
                user.setGender(userSex);
            }
            if (userStatus.equals("不修改")) {
                user.setUserStatus("");
            } else {
                user.setUserStatus(userStatus);
            }

            userService.updateUserAnyInfo(user);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());

        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw new Exception(e1);
            }
            throw new Exception(e);
        }

    }

    private void findUserByUsernameAndPassword(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userName = jsonRequest.getString("user_name").replaceAll("\\s+", "");
        String userPass = jsonRequest.getString("user_pass").replaceAll("\\s+", "");
        String captcha = jsonRequest.getString("captcha").replaceAll("\\s+", "");

        List<User> userList = userService.findUserByUsernameAndPassword(userName, userPass);
        if (!userList.isEmpty() && captcha.equals("xszg")) {

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("response", "success");
            jsonResponse.put("user_id", userList.get(0).getUserId());
            response.getWriter().write(jsonResponse.toString());

        } else {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
        }
    }

    // 查询所有用户的信息
    private void findAllUsers(HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<User> userList = userService.findAllUsers();
            List<Map<String, Object>> userData = new ArrayList<>();
            for (User user : userList) {
                Map<String, Object> userMap = new LinkedHashMap<>();
                userMap.put("user_id", user.getUserId());
                userMap.put("avatar_id", userService.getAvatarPathByUserId(user.getUserId()));
                userMap.put("username", user.getUsername());
                userMap.put("user_type", user.getUserType());
                userMap.put("user_status", user.getUserStatus());
                userMap.put("gender", user.getGender());
                userMap.put("email", user.getEmail());
                userMap.put("phone_number", user.getPhoneNumber());
                userMap.put("creation_time", user.getCreationTime().toString());

                userData.add(userMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", userList.size());
            responseMap.put("data", userData);

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseMap);
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw new Exception(e1);
            }
            throw new Exception(e);
        }
    }

    private void findUserById(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int user_id = jsonRequest.getInteger("user_id");

        try {
            User user = userService.findUserById(user_id);

            // 构建json字符串,返回用户信息给前端
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("username", user.getUsername());
            jsonResponse.put("email", user.getEmail());
            jsonResponse.put("phone_number", user.getPhoneNumber());
            jsonResponse.put("user_type", user.getUserType());
            jsonResponse.put("user_status", user.getUserStatus());
            jsonResponse.put("user_sex", user.getGender()); // Use the exact field name expected by the frontend

            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void deleteOneUser(HttpServletResponse response, JSONObject jsonRequest) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int user_id = jsonRequest.getInteger("user_id");

            userService.deleteUser(user_id);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw new Exception(e1);
            }
            throw new Exception(e);
        }

    }

    private void deleteUser(HttpServletResponse response, JSONObject jsonRequest) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            JSONArray users_id = jsonRequest.getJSONArray("user_id_s");
            for (int i = 0; i < users_id.size(); i++) {
                userService.deleteUser(users_id.getInteger(i));
            }

            // 返回删除后的全部的用户信息
            this.findAllUsers(response);
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    private void addUser(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userName = jsonRequest.getString("user_name").replaceAll("\\s+", "");
        String userPass = jsonRequest.getString("user_password").replaceAll("\\s+", "");
        String userType = jsonRequest.getString("user_type").replaceAll("\\s+", "");
        String userSex = jsonRequest.getString("user_sex").replaceAll("\\s+", "");
        String userEmail = jsonRequest.getString("user_email").replaceAll("\\s+", "");
        String userPhone = jsonRequest.getString("user_phone").replaceAll("\\s+", "");
        String userAvatar = jsonRequest.getString("user_avatar").replaceAll("\\s+", "");

        try {
            User user = new User();
            user.setUsername(userName);
            user.setPassword(userPass);
            user.setUserType(userType);
            user.setUserStatus("正常");
            user.setGender(userSex);
            user.setEmail(userEmail);
            user.setPhoneNumber(userPhone);
//            user.setAvatarId(10000);
            user.setAvatarId(Integer.parseInt(userAvatar));

            userService.addUser(user);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }

    }
}


