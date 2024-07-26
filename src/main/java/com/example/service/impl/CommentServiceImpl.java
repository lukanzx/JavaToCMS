package com.example.service.impl;

import com.example.entity.Comment;
import com.example.dao.CommentDao;
import com.example.dao.impl.CommentDaoImpl;
import com.example.service.CommentService;

import java.util.Date;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao = new CommentDaoImpl();

    public void addComment(Comment comment) throws Exception {
        try {
            commentDao.addComment(comment);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteComment(int commentId) throws Exception {
        try {
            commentDao.deleteComment(commentId);
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateComment(Comment comment) throws Exception {
        try {
            commentDao.updateComment(comment);
        } catch (Exception e) {
            throw e;
        }
    }

    public Comment getCommentById(int commentId) throws Exception {
        try {
            return commentDao.getCommentById(commentId);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Comment> getAllComments() throws Exception {
        try {
            return commentDao.getAllComments();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Comment> getCommentsByArticleId(int articleId) throws Exception {
        try {
            return commentDao.getCommentsByArticleId(articleId);
        } catch (Exception e) {
            throw e;
        }

    }

    public List<Comment> getCommentsByArticleTitle(String articleTitle) throws Exception {
        try {
            return commentDao.getCommentsByArticleTitle(articleTitle);
        } catch (Exception e) {
            throw e;
        }

    }

    public List<Comment> getCommentsByTimeRange(Date startTime, Date endTime) throws Exception {
        try {
            return commentDao.getCommentsByTimeRange(startTime, endTime);
        } catch (Exception e) {
            throw e;
        }
    }
}
