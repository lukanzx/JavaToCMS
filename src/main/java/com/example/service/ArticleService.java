package com.example.service;

import com.example.entity.Article;

import java.util.List;

public interface ArticleService {
    void addArticle(Article article) throws Exception;

    void deleteArticle(int articleId) throws Exception;

    void updateArticle(Article article) throws Exception;

    Article getArticleById(int articleId) throws Exception;

    List<Article> getAllArticles() throws Exception;

    List<Article> getArticlesByTopicId(int topicId) throws Exception;

    List<Article> getArticlesByTopicName(String topicName) throws Exception;

    String getTopicNameByArticleId(int articleId) throws Exception;

    String getCategoryNameByArticleId(int articleId) throws Exception;

    String getImageUrlByArticleId(int articleId) throws Exception;

    int getCommentCountByArticleId(int creatorId) throws Exception;
}
