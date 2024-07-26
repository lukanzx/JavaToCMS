package com.example.entity;

import java.sql.Timestamp;

/*
-- 创建分类表
CREATE TABLE `category`
(
    `category_id`          INT AUTO_INCREMENT PRIMARY KEY,
    `category_name`        VARCHAR(255) NOT NULL,
    `category_description` TEXT         NOT NULL,
    `creation_time`        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `cover_image_id`       INT       DEFAULT 10000,
    `creator_id`           INT          NOT NULL,
    FOREIGN KEY (`cover_image_id`) REFERENCES `images` (`image_id`),
    FOREIGN KEY (`creator_id`) REFERENCES `user` (`user_id`)
);
*/
public class Category {
    private int categoryId;
    private String categoryName;         // 分类名称
    private String categoryDescription;  // 分类描述
    private Timestamp creationTime;      // 创建时间
    private int coverImageId;            // 封面图片id
    private int creatorId;               // 创建者id

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public int getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(int coverImageId) {
        this.coverImageId = coverImageId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

}
