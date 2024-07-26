package com.example.servlet.SpecialTopicServlet;

import com.example.util.ExceptionUtil;
import com.example.util.RequestUtil;
import com.example.util.ResultUtil;
import com.example.entity.Topic;
import com.example.service.TopicService;
import com.example.service.impl.TopicServiceImpl;
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

@WebServlet("/SpecialTopicServlet")
public class SpecialTopicServlet extends HttpServlet {
    private final TopicService topicService = new TopicServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            this.getAllTopics(response, new JSONObject());
        } catch (Exception e) {
        }
    }

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

            if ("deleteTopic".equals(req)) {
                this.deleteTopic(response, jsonRequest);
            } else if ("deleteSomeTopic".equals(req)) {
                this.deleteSomeTopic(response, jsonRequest);
            } else if ("getAllTopics".equals(req)) {
                this.getAllTopics(response, null);
            } else if ("getTopicsByCategoryName".equals(req)) {
                this.getTopicsByCategoryName(response, jsonRequest);
            } else if ("addOneTopic".equals(req)) {
                this.addOneTopic(response, jsonRequest);
            } else if ("updateTopic".equals(req)) {
                this.updateTopic(response, jsonRequest);
            } else {
                out.println("请求不匹配");
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    private void updateTopic(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 通过前端获取的数据
        String topic_name = jsonRequest.getString("topic_name").replaceAll("\\s+", "");
        String topic_description = jsonRequest.getString("topic_description").replaceAll("\\s+", "");
        String category_id = jsonRequest.getString("category_id").replaceAll("\\s+", "");

        int cover_image_id = jsonRequest.getInteger("cover_image_id");
        int topic_id = jsonRequest.getInteger("topic_id");

        Topic topic = new Topic();
        topic.setTopicId(topic_id);
        topic.setTopicName(topic_name);
        topic.setTopicDescription(topic_description);
        topic.setCategoryId(Integer.parseInt(category_id));
        topic.setCoverImageId(cover_image_id);

        try {
            topicService.updateTopic(topic);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw e;
            }
        }
    }

    private void addOneTopic(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 通过前端获取的数据
        String topic_name = jsonRequest.getString("topic_name").replaceAll("\\s+", "");
        String topic_description = jsonRequest.getString("topic_description").replaceAll("\\s+", "");
        int creator_id = jsonRequest.getInteger("creator_id");

        Topic topic = new Topic();
        topic.setTopicName(topic_name);
        topic.setTopicDescription(topic_description);
        topic.setCreatorId(creator_id);

        topic.setCategoryId(1); // 默认的分类id
        topic.setCoverImageId(10000);// 默认的封面图片

        try {
            topicService.addTopic(topic);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw e;
            }
        }
    }

    private void deleteTopic(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int topic_id = jsonRequest.getInteger("topic_id");

            topicService.deleteTopic(topic_id);
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

    //删除多个话题
    private void deleteSomeTopic(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            JSONArray topic_Ids = jsonRequest.getJSONArray("topic_Ids");
            for (int i = 0; i < topic_Ids.size(); i++) {

                topicService.deleteTopic(topic_Ids.getInteger(i));
            }

            // 返回删除后的全部的用户信息
            this.getAllTopics(response, new JSONObject());
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    // 通过分类的名称获取对应的话题
    private void getTopicsByCategoryName(HttpServletResponse response, JSONObject jsonRequest) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String classify_name = jsonRequest.getString("classify_name").replaceAll("\\s+", "");


            // 通过分类名称获取对应的全部的话题
            List<Topic> topicList = topicService.getTopicsByCategoryName(classify_name);

            List<Map<String, Object>> topicData = new ArrayList<>();
            for (Topic topic : topicList) {
                Map<String, Object> topicMap = new LinkedHashMap<>();
                topicMap.put("topic_id", topic.getTopicId());

                // 通过 topic_id 获取 图片的地址image_url
                String imageUrl = topicService.getImageUrlByTopicId(topic.getTopicId());
                topicMap.put("cover_image_id", imageUrl);

                topicMap.put("topic_name", topic.getTopicName());
                topicMap.put("topic_description", topic.getTopicDescription());
                topicMap.put("creation_time", topic.getCreationTime().toString());
                topicMap.put("update_time", topic.getUpdateTime().toString());

                // 通过 topic_id 获取 category_name 所属的分类名称
                String categoryName = topicService.getCategoryNameByTopicId(topic.getTopicId());
                topicMap.put("category_name", categoryName);

                topicMap.put("creator_id", topic.getCreatorId());

                // 通过 topic_id 获取本专题下的的 文章数量总数量
                int articleNum = topicService.getArticleNumByTopicId(topic.getTopicId());
                topicMap.put("article_num", articleNum);

                // 默认设置阅读数量为1024
                topicMap.put("look_num", "1024");

                topicData.add(topicMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", topicList.size());
            responseMap.put("data", topicData);

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

    protected void getAllTopics(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Topic> topicList = topicService.getAllTopics();
            List<Map<String, Object>> topicData = new ArrayList<>();
            for (Topic topic : topicList) {
                Map<String, Object> topicMap = new LinkedHashMap<>();
                topicMap.put("topic_id", topic.getTopicId());

                // 通过 topic_id 获取 图片的地址image_url
                String imageUrl = topicService.getImageUrlByTopicId(topic.getTopicId());
                topicMap.put("cover_image_id", imageUrl);

                topicMap.put("topic_name", topic.getTopicName());
                topicMap.put("topic_description", topic.getTopicDescription());
                topicMap.put("creation_time", topic.getCreationTime().toString());
                topicMap.put("update_time", topic.getUpdateTime().toString());

                // 通过 topic_id 获取 category_name 所属的分类名称
                String categoryName = topicService.getCategoryNameByTopicId(topic.getTopicId());
                topicMap.put("category_name", categoryName);

                topicMap.put("creator_id", topic.getCreatorId());

                // 通过 topic_id 获取本专题下的的 文章数量总数量
                int articleNum = topicService.getArticleNumByTopicId(topic.getTopicId());
                topicMap.put("article_num", articleNum);

                // 默认设置阅读数量为1024
                topicMap.put("look_num", "1024");

                topicData.add(topicMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", topicList.size());
            responseMap.put("data", topicData);

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
