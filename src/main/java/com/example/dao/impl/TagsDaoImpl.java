package com.example.dao.impl;

import com.example.util.DBUtil;
import com.example.entity.Tags;
import com.example.dao.TagsDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagsDaoImpl implements TagsDao {

    // 添加一个新数据的函数
    public void addTag(Tags tags) throws Exception {
        String sql = "INSERT INTO tags (tag_name, tag_attribute, tag_usage_count, tag_description, created_by_user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tags.getTagName());
            pstmt.setString(2, tags.getTagAttribute());
            pstmt.setInt(3, tags.getTagUsageCount());
            pstmt.setString(4, tags.getTagDescription());
            pstmt.setInt(5, tags.getCreatedByUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }

    // 依靠id列表，删除一系列数据的函数
    public void deleteTagsByIdList(List<Integer> tagsList) throws Exception {
        String sql = "DELETE FROM tags WHERE tag_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int tagId : tagsList) {
                pstmt.setInt(1, tagId);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // 返回全部查询数据的函数
    public List<Tags> getAllTags() throws Exception {
        List<Tags> tagsList = new ArrayList<>();
        String sql = "SELECT * FROM tags";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tags tags = new Tags();
                tags.setTagId(rs.getInt("tag_id"));
                tags.setTagName(rs.getString("tag_name"));
                tags.setTagAttribute(rs.getString("tag_attribute"));
                tags.setTagUsageCount(rs.getInt("tag_usage_count"));
                tags.setTagDescription(rs.getString("tag_description"));
                tags.setCreatedAt(rs.getTimestamp("created_at"));
                tags.setUpdatedAt(rs.getTimestamp("updated_at"));
                tags.setCreatedByUserId(rs.getInt("created_by_user_id"));
                tagsList.add(tags);
            }
        } catch (Exception e) {
            throw e;
        }
        return tagsList;
    }

    // 修改表行数据的函数
    public void updateTag(Tags tags) throws Exception {
        String sql = "UPDATE tags SET tag_name = ?, tag_attribute = ?, tag_usage_count = ?, tag_description = ? WHERE tag_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tags.getTagName());
            pstmt.setString(2, tags.getTagAttribute());
            pstmt.setInt(3, tags.getTagUsageCount());
            pstmt.setString(4, tags.getTagDescription());
            pstmt.setInt(5, tags.getTagId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    // 删除一条新数据的函数，依靠tags_id
    public void deleteTagById(int tagId) throws Exception {
        String sql = "DELETE FROM tags WHERE tag_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tagId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    // 通过标签的属性，返回全部的标签。也就是返回标签属性相同的标签的LList
    public List<Tags> getTagsByAttribute(String tagsAttribute) throws Exception {
        List<Tags> tagsList = new ArrayList<>();
        String sql = "SELECT * FROM tags WHERE tag_attribute = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tagsAttribute);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Tags tag = new Tags();
                    tag.setTagId(rs.getInt("tag_id"));
                    tag.setTagName(rs.getString("tag_name"));
                    tag.setTagAttribute(rs.getString("tag_attribute"));
                    tag.setTagUsageCount(rs.getInt("tag_usage_count"));
                    tag.setTagDescription(rs.getString("tag_description"));
                    tag.setCreatedAt(rs.getTimestamp("created_at"));
                    tag.setUpdatedAt(rs.getTimestamp("updated_at"));
                    tag.setCreatedByUserId(rs.getInt("created_by_user_id"));
                    tagsList.add(tag);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return tagsList;
    }
}

