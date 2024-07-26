package com.example.dao;

import com.example.entity.Topic;

import java.util.List;

public interface TopicDao {

    void addTopic(Topic topic) throws Exception;

    void deleteTopic(int topicId) throws Exception;

    void updateTopic(Topic topic) throws Exception;

    Topic getTopicById(int topicId) throws Exception;

    List<Topic> getAllTopics() throws Exception;

    List<Topic> getTopicsByName(String topicName) throws Exception;

    List<Topic> getTopicsByCategoryId(int categoryId) throws Exception;

    List<Topic> getTopicsByCategoryName(String categoryName) throws Exception;

    // 通过 topic_id 获取 图片的地址image_url
    String getImageUrlByTopicId(int topicId) throws Exception;

    // 通过 topic_id 获取 category_name 所属的分类名称
    String getCategoryNameByTopicId(int topicId) throws Exception;

    // 通过 topic_id 获取本专题下的的 文章数量总数量
    int getArticleNumByTopicId(int topicId) throws Exception;
}
