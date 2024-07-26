package com.example.entity;

import java.sql.Timestamp;

/*
-- 创建专题表
CREATE TABLE `topic`
(
    `topic_id`          INT AUTO_INCREMENT PRIMARY KEY,
    `topic_name`        VARCHAR(255) NOT NULL,
    `topic_description` TEXT         NOT NULL,
    `creation_time`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_time`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `category_id`       INT          NOT NULL,
    `creator_id`        INT          NOT NULL,
    `cover_image_id`    INT       DEFAULT 10000,
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
    FOREIGN KEY (`creator_id`) REFERENCES `user` (`user_id`),
    FOREIGN KEY (`cover_image_id`) REFERENCES `images` (`image_id`)
);
*/
public class Topic {
    private int topicId;
    private String topicName;            // 专题名称
    private String topicDescription;     // 专题描述
    private Timestamp creationTime;      // 创建时间
    private Timestamp updateTime;        // 修改时间
    private int categoryId;              // 所属分类id
    private int creatorId;               // 创建者id
    private int coverImageId;            // 封面图片id

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(int coverImageId) {
        this.coverImageId = coverImageId;
    }

}
