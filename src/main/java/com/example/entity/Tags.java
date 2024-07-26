package com.example.entity;

import java.sql.Timestamp;

/*
CREATE TABLE tags (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(255) NOT NULL,
    tag_attribute VARCHAR(255) NOT NULL,
    tag_usage_count INT DEFAULT 0,
    tag_description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by_user_id INT NOT NULL,
    FOREIGN KEY (created_by_user_id) REFERENCES user(user_id)
);
*/
public class Tags {

    private int tagId;
    private String tagName;
    private String tagAttribute;
    private int tagUsageCount;
    private String tagDescription;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int createdByUserId;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagAttribute() {
        return tagAttribute;
    }

    public void setTagAttribute(String tagAttribute) {
        this.tagAttribute = tagAttribute;
    }

    public int getTagUsageCount() {
        return tagUsageCount;
    }

    public void setTagUsageCount(int tagUsageCount) {
        this.tagUsageCount = tagUsageCount;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

}