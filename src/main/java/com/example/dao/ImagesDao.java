package com.example.dao;

import com.example.entity.Images;

import java.util.List;

public interface ImagesDao {

    // 添加一条图片信息
    boolean addImage(Images image) throws Exception;

    // 根据图片ID删除一条信息
    void deleteImage(int imageId) throws Exception;

    // 根据图片ID查找一条信息
    Images findImageById(int imageId) throws Exception;

    // 返回所有图片信息
    List<Images> findAllImages() throws Exception;

    List<Images> findAllImagesByName(String imageName) throws Exception;

    List<Images> findAllImagesByType(String imageType) throws Exception;

    List<Images> findAllImagesByNameAneType(String imageName, String imageType) throws Exception;

    // 更新一条图片信息
    void updateImage(Images image) throws Exception;
}