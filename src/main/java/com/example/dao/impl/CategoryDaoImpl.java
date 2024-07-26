package com.example.dao.impl;

import com.example.util.DBUtil;
import com.example.entity.Category;
import com.example.dao.CategoryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private Connection getConnection() throws Exception {
        return DBUtil.getConnection();
    }

    public void addCategory(Category category) throws Exception {
        String sql = "INSERT INTO category (category_name, category_description, cover_image_id, creator_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getCategoryDescription());
            stmt.setInt(3, category.getCoverImageId());
            stmt.setInt(4, category.getCreatorId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void deleteCategory(int categoryId) throws Exception {
        String sql = "DELETE FROM category WHERE category_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void updateCategory(Category category) throws Exception {
        String sql = "UPDATE category SET category_name = ?, category_description = ?, cover_image_id = ? WHERE category_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getCategoryDescription());
            stmt.setInt(3, category.getCoverImageId());
            stmt.setInt(4, category.getCategoryId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public Category getCategoryById(int categoryId) throws Exception {
        String sql = "SELECT * FROM category WHERE category_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    category.setCategoryDescription(rs.getString("category_description"));
                    category.setCreationTime(rs.getTimestamp("creation_time"));
                    category.setCoverImageId(rs.getInt("cover_image_id"));
                    category.setCreatorId(rs.getInt("creator_id"));
                    return category;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Category> getAllCategories() throws Exception {
        String sql = "SELECT * FROM category";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setCategoryDescription(rs.getString("category_description"));
                category.setCreationTime(rs.getTimestamp("creation_time"));
                category.setCoverImageId(rs.getInt("cover_image_id"));
                category.setCreatorId(rs.getInt("creator_id"));
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Category> getCategoriesByName(String categoryName) throws Exception {
        String sql = "SELECT * FROM category WHERE category_name LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + categoryName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<Category> categories = new ArrayList<>();
                while (rs.next()) {
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    category.setCategoryDescription(rs.getString("category_description"));
                    category.setCreationTime(rs.getTimestamp("creation_time"));
                    category.setCoverImageId(rs.getInt("cover_image_id"));
                    category.setCreatorId(rs.getInt("creator_id"));
                    categories.add(category);
                }
                return categories;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    // 给出一个分类的id，返回该分类下的话题（专题）数量
    public int getTopicCountByCategoryId(int categoryId) throws Exception {
        String sql = "SELECT COUNT(*) AS topic_count FROM topic WHERE category_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("topic_count");
                } else {
                    return 0;
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //给出 category_id,返回images表中的image_url
    public String getImageUrlByCategoryId(int categoryId) throws Exception {
        String sql = "SELECT i.image_url FROM images i INNER JOIN category c ON i.image_id = c.cover_image_id WHERE c.category_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("image_url");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

}
