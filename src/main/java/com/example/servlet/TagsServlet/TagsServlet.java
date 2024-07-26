package com.example.servlet.TagsServlet;

import com.example.util.ExceptionUtil;
import com.example.util.RequestUtil;
import com.example.util.ResultUtil;
import com.example.entity.Tags;
import com.example.service.TagsService;
import com.example.service.impl.TagsServiceImpl;
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

@WebServlet("/TagsServlet")
public class TagsServlet extends HttpServlet {
    private final TagsService tagsService = new TagsServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            try {
                this.getAllTags(resp);
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

            if ("deleteTag".equals(req)) {
                this.deleteTag(response, jsonRequest);
            } else if ("deleteSomeTag".equals(req)) {
                this.deleteSomeTag(response, jsonRequest);
            } else if ("getAllTags".equals(req)) {
                this.getAllTags(response);
            } else if ("getCategoriesByAttribute".equals(req)) {
                this.getCategoriesByAttribute(response, jsonRequest);
            } else if ("addOneTags".equals(req)) {
                this.addOneTags(response, jsonRequest);
            } else if ("updateTags".equals(req)) {
                this.updateTags(response, jsonRequest);
            } else {
                out.println("请求不匹配");
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    private void updateTags(HttpServletResponse response, JSONObject jsonRequest) {

        // 接收前端的信息，更新一个标签的信息
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int tags_id = jsonRequest.getInteger("tags_id");
            String tags_name = jsonRequest.getString("tags_name").replaceAll("\\s+", "");
            String tags_attribute = jsonRequest.getString("tags_attribute").replaceAll("\\s+", "");
            String tags_description = jsonRequest.getString("tags_description").replaceAll("\\s+", "");
            int tag_usage_count = jsonRequest.getInteger("tag_usage_count");

            Tags tags = new Tags();
            tags.setTagId(tags_id);
            tags.setTagName(tags_name);
            tags.setTagAttribute(tags_attribute);
            tags.setTagDescription(tags_description);
            tags.setTagUsageCount(tag_usage_count);

            tagsService.updateTag(tags);
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

    private void addOneTags(HttpServletResponse response, JSONObject jsonRequest) {
        // 接收前端的json信息，创建一个表情 Tags
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String tags_name = jsonRequest.getString("tags_name");
            String tags_attribute = jsonRequest.getString("tags_attribute");
            String tags_description = jsonRequest.getString("tags_description");
            int created_by_user_id = jsonRequest.getInteger("created_by_user_id");

            Tags tags = new Tags();
            tags.setTagName(tags_name);
            tags.setTagAttribute(tags_attribute);
            tags.setTagDescription(tags_description);
            tags.setTagUsageCount(0);// 初始化标签的使用次数为 0
            tags.setCreatedByUserId(created_by_user_id);

            tagsService.addTag(tags);
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

    private void deleteTag(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int tags_id = jsonRequest.getInteger("tags_id");
            tagsService.deleteTagById(tags_id);
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

    private void deleteSomeTag(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            JSONArray tags_Ids = jsonRequest.getJSONArray("tags_Ids");
            for (int i = 0; i < tags_Ids.size(); i++) {
                tagsService.deleteTagById(tags_Ids.getInteger(i));
            }

            // 返回删除后的全部的剩下的信息
            this.getAllTags(response);
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    // 返回标签属性相同的全部的标签的集合json
    private void getCategoriesByAttribute(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String tags_attribute = jsonRequest.getString("tags_attribute").replaceAll("\\s+", "");

        try {
            List<Tags> tagsList = tagsService.getTagsByAttribute(tags_attribute);
            List<Map<String, Object>> tagsData = new ArrayList<>();
            for (Tags tag : tagsList) {
                Map<String, Object> tagMap = new LinkedHashMap<>();
                tagMap.put("tag_id", tag.getTagId());
                tagMap.put("tag_name", tag.getTagName());
                tagMap.put("tag_attribute", tag.getTagAttribute());
                tagMap.put("tag_usage_count", tag.getTagUsageCount());
                tagMap.put("tag_description", tag.getTagDescription());
                tagMap.put("created_at", tag.getCreatedAt().toString());
                tagMap.put("updated_at", tag.getUpdatedAt().toString());
                tagMap.put("created_by_user_id", tag.getCreatedByUserId());

                tagsData.add(tagMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", tagsList.size());
            responseMap.put("data", tagsData);

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

    protected void getAllTags(HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Tags> tagsList = tagsService.getAllTags();
            List<Map<String, Object>> tagsData = new ArrayList<>();
            for (Tags tag : tagsList) {
                Map<String, Object> tagMap = new LinkedHashMap<>();
                tagMap.put("tag_id", tag.getTagId());
                tagMap.put("tag_name", tag.getTagName());
                tagMap.put("tag_attribute", tag.getTagAttribute());
                tagMap.put("tag_usage_count", tag.getTagUsageCount());
                tagMap.put("tag_description", tag.getTagDescription());
                tagMap.put("created_at", tag.getCreatedAt().toString());
                tagMap.put("updated_at", tag.getUpdatedAt().toString());
                tagMap.put("created_by_user_id", tag.getCreatedByUserId());

                tagsData.add(tagMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", tagsList.size());
            responseMap.put("data", tagsData);

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
