package com.example.servlet.ArticleServlet;

import com.example.util.ExceptionUtil;
import com.example.util.RequestUtil;
import com.example.util.ResultUtil;
import com.example.entity.Article;
import com.example.service.ArticleService;
import com.example.service.impl.ArticleServiceImpl;
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

@WebServlet("/ArticleServlet")
public class ArticleServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            this.getAllArticles(response, null);
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

            if ("deleteArticle".equals(req)) {
                this.deleteArticle(response, jsonRequest);
            } else if ("deleteSomeArticle".equals(req)) {
                this.deleteSomeArticle(response, jsonRequest);
            } else if ("getAllArticles".equals(req)) {
                this.getAllArticles(response, jsonRequest);
            } else if ("getAllArticlesByTopicName".equals(req)) {
                this.getAllArticlesByTopicName(response, jsonRequest);
            }
            // 添加一篇文章
            else if ("addOneArticle".equals(req)) {
                this.addOneArticle(response, jsonRequest);
            }
            // 更新一篇文章
            else if ("updateArticle".equals(req)) {
                this.updateArticle(response, jsonRequest);
            } else {
                out.println("请求不匹配");
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    private void updateArticle(HttpServletResponse response, JSONObject jsonRequest) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int article_id = jsonRequest.getInteger("article_id");
        String article_title = jsonRequest.getString("article_title").replaceAll("\\s+", "");
        String article_content = jsonRequest.getString("article_content").replaceAll("\\s+", "");
        int cover_image_id = jsonRequest.getInteger("cover_image_id");
        int topic_id = jsonRequest.getInteger("topic_id");

        Article article = new Article();
        article.setArticleId(article_id);
        article.setArticleTitle(article_title);
        article.setArticleContent(article_content);
        article.setCoverImageId(cover_image_id);
        article.setTopicId(topic_id);

        try {
            articleService.updateArticle(article);
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

    private void addOneArticle(HttpServletResponse response, JSONObject jsonRequest) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 前端传上来的数据。三项
        String article_title = jsonRequest.getString("article_title").replaceAll("\\s+", "");
        String article_content = jsonRequest.getString("article_content").replaceAll("\\s+", "");
        int creator_id = jsonRequest.getInteger("creator_id");

        Article article = new Article();
        article.setArticleTitle(article_title);
        article.setArticleContent(article_content);
        article.setTopicId(0); // 默认专题
        article.setCreatorId(creator_id);
        article.setCoverImageId(10000); // 默认封面

        try {
            articleService.addArticle(article);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw e1;
            }
            throw e;
        }

    }

    protected void getAllArticles(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Article> articleList = articleService.getAllArticles();
            List<Map<String, Object>> articleData = new ArrayList<>();
            for (Article article : articleList) {
                Map<String, Object> articleMap = new LinkedHashMap<>();

                articleMap.put("article_id", article.getArticleId());

                // 通过 article_id 获取 image_url 图片封面的地址
                String imageUrl = articleService.getImageUrlByArticleId(article.getArticleId());
                articleMap.put("cover_image_id", imageUrl);

                articleMap.put("article_title", article.getArticleTitle());
                articleMap.put("article_content", article.getArticleContent());

                // 通过 article_id 获取 本篇文章评论数
                int commentCount = articleService.getCommentCountByArticleId(article.getCreatorId());
                articleMap.put("comment_count", commentCount);

                // 默认设置文章的浏览数 为 1024
                articleMap.put("look_num", "1024");

                articleMap.put("creation_time", article.getCreationTime().toString());
                articleMap.put("update_time", article.getUpdateTime().toString());

                // 通过 article_id 获取 topic_name（话题，专题名称）
                String topicName = articleService.getTopicNameByArticleId(article.getArticleId());
                articleMap.put("topic_name", topicName);

                // 通过 article_id 获取 category_name（分类名称）
                String categoryName = articleService.getCategoryNameByArticleId(article.getArticleId());
                articleMap.put("category_name", categoryName);

                articleMap.put("creator_id", article.getCreatorId());

                articleMap.put("is_recommend", "推荐");

                articleMap.put("is_top_up", "置顶");

                articleData.add(articleMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", articleList.size());
            responseMap.put("data", articleData);

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

    private void deleteArticle(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int article_id = jsonRequest.getInteger("article_id");
            articleService.deleteArticle(article_id);
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

    private void deleteSomeArticle(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            JSONArray article_ids = jsonRequest.getJSONArray("article_ids");
            for (int i = 0; i < article_ids.size(); i++) {
                articleService.deleteArticle(article_ids.getInteger(i));
            }
            // 返回删除后的全部的用户信息
            this.getAllArticles(response, new JSONObject());
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    // 通过话题名称获取所有文章
    private void getAllArticlesByTopicName(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String topic_name = jsonRequest.getString("topic_name").replaceAll("\\s+", "");

        try {
            List<Article> articleList = articleService.getArticlesByTopicName(topic_name);
            List<Map<String, Object>> articleData = new ArrayList<>();
            for (Article article : articleList) {
                Map<String, Object> articleMap = new LinkedHashMap<>();

                articleMap.put("article_id", article.getArticleId());

                // 通过 article_id 获取 image_url 图片封面的地址
                String imageUrl = articleService.getImageUrlByArticleId(article.getArticleId());
                articleMap.put("cover_image_id", imageUrl);

                articleMap.put("article_title", article.getArticleTitle());
                articleMap.put("article_content", article.getArticleContent());

                // 通过 article_id 获取 本篇文章评论数
                int commentCount = articleService.getCommentCountByArticleId(article.getCreatorId());
                articleMap.put("comment_count", commentCount);

                // 默认设置文章的浏览数 为 1024
                articleMap.put("look_num", "1024");

                articleMap.put("creation_time", article.getCreationTime().toString());
                articleMap.put("update_time", article.getUpdateTime().toString());

                // 通过 article_id 获取 topic_name（话题，专题名称）
                String topicName = articleService.getTopicNameByArticleId(article.getArticleId());
                articleMap.put("topic_name", topicName);

                // 通过 article_id 获取 category_name（分类名称）
                String categoryName = articleService.getCategoryNameByArticleId(article.getArticleId());
                articleMap.put("category_name", categoryName);

                articleMap.put("creator_id", article.getCreatorId());

                articleMap.put("is_recommend", "推荐");

                articleMap.put("is_top_up", "置顶");

                articleData.add(articleMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", articleList.size());
            responseMap.put("data", articleData);

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
//