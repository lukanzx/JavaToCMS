package com.example.dao.impl;

import com.example.util.DBUtil;
import com.example.entity.Images;
import com.example.dao.ImagesDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImagesDaoImpl implements ImagesDao {

    // 添加一条图片信息
    @Override
    public boolean addImage(Images image) throws Exception {
        try {
            String sql = "INSERT INTO " + "images (image_url, image_name, image_size, dimensions, image_status, image_type, uploader_id) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, image.getImageUrl());
            stmt.setString(2, image.getImageName());
            stmt.setInt(3, image.getImageSize());
            stmt.setString(4, image.getDimensions());
            stmt.setBoolean(5, image.getImageStatus());
            stmt.setString(6, image.getImageType());
            stmt.setInt(7, image.getUploaderId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    // 根据图片的id删除图片信息
    @Override
    public void deleteImage(int imageId) throws Exception {
        try {
            String sql = "DELETE FROM images WHERE image_id = ?";
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, imageId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    // 根据图片ID查找一条信息
    @Override
    public Images findImageById(int imageId) throws Exception {
        try {
            String sql = "SELECT * FROM images WHERE image_id = ?";
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, imageId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Images images = new Images();
                images.setImageId(rs.getInt("image_id"));
                images.setImageUrl(rs.getString("image_url"));
                images.setImageName(rs.getString("image_name"));
                images.setImageSize(rs.getInt("image_size"));
                images.setDimensions(rs.getString("dimensions"));
                images.setImageStatus(rs.getBoolean("image_status"));
                images.setImageType(rs.getString("image_type"));
                images.setUploadTime(rs.getTimestamp("upload_time"));
                images.setUploaderId(rs.getInt("uploader_id"));
                return images;
            }

        } catch (SQLException e) {
            throw new Exception(e);
        }
        return null;
    }

    // 返回所有图片信息
    @Override
    public List<Images> findAllImages() throws Exception {
        System.out.println("ImagesDaoImpl@findAllImages 进入");
        try {
            System.out.println("ImagesDaoImpl@findAllImages 进入 try");
            List<Images> images = new ArrayList<>();
            String sql = "SELECT * FROM images";
            System.out.println("ImagesDaoImpl@findAllImages 将开始连接数据库");
            Connection conn = DBUtil.getConnection();
            System.out.println("ImagesDaoImpl@findAllImages 得到 conn");
            System.out.println("ImagesDaoImpl@findAllImages conn 得到 stmt");
            PreparedStatement stmt = conn.prepareStatement(sql);
            System.out.println("ImagesDaoImpl@findAllImages 得到 rs");
            ResultSet rs = stmt.executeQuery();
            System.out.println("ImagesDaoImpl@findAllImages 进入 while");
            while (rs.next()) {
                Images image = new Images();
                image.setImageId(rs.getInt("image_id"));
                image.setImageUrl(rs.getString("image_url"));
                image.setImageName(rs.getString("image_name"));
                image.setImageSize(rs.getInt("image_size"));
                image.setDimensions(rs.getString("dimensions"));
                image.setImageStatus(rs.getBoolean("image_status"));
                image.setImageType(rs.getString("image_type"));
                image.setUploadTime(rs.getTimestamp("upload_time"));
                image.setUploaderId(rs.getInt("uploader_id"));
                images.add(image);
            }
            System.out.println("ImagesDaoImpl@findAllImages 返回 images");
            return images;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public List<Images> findAllImagesByName(String imageName) throws Exception {
        List<Images> images = new ArrayList<>();
        String sql = "SELECT * FROM images WHERE image_name = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imageName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Images image = new Images();
                    image.setImageId(rs.getInt("image_id"));
                    image.setImageUrl(rs.getString("image_url"));
                    image.setImageName(rs.getString("image_name"));
                    image.setImageSize(rs.getInt("image_size"));
                    image.setDimensions(rs.getString("dimensions"));
                    image.setImageStatus(rs.getBoolean("image_status"));
                    image.setImageType(rs.getString("image_type"));
                    image.setUploadTime(rs.getTimestamp("upload_time"));
                    image.setUploaderId(rs.getObject("uploader_id") != null ? rs.getInt("uploader_id") : null);
                    images.add(image);
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return images;
    }

    @Override
    public List<Images> findAllImagesByType(String imageType) throws Exception {
        List<Images> images = new ArrayList<>();
        String sql = "SELECT * FROM images WHERE image_type = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imageType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Images image = new Images();
                    image.setImageId(rs.getInt("image_id"));
                    image.setImageUrl(rs.getString("image_url"));
                    image.setImageName(rs.getString("image_name"));
                    image.setImageSize(rs.getInt("image_size"));
                    image.setDimensions(rs.getString("dimensions"));
                    image.setImageStatus(rs.getBoolean("image_status"));
                    image.setImageType(rs.getString("image_type"));
                    image.setUploadTime(rs.getTimestamp("upload_time"));
                    image.setUploaderId(rs.getObject("uploader_id") != null ? rs.getInt("uploader_id") : null);
                    images.add(image);
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return images;
    }

    @Override
    public List<Images> findAllImagesByNameAneType(String imageName, String imageType) throws Exception {
        List<Images> images = new ArrayList<>();
        String sql = "SELECT * FROM images WHERE image_name = ? AND image_type = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imageName);
            stmt.setString(2, imageType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Images image = new Images();
                    image.setImageId(rs.getInt("image_id"));
                    image.setImageUrl(rs.getString("image_url"));
                    image.setImageName(rs.getString("image_name"));
                    image.setImageSize(rs.getInt("image_size"));
                    image.setDimensions(rs.getString("dimensions"));
                    image.setImageStatus(rs.getBoolean("image_status"));
                    image.setImageType(rs.getString("image_type"));
                    image.setUploadTime(rs.getTimestamp("upload_time"));
                    image.setUploaderId(rs.getObject("uploader_id") != null ? rs.getInt("uploader_id") : null);
                    images.add(image);
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return images;
    }

    // 更新一条图片信息
    @Override
    public void updateImage(Images image) throws Exception {
        try {
            String sql = "UPDATE images SET image_name = ?, image_status = ? WHERE image_id = ?";
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, image.getImageName());
            stmt.setBoolean(2, image.getImageStatus());
            stmt.setInt(3, image.getImageId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}

