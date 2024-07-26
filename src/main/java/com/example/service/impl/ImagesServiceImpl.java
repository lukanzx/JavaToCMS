package com.example.service.impl;

import com.example.entity.Images;
import com.example.dao.ImagesDao;
import com.example.dao.impl.ImagesDaoImpl;
import com.example.service.ImagesService;

import java.util.List;

public class ImagesServiceImpl implements ImagesService {
    private final ImagesDao imagesDao = new ImagesDaoImpl();

    // 添加一条图片信息
    @Override
    public boolean addImage(Images image) throws Exception {
        try {
            return imagesDao.addImage(image);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据图片ID删除一条信息
    @Override
    public void deleteImage(int imageId) throws Exception {
        try {
            imagesDao.deleteImage(imageId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 根据图片ID查找一条信息
    @Override
    public Images findImageById(int imageId) throws Exception {
        try {
            return imagesDao.findImageById(imageId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 返回所有图片信息
    @Override
    public List<Images> findAllImages() throws Exception {
        try {
            System.out.println("ImagesServiceImpl@findAllImages 将进入 imagesDao.findAllImages()");
            return imagesDao.findAllImages();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<Images> findAllImagesByName(String imageName) throws Exception {
        try {
            return imagesDao.findAllImagesByName(imageName);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<Images> findAllImagesByType(String imageType) throws Exception {
        try {
            return imagesDao.findAllImagesByType(imageType);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<Images> findAllImagesByNameAneType(String imageName, String imageType) throws Exception {
        try {
            return imagesDao.findAllImagesByNameAneType(imageName, imageType);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 更新一条图片信息
    @Override
    public void updateImage(Images image) throws Exception {
        try {
            imagesDao.updateImage(image);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}

