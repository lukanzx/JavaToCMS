package com.example.servlet.PosterServlet;

import com.example.util.ExceptionUtil;
import com.example.util.RequestUtil;
import com.example.util.ResultUtil;
import com.example.entity.Posters;
import com.example.service.PostersService;
import com.example.service.impl.PostersServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

@WebServlet("/PosterServlet")
public class PosterServlet extends HttpServlet {
    private final PostersService postersService = new PostersServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            try {
                this.getAllPosters(resp);
            } catch (Exception e) {
                ExceptionUtil.printException(e);
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    @Override
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
            out.println("接收到的请求是" + req);

            if ("deletePoster".equals(req)) {
                this.deletePoster(response, jsonRequest);
            } else if ("deleteSomePoster".equals(req)) {
                this.deleteSomePoster(response, jsonRequest);
            } else if ("getAllPosters".equals(req)) {
                this.getAllPosters(response);
            } else if ("getAllPostersByLocation".equals(req)) {
                this.getAllPostersByLocation(response, jsonRequest);
            } else if ("addOnePoster".equals(req)) {
                this.addOnePoster(response, jsonRequest);
            } else if ("updateOnePoster".equals(req)) {
                this.updateOnePoster(response, jsonRequest);
            } else {
                out.println("请求不匹配");
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    private void updateOnePoster(HttpServletResponse response, JSONObject jsonRequest) {
        // 接收前端的信息，更新一个海报的信息
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int poster_id = jsonRequest.getInteger("poster_id");
            String poster_title = jsonRequest.getString("poster_title").replaceAll("\\s+", "");
            String position = jsonRequest.getString("position").replaceAll("\\s+", "");
            String status = jsonRequest.getString("status").replaceAll("\\s+", "");
            int cover_image_id = jsonRequest.getInteger("cover_image_id");

            Posters poster = new Posters();
            poster.setPosterId(poster_id);
            poster.setPosterTitle(poster_title);
            poster.setPosition(position);
            poster.setCoverImageId(cover_image_id);
            poster.setStatus(status);

            postersService.updatePoster(poster);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                ExceptionUtil.printException(e1);
            }
            ExceptionUtil.printException(e);
        }
    }

    private void addOnePoster(HttpServletResponse response, JSONObject jsonRequest) {
        // 接收前端的信息，创建一个海报
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {

            String poster_title = jsonRequest.getString("poster_title").replaceAll("\\s+", "");
            String position = jsonRequest.getString("position").replaceAll("\\s+", "");
            int created_by_user_id = jsonRequest.getInteger("created_by_user_id");

            Posters poster = new Posters();
            poster.setPosterTitle(poster_title);
            poster.setPosition(position);
            poster.setCreatedByUserId(created_by_user_id);

            poster.setCoverImageId(10000);
            poster.setStatus("正常");

            postersService.addPoster(poster);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                ExceptionUtil.printException(e1);
            }
            ExceptionUtil.printException(e);
        }
    }

    private void deleteSomePoster(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            JSONArray poster_Ids = jsonRequest.getJSONArray("poster_Ids");
            for (int i = 0; i < poster_Ids.size(); i++) {

                postersService.deletePosterById(poster_Ids.getInteger(i));
            }
            // 返回删除后的全部的剩下的信息
            this.getAllPosters(response);
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    private void deletePoster(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int poster_id = jsonRequest.getInteger("poster_id");

            postersService.deletePosterById(poster_id);
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

    protected void getAllPosters(HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Posters> postersList = postersService.getAllPosters();
            List<Map<String, Object>> postersData = new ArrayList<>();
            for (Posters poster : postersList) {
                Map<String, Object> posterMap = new LinkedHashMap<>();
                posterMap.put("poster_id", poster.getPosterId());

                // 通过 poster_id 获取 image表的 image_url
                String imageUrl = postersService.getImageUrlByPosterId(poster.getPosterId());
                posterMap.put("cover_image_id", imageUrl);

                posterMap.put("poster_title", poster.getPosterTitle());
                posterMap.put("position", poster.getPosition());
                posterMap.put("status", poster.getStatus());
                posterMap.put("created_by_user_id", poster.getCreatedByUserId());
                posterMap.put("created_at", poster.getCreatedAt().toString());
                posterMap.put("updated_at", poster.getUpdatedAt().toString());

                postersData.add(posterMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", postersList.size());
            responseMap.put("data", postersData);

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
    // 通过海报的放置位置，查找全部可能的海报

    private void getAllPostersByLocation(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String poster_location = jsonRequest.getString("poster_location").replaceAll("\\s+", "");

            List<Posters> postersList = postersService.getAllPostersByLocation(poster_location);
            List<Map<String, Object>> postersData = new ArrayList<>();
            for (Posters poster : postersList) {
                Map<String, Object> posterMap = new LinkedHashMap<>();
                posterMap.put("poster_id", poster.getPosterId());

                // 通过 poster_id 获取 image表的 image_url
                String imageUrl = postersService.getImageUrlByPosterId(poster.getPosterId());
                posterMap.put("cover_image_id", imageUrl);

                posterMap.put("poster_title", poster.getPosterTitle());
                posterMap.put("position", poster.getPosition());
                posterMap.put("status", poster.getStatus());
                posterMap.put("created_by_user_id", poster.getCreatedByUserId());
                posterMap.put("created_at", poster.getCreatedAt().toString());
                posterMap.put("updated_at", poster.getUpdatedAt().toString());

                postersData.add(posterMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", postersList.size());
            responseMap.put("data", postersData);

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
}
