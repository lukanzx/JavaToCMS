package com.example.dao.impl;

import com.example.util.DBUtil;
import com.example.entity.Posters;
import com.example.dao.PostersDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostersDaoImpl implements PostersDao {

    // 添加一个新数据的函数
    public void addPoster(Posters posters) throws Exception {
        String sql = "INSERT INTO posters (poster_title, cover_image_id, position, status, created_by_user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, posters.getPosterTitle());
            pstmt.setInt(2, posters.getCoverImageId());
            pstmt.setString(3, posters.getPosition());
            pstmt.setString(4, posters.getStatus());
            pstmt.setInt(5, posters.getCreatedByUserId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    // 依靠id列表，删除一系列数据的函数
    public void deletePostersByIdList(List<Integer> postersList) throws Exception {
        String sql = "DELETE FROM posters WHERE poster_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int posterId : postersList) {
                pstmt.setInt(1, posterId);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // 返回全部查询数据的函数
    public List<Posters> getAllPosters() throws Exception {
        List<Posters> postersList = new ArrayList<>();
        String sql = "SELECT * FROM posters";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Posters posters = new Posters();
                posters.setPosterId(rs.getInt("poster_id"));
                posters.setPosterTitle(rs.getString("poster_title"));
                posters.setCoverImageId(rs.getInt("cover_image_id"));
                posters.setPosition(rs.getString("position"));
                posters.setStatus(rs.getString("status"));
                posters.setCreatedAt(rs.getTimestamp("created_at"));
                posters.setUpdatedAt(rs.getTimestamp("updated_at"));
                posters.setCreatedByUserId(rs.getInt("created_by_user_id"));
                postersList.add(posters);
            }
        } catch (Exception e) {
            throw e;
        }
        return postersList;
    }

    // 修改表行数据的函数
    public void updatePoster(Posters posters) throws Exception {
        String sql = "UPDATE posters SET poster_title = ?, cover_image_id = ?, position = ?, status = ? WHERE poster_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, posters.getPosterTitle());
            pstmt.setInt(2, posters.getCoverImageId());
            pstmt.setString(3, posters.getPosition());
            pstmt.setString(4, posters.getStatus());
            pstmt.setInt(5, posters.getPosterId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    // 删除一条新数据的函数，依靠posters_id
    public void deletePosterById(int posterId) throws Exception {
        String sql = "DELETE FROM posters WHERE poster_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, posterId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    // 根据poster_id获取image表的image_url
    public String getImageUrlByPosterId(int posterId) throws Exception {
        String sql = "SELECT i.image_url FROM posters p INNER JOIN images i ON p.cover_image_id = i.image_id WHERE p.poster_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, posterId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("image_url");
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return null;
    }

    public List<Posters> getAllPostersByLocation(String posterLocation) throws Exception {
        List<Posters> postersList = new ArrayList<>();
        String sql = "SELECT * FROM posters WHERE position = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, posterLocation);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Posters posters = new Posters();
                    posters.setPosterId(rs.getInt("poster_id"));
                    posters.setPosterTitle(rs.getString("poster_title"));
                    posters.setCoverImageId(rs.getInt("cover_image_id"));
                    posters.setPosition(rs.getString("position"));
                    posters.setStatus(rs.getString("status"));
                    posters.setCreatedAt(rs.getTimestamp("created_at"));
                    posters.setUpdatedAt(rs.getTimestamp("updated_at"));
                    posters.setCreatedByUserId(rs.getInt("created_by_user_id"));
                    postersList.add(posters);
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return postersList;
    }
}
