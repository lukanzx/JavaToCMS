package com.example.dao.impl;

import com.example.util.DBUtil;
import com.example.entity.Comment;
import com.example.dao.CommentDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDaoImpl implements CommentDao {

    private Connection getConnection() throws Exception {
        return DBUtil.getConnection();
    }

    public void addComment(Comment comment) throws Exception {
        String sql = "INSERT INTO comment (comment_content, article_id, creator_id, article_title) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, comment.getCommentContent());
            stmt.setInt(2, comment.getArticleId());
            stmt.setInt(3, comment.getCreatorId());
            stmt.setString(4, comment.getArticleTitle());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void deleteComment(int commentId) throws Exception {
        String sql = "DELETE FROM comment WHERE comment_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void updateComment(Comment comment) throws Exception {
        String sql = "UPDATE comment SET comment_content = ? WHERE comment_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, comment.getCommentContent());
            stmt.setInt(2, comment.getCommentId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public Comment getCommentById(int commentId) throws Exception {
        String sql = "SELECT * FROM comment WHERE comment_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(rs.getInt("comment_id"));
                    comment.setCommentContent(rs.getString("comment_content"));
                    comment.setCreationTime(rs.getTimestamp("creation_time"));
                    comment.setArticleId(rs.getInt("article_id"));
                    comment.setCreatorId(rs.getInt("creator_id"));
                    comment.setArticleTitle(rs.getString("article_title"));
                    return comment;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Comment> getAllComments() throws Exception {
        String sql = "SELECT * FROM comment";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            List<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setCommentContent(rs.getString("comment_content"));
                comment.setCreationTime(rs.getTimestamp("creation_time"));
                comment.setArticleId(rs.getInt("article_id"));
                comment.setCreatorId(rs.getInt("creator_id"));
                comment.setArticleTitle(rs.getString("article_title"));
                comments.add(comment);
            }
            return comments;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Comment> getCommentsByArticleId(int articleId) throws Exception {
        String sql = "SELECT * FROM comment WHERE article_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(rs.getInt("comment_id"));
                    comment.setCommentContent(rs.getString("comment_content"));
                    comment.setCreationTime(rs.getTimestamp("creation_time"));
                    comment.setArticleId(rs.getInt("article_id"));
                    comment.setCreatorId(rs.getInt("creator_id"));
                    comment.setArticleTitle(rs.getString("article_title"));
                    comments.add(comment);
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Comment> getCommentsByArticleTitle(String articleTitle) throws Exception {
        String sql = "SELECT * FROM comment WHERE article_title LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + articleTitle + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(rs.getInt("comment_id"));
                    comment.setCommentContent(rs.getString("comment_content"));
                    comment.setCreationTime(rs.getTimestamp("creation_time"));
                    comment.setArticleId(rs.getInt("article_id"));
                    comment.setCreatorId(rs.getInt("creator_id"));
                    comment.setArticleTitle(rs.getString("article_title"));
                    comments.add(comment);
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Comment> getCommentsByTimeRange(Date startTime, Date endTime) throws Exception {
        String sql = "SELECT * FROM comment WHERE creation_time BETWEEN ? AND ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(startTime.getTime()));
            stmt.setTimestamp(2, new Timestamp(endTime.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setCommentId(rs.getInt("comment_id"));
                    comment.setCommentContent(rs.getString("comment_content"));
                    comment.setCreationTime(rs.getTimestamp("creation_time"));
                    comment.setArticleId(rs.getInt("article_id"));
                    comment.setCreatorId(rs.getInt("creator_id"));
                    comment.setArticleTitle(rs.getString("article_title"));
                    comments.add(comment);
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
}
