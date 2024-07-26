package com.example.dao.impl;

import com.example.util.DBUtil;
import com.example.entity.Topic;
import com.example.dao.TopicDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicDaoImpl implements TopicDao {

    private Connection getConnection() throws Exception {
        return DBUtil.getConnection();
    }

    public void addTopic(Topic topic) throws Exception {
        String sql = "INSERT INTO topic (topic_name, topic_description, category_id, creator_id, cover_image_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, topic.getTopicName());
            stmt.setString(2, topic.getTopicDescription());
            stmt.setInt(3, topic.getCategoryId());
            stmt.setInt(4, topic.getCreatorId());
            stmt.setInt(5, topic.getCoverImageId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void deleteTopic(int topicId) throws Exception {
        String sql = "DELETE FROM topic WHERE topic_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void updateTopic(Topic topic) throws Exception {
        String sql = "UPDATE topic SET topic_name = ?, topic_description = ?, category_id = ?, cover_image_id = ? WHERE topic_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, topic.getTopicName());
            stmt.setString(2, topic.getTopicDescription());
            stmt.setInt(3, topic.getCategoryId());
            stmt.setInt(4, topic.getCoverImageId());
            stmt.setInt(5, topic.getTopicId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public Topic getTopicById(int topicId) throws Exception {
        String sql = "SELECT * FROM topic WHERE topic_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopicId(rs.getInt("topic_id"));
                    topic.setTopicName(rs.getString("topic_name"));
                    topic.setTopicDescription(rs.getString("topic_description"));
                    topic.setCreationTime(rs.getTimestamp("creation_time"));
                    topic.setUpdateTime(rs.getTimestamp("update_time"));
                    topic.setCategoryId(rs.getInt("category_id"));
                    topic.setCreatorId(rs.getInt("creator_id"));
                    topic.setCoverImageId(rs.getInt("cover_image_id"));
                    return topic;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Topic> getAllTopics() throws Exception {
        String sql = "SELECT * FROM topic";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            List<Topic> topics = new ArrayList<>();
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTopicId(rs.getInt("topic_id"));
                topic.setTopicName(rs.getString("topic_name"));
                topic.setTopicDescription(rs.getString("topic_description"));
                topic.setCreationTime(rs.getTimestamp("creation_time"));
                topic.setUpdateTime(rs.getTimestamp("update_time"));
                topic.setCategoryId(rs.getInt("category_id"));
                topic.setCreatorId(rs.getInt("creator_id"));
                topic.setCoverImageId(rs.getInt("cover_image_id"));
                topics.add(topic);
            }
            return topics;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Topic> getTopicsByName(String topicName) throws Exception {
        String sql = "SELECT * FROM topic WHERE topic_name LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + topicName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<Topic> topics = new ArrayList<>();
                while (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopicId(rs.getInt("topic_id"));
                    topic.setTopicName(rs.getString("topic_name"));
                    topic.setTopicDescription(rs.getString("topic_description"));
                    topic.setCreationTime(rs.getTimestamp("creation_time"));
                    topic.setUpdateTime(rs.getTimestamp("update_time"));
                    topic.setCategoryId(rs.getInt("category_id"));
                    topic.setCreatorId(rs.getInt("creator_id"));
                    topic.setCoverImageId(rs.getInt("cover_image_id"));
                    topics.add(topic);
                }
                return topics;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Topic> getTopicsByCategoryId(int categoryId) throws Exception {
        String sql = "SELECT * FROM topic WHERE category_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Topic> topics = new ArrayList<>();
                while (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopicId(rs.getInt("topic_id"));
                    topic.setTopicName(rs.getString("topic_name"));
                    topic.setTopicDescription(rs.getString("topic_description"));
                    topic.setCreationTime(rs.getTimestamp("creation_time"));
                    topic.setUpdateTime(rs.getTimestamp("update_time"));
                    topic.setCategoryId(rs.getInt("category_id"));
                    topic.setCreatorId(rs.getInt("creator_id"));
                    topic.setCoverImageId(rs.getInt("cover_image_id"));
                    topics.add(topic);
                }
                return topics;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Topic> getTopicsByCategoryName(String categoryName) throws Exception {
        String sql = "SELECT t.* FROM topic t INNER JOIN category c ON t.category_id = c.category_id WHERE c.category_name LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + categoryName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<Topic> topics = new ArrayList<>();
                while (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopicId(rs.getInt("topic_id"));
                    topic.setTopicName(rs.getString("topic_name"));
                    topic.setTopicDescription(rs.getString("topic_description"));
                    topic.setCreationTime(rs.getTimestamp("creation_time"));
                    topic.setUpdateTime(rs.getTimestamp("update_time"));
                    topic.setCategoryId(rs.getInt("category_id"));
                    topic.setCreatorId(rs.getInt("creator_id"));
                    topic.setCoverImageId(rs.getInt("cover_image_id"));
                    topics.add(topic);
                }
                return topics;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public String getImageUrlByTopicId(int topicId) throws Exception {
        String sql = "SELECT i.image_url FROM topic t INNER JOIN images i ON t.cover_image_id = i.image_id WHERE t.topic_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("image_url");
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return null;
    }

    public String getCategoryNameByTopicId(int topicId) throws Exception {
        String sql = "SELECT c.category_name FROM topic t INNER JOIN category c ON t.category_id = c.category_id WHERE t.topic_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("category_name");
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return null;
    }

    public int getArticleNumByTopicId(int topicId) throws Exception {
        String sql = "SELECT COUNT(*) AS article_count FROM article WHERE topic_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("article_count");
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return 0;
    }

}
