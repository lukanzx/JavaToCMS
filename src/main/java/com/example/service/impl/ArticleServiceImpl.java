package com.example.service.impl;

import com.example.entity.Article;
import com.example.dao.ArticleDao;
import com.example.dao.impl.ArticleDaoImpl;
import com.example.service.ArticleService;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {
    private final ArticleDao articleDao = new ArticleDaoImpl();

    public void addArticle(Article article) throws Exception {
        try {
            articleDao.addArticle(article);
        } catch (Exception e) {
            throw e;
        }

    }

    public void deleteArticle(int articleId) throws Exception {
        try {
            articleDao.deleteArticle(articleId);
        } catch (Exception e) {
            throw e;
        }

    }

    public void updateArticle(Article article) throws Exception {
        try {
            articleDao.updateArticle(article);
        } catch (Exception e) {
            throw e;
        }
    }

    public Article getArticleById(int articleId) throws Exception {
        try {
            return articleDao.getArticleById(articleId);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Article> getAllArticles() throws Exception {
        try {
            return articleDao.getAllArticles();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Article> getArticlesByTopicId(int topicId) throws Exception {
        try {
            return articleDao.getArticlesByTopicId(topicId);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Article> getArticlesByTopicName(String topicName) throws Exception {
        try {
            return articleDao.getArticlesByTopicName(topicName);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getTopicNameByArticleId(int articleId) throws Exception {
        try {
            return articleDao.getTopicNameByArticleId(articleId);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getCategoryNameByArticleId(int articleId) throws Exception {
        try {
            return articleDao.getCategoryNameByArticleId(articleId);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getImageUrlByArticleId(int articleId) throws Exception {
        try {
            return articleDao.getImageUrlByArticleId(articleId);
        } catch (Exception e) {
            throw e;
        }
    }

    public int getCommentCountByArticleId(int creatorId) throws Exception {
        try {
            return articleDao.getCommentCountByArticleId(creatorId);
        } catch (Exception e) {
            throw e;
        }
    }

}
