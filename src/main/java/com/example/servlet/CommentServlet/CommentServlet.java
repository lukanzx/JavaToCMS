package com.example.servlet.CommentServlet;

import com.example.util.ExceptionUtil;
import com.example.util.RequestUtil;
import com.example.util.ResultUtil;
import com.example.entity.Comment;
import com.example.service.CommentService;
import com.example.service.impl.CommentServiceImpl;
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

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
    private final CommentService commentService = new CommentServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // TDDO
        try {
            this.getAllComments(response, null);
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

            if ("deleteComment".equals(req)) {
                this.deleteComment(response, jsonRequest);
            } else if ("deleteSomeComment".equals(req)) {
                this.deleteSomeComment(response, jsonRequest);
            } else if ("getAllComments".equals(req)) {
                this.getAllComments(response, jsonRequest);
            } else if ("getCommentsByArticleName".equals(req)) {
                this.getCommentsByArticleName(response, jsonRequest);
            } // 添加一句评论
            else if ("addOneComment".equals(req)) {
                this.addOneComment(response, jsonRequest);
            }
            // 更新一句评论
            else if ("updateComment".equals(req)) {
                this.updateComment(response, jsonRequest);
            } else {
                out.println("请求不匹配");
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    private void updateComment(HttpServletResponse response, JSONObject jsonRequest) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            Comment comment = new Comment();
            comment.setCommentId(jsonRequest.getInteger("comment_id"));
            comment.setCommentContent(jsonRequest.getString("comment_content").replaceAll("\\s+", ""));
            commentService.updateComment(comment);
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

    private void addOneComment(HttpServletResponse response, JSONObject jsonRequest) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            Comment comment = new Comment();
            comment.setCommentContent(jsonRequest.getString("comment_content").replaceAll("\\s+", ""));
            comment.setCreatorId(jsonRequest.getInteger("creator_id"));
            comment.setArticleTitle(jsonRequest.getString("article_title").replaceAll("\\s+", ""));
            comment.setArticleId(jsonRequest.getInteger("article_id"));
            commentService.addComment(comment);
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

    private void deleteComment(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int comment_id = jsonRequest.getInteger("comment_id");
            commentService.deleteComment(comment_id);
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

    private void deleteSomeComment(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            JSONArray Comment_Ids = jsonRequest.getJSONArray("Comment_Ids");
            for (int i = 0; i < Comment_Ids.size(); i++) {
                commentService.deleteComment(Comment_Ids.getInteger(i));
            }
            // 返回删除后的剩下的全部的评论信息
            this.getAllComments(response, new JSONObject());
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    // 通过文章的标题，获取全部的评论
    private void getCommentsByArticleName(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String article_title = jsonRequest.getString("article_title").replaceAll("\\s+", "");

        try {
            List<Comment> commentList = commentService.getCommentsByArticleTitle(article_title);
            List<Map<String, Object>> commentData = new ArrayList<>();
            for (Comment comment : commentList) {
                Map<String, Object> commentMap = new LinkedHashMap<>();
                commentMap.put("comment_id", comment.getCommentId());
                commentMap.put("comment_content", comment.getCommentContent());
                commentMap.put("creation_time", comment.getCreationTime().toString());
                commentMap.put("creator_id", comment.getCreatorId());
                commentMap.put("article_title", comment.getArticleTitle());
                commentData.add(commentMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", commentList.size());
            responseMap.put("data", commentData);

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

    protected void getAllComments(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Comment> commentList = commentService.getAllComments();
            List<Map<String, Object>> commentData = new ArrayList<>();
            for (Comment comment : commentList) {
                Map<String, Object> commentMap = new LinkedHashMap<>();
                commentMap.put("comment_id", comment.getCommentId());
                commentMap.put("comment_content", comment.getCommentContent());
                commentMap.put("creation_time", comment.getCreationTime().toString());
                commentMap.put("creator_id", comment.getCreatorId());
                commentMap.put("article_title", comment.getArticleTitle());
                commentMap.put("article_id", comment.getArticleId());
                commentData.add(commentMap);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", commentList.size());
            responseMap.put("data", commentData);

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

