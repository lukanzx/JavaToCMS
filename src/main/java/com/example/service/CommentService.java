package com.example.service;

import com.example.entity.Comment;

import java.util.Date;
import java.util.List;

public interface CommentService {

    void addComment(Comment comment) throws Exception;

    void deleteComment(int commentId) throws Exception;

    void updateComment(Comment comment) throws Exception;

    Comment getCommentById(int commentId) throws Exception;

    List<Comment> getAllComments() throws Exception;

    List<Comment> getCommentsByArticleId(int articleId) throws Exception;

    List<Comment> getCommentsByArticleTitle(String articleTitle) throws Exception;

    List<Comment> getCommentsByTimeRange(Date startTime, Date endTime) throws Exception;

}
