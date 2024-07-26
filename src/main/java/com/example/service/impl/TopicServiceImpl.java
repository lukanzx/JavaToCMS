package com.example.service.impl;

import com.example.entity.Topic;
import com.example.dao.TopicDao;
import com.example.dao.impl.TopicDaoImpl;
import com.example.service.TopicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    private final TopicDao topicDao = new TopicDaoImpl();

    public void addTopic(Topic topic) throws Exception {
        try {
            topicDao.addTopic(topic);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteTopic(int topicId) throws Exception {
        try {
            topicDao.deleteTopic(topicId);
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateTopic(Topic topic) throws Exception {
        try {
            topicDao.updateTopic(topic);
        } catch (Exception e) {
            throw e;
        }
    }

    public Topic getTopicById(int topicId) throws Exception {
        try {
            return topicDao.getTopicById(topicId);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Topic> getAllTopics() throws Exception {
        try {
            return topicDao.getAllTopics();
        } catch (Exception e) {
            throw e;
        }

    }

    public List<Topic> getTopicsByName(String topicName) throws Exception {
        try {
            return topicDao.getTopicsByName(topicName);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Topic> getTopicsByCategoryId(int categoryId) throws Exception {
        try {
            return topicDao.getTopicsByCategoryId(categoryId);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Topic> getTopicsByCategoryName(String categoryName) throws Exception {
        try {
            return topicDao.getTopicsByCategoryName(categoryName);
        } catch (Exception e) {
            throw e;
        }
    }

    // 通过 topic_id 获取 图片的地址image_url
    public String getImageUrlByTopicId(int topicId) throws Exception {
        try {
            return topicDao.getImageUrlByTopicId(topicId);
        } catch (Exception e) {
            throw e;
        }
    }

    // 通过 topic_id 获取 category_name 所属的分类名称
    public String getCategoryNameByTopicId(int topicId) throws Exception {
        try {
            return topicDao.getCategoryNameByTopicId(topicId);
        } catch (Exception e) {
            throw e;
        }
    }

    // 通过 topic_id 获取本专题下的的 文章数量总数量
    public int getArticleNumByTopicId(int topicId) throws Exception {
        try {
            return topicDao.getArticleNumByTopicId(topicId);
        } catch (Exception e) {
            throw e;
        }
    }
}
