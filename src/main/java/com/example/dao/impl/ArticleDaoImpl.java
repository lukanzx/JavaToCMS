package com.example.dao.impl;

import com.example.util.DBUtil;
import com.example.entity.Article;
import com.example.dao.ArticleDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDaoImpl implements ArticleDao {

    private Connection getConnection() throws Exception {
        return DBUtil.getConnection();
    }

    public void addArticle(Article article) throws Exception {
        String sql = "INSERT INTO article (article_title, article_content, topic_id, creator_id, cover_image_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, article.getArticleTitle());
            stmt.setString(2, article.getArticleContent());
            stmt.setInt(3, article.getTopicId());
            stmt.setInt(4, article.getCreatorId());
            stmt.setInt(5, article.getCoverImageId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void deleteArticle(int articleId) throws Exception {
        String sql = "DELETE FROM article WHERE article_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, articleId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void updateArticle(Article article) throws Exception {
        String sql = "UPDATE article SET article_title = ?, article_content = ?, topic_id = ?, cover_image_id = ? WHERE article_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, article.getArticleTitle());
            stmt.setString(2, article.getArticleContent());
            stmt.setInt(3, article.getTopicId());
            stmt.setInt(4, article.getCoverImageId());
            stmt.setInt(5, article.getArticleId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public Article getArticleById(int articleId) throws Exception {
        String sql = "SELECT * FROM article WHERE article_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Article article = new Article();
                    article.setArticleId(rs.getInt("article_id"));
                    article.setArticleTitle(rs.getString("article_title"));
                    article.setArticleContent(rs.getString("article_content"));
                    article.setCreationTime(rs.getTimestamp("creation_time"));
                    article.setUpdateTime(rs.getTimestamp("update_time"));
                    article.setTopicId(rs.getInt("topic_id"));
                    article.setCreatorId(rs.getInt("creator_id"));
                    article.setCoverImageId(rs.getInt("cover_image_id"));
                    return article;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Article> getAllArticles() throws Exception {
        String sql = "SELECT * FROM article";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            List<Article> articles = new ArrayList<>();
            while (rs.next()) {
                Article article = new Article();
                article.setArticleId(rs.getInt("article_id"));
                article.setArticleTitle(rs.getString("article_title"));
                article.setArticleContent(rs.getString("article_content"));
                article.setCreationTime(rs.getTimestamp("creation_time"));
                article.setUpdateTime(rs.getTimestamp("update_time"));
                article.setTopicId(rs.getInt("topic_id"));
                article.setCreatorId(rs.getInt("creator_id"));
                article.setCoverImageId(rs.getInt("cover_image_id"));
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Article> getArticlesByTopicId(int topicId) throws Exception {
        String sql = "SELECT * FROM article WHERE topic_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Article> articles = new ArrayList<>();
                while (rs.next()) {
                    Article article = new Article();
                    article.setArticleId(rs.getInt("article_id"));
                    article.setArticleTitle(rs.getString("article_title"));
                    article.setArticleContent(rs.getString("article_content"));
                    article.setCreationTime(rs.getTimestamp("creation_time"));
                    article.setUpdateTime(rs.getTimestamp("update_time"));
                    article.setTopicId(rs.getInt("topic_id"));
                    article.setCreatorId(rs.getInt("creator_id"));
                    article.setCoverImageId(rs.getInt("cover_image_id"));
                    articles.add(article);
                }
                return articles;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Article> getArticlesByTopicName(String topicName) throws Exception {
        String sql = "SELECT a.* FROM article a INNER JOIN topic t ON a.topic_id = t.topic_id WHERE t.topic_name LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + topicName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<Article> articles = new ArrayList<>();
                while (rs.next()) {
                    Article article = new Article();
                    article.setArticleId(rs.getInt("article_id"));
                    article.setArticleTitle(rs.getString("article_title"));
                    article.setArticleContent(rs.getString("article_content"));
                    article.setCreationTime(rs.getTimestamp("creation_time"));
                    article.setUpdateTime(rs.getTimestamp("update_time"));
                    article.setTopicId(rs.getInt("topic_id"));
                    article.setCreatorId(rs.getInt("creator_id"));
                    article.setCoverImageId(rs.getInt("cover_image_id"));
                    articles.add(article);
                }
                return articles;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public String getImageUrlByArticleId(int articleId) throws Exception {
        String sql = "SELECT i.image_url FROM article a INNER JOIN images i ON a.cover_image_id = i.image_id WHERE a.article_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("image_url");
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int getCommentCountByArticleId(int articleId) throws Exception {
        String sql = "SELECT COUNT(*) AS comment_count FROM comment WHERE article_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("comment_count");
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public String getTopicNameByArticleId(int articleId) throws Exception {
        String sql = "SELECT t.topic_name FROM article a INNER JOIN topic t ON a.topic_id = t.topic_id WHERE a.article_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("topic_name");
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return null;
    }

    public String getCategoryNameByArticleId(int articleId) throws Exception {
        String sql = "SELECT c.category_name FROM article a INNER JOIN topic t ON a.topic_id = t.topic_id INNER JOIN category c ON t.category_id = c.category_id WHERE a.article_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("category_name");
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return null;
    }
}
