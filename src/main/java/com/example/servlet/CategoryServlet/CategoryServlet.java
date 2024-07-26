package com.example.servlet.CategoryServlet;

import com.example.util.ExceptionUtil;
import com.example.util.RequestUtil;
import com.example.util.ResultUtil;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.example.service.impl.CategoryServiceImpl;
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

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // TDDO
        try {
            this.getAllCategories(response, new JSONObject());
        } catch (Exception e) {
            ExceptionUtil.printException(e);
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

            if ("deleteCategory".equals(req)) {
                this.deleteCategory(response, jsonRequest);
            } else if ("deleteSomeCategory".equals(req)) {
                this.deleteSomeCategory(response, jsonRequest);
            } else if ("getAllCategories".equals(req)) {
                this.getAllCategories(response, jsonRequest);
            } else if ("getCategoriesByName".equals(req)) {
                this.getCategoriesByName(response, jsonRequest);
            }

            // 添加一个分类
            else if ("addOneCategory".equals(req)) {
                this.addOneCategory(response, jsonRequest);
            }
            // 更新一个分类
            else if ("updateCategory".equals(req)) {
                this.updateCategory(response, jsonRequest);
            } else {
                out.println("请求不匹配");
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    private void updateCategory(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int category_id = jsonRequest.getInteger("category_id");
        String category_name = jsonRequest.getString("category_name").replaceAll("\\s+", "");
        String category_description = jsonRequest.getString("category_description").replaceAll("\\s+", "");
        int cover_image_id = jsonRequest.getInteger("cover_image_id");

        Category category = new Category();
        category.setCategoryId(category_id);
        category.setCategoryName(category_name);
        category.setCategoryDescription(category_description);
        category.setCoverImageId(cover_image_id);
        try {
            categoryService.updateCategory(category);
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

    private void addOneCategory(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        // TODO
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String category_name = jsonRequest.getString("category_name").replaceAll("\\s+", "");
        String category_description = jsonRequest.getString("category_description").replaceAll("\\s+", "");
        int creator_id = jsonRequest.getInteger("creator_id");

        Category category = new Category();
        category.setCategoryName(category_name);
        category.setCategoryDescription(category_description);
        category.setCreatorId(creator_id);
        category.setCoverImageId(10000);
        try {
            categoryService.addCategory(category);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    // 获取全部的分类
    protected void getAllCategories(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Category> categoryList = categoryService.getAllCategories();
            List<Map<String, Object>> categoryData = new ArrayList<>();
            for (Category category : categoryList) {
                Map<String, Object> categoryMap = new LinkedHashMap<>();
                categoryMap.put("category_id", category.getCategoryId());

                // 查询每个分类的封面图片URL
                String imageUrl = categoryService.getImageUrlByCategoryId(category.getCategoryId());
                categoryMap.put("cover_image_id", imageUrl);

                categoryMap.put("category_name", category.getCategoryName());
                categoryMap.put("category_description", category.getCategoryDescription());
                categoryMap.put("creation_time", category.getCreationTime().toString());
                categoryMap.put("creator_id", category.getCreatorId());

                // 查询每个每个分类全部的专题数量
                int topicCount = categoryService.getTopicCountByCategoryId(category.getCategoryId());
                categoryMap.put("topic_num", topicCount);

                categoryData.add(categoryMap);

            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", categoryList.size());
            responseMap.put("data", categoryData);

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

    // 通过id删除一个分类
    private void deleteCategory(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int classify_id = jsonRequest.getInteger("classify_id");
            categoryService.deleteCategory(classify_id);
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

    // 通过id列表删除多个分类
    private void deleteSomeCategory(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            JSONArray category_Ids = jsonRequest.getJSONArray("category_Ids");
            for (int i = 0; i < category_Ids.size(); i++) {
                categoryService.deleteCategory(category_Ids.getInteger(i));
            }

            // 返回删除后的全部的用户信息
            this.getAllCategories(response, new JSONObject());
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    // 通过分类名称查询全部可能的分类
    private void getCategoriesByName(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String classify_name = jsonRequest.getString("classify_name").replaceAll("\\s+", "");

        if (classify_name.isEmpty()) {
            List<Category> categoryList = categoryService.getCategoriesByName(classify_name);
            List<Map<String, Object>> categoryData = new ArrayList<>();
            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", categoryList.size());
            responseMap.put("data", categoryData);
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseMap);
            response.getWriter().write(jsonResponse);
        }
        try {
            List<Category> categoryList = categoryService.getCategoriesByName(classify_name);
            List<Map<String, Object>> categoryData = new ArrayList<>();
            for (Category category : categoryList) {
                Map<String, Object> categoryMap = new LinkedHashMap<>();
                categoryMap.put("category_id", category.getCategoryId());

                // 查询每个分类的封面图片URL
                String imageUrl = categoryService.getImageUrlByCategoryId(category.getCategoryId());
                categoryMap.put("cover_image_id", imageUrl);

                categoryMap.put("category_name", category.getCategoryName());
                categoryMap.put("category_description", category.getCategoryDescription());
                categoryMap.put("creation_time", category.getCreationTime().toString());
                categoryMap.put("creator_id", category.getCreatorId());

                // 查询每个每个分类全部的专题数量
                int topicCount = categoryService.getTopicCountByCategoryId(category.getCategoryId());
                categoryMap.put("topic_num", topicCount);
                categoryData.add(categoryMap);

            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", categoryList.size());
            responseMap.put("data", categoryData);

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
