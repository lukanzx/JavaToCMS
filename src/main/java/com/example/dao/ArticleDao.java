package com.example.dao;

import com.example.entity.Article;

import java.util.List;

public interface ArticleDao {

    void addArticle(Article article) throws Exception;

    void deleteArticle(int articleId) throws Exception;

    void updateArticle(Article article) throws Exception;

    Article getArticleById(int articleId) throws Exception;

    List<Article> getAllArticles() throws Exception;

    List<Article> getArticlesByTopicId(int topicId) throws Exception;

    List<Article> getArticlesByTopicName(String topicName) throws Exception;

    // 通过 article_id 获取 image_url 图片封面的地址
    String getImageUrlByArticleId(int articleId) throws Exception;

    // 通过 article_id 获取 本篇文章评论数
    int getCommentCountByArticleId(int articleId) throws Exception;

    // 通过 article_id 获取 topic_name（话题，专题名称）
    String getTopicNameByArticleId(int articleId) throws Exception;

    // 通过 article_id 获取 category_name（分类名称）
    String getCategoryNameByArticleId(int articleId) throws Exception;
}
