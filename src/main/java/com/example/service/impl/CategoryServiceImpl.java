package com.example.service.impl;

import com.example.entity.Category;
import com.example.dao.CategoryDao;
import com.example.dao.impl.CategoryDaoImpl;
import com.example.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    public void addCategory(Category category) throws Exception {
        try {
            categoryDao.addCategory(category);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteCategory(int categoryId) throws Exception {
        try {
            categoryDao.deleteCategory(categoryId);
        } catch (Exception e) {
            throw e;
        }

    }

    public void updateCategory(Category category) throws Exception {
        try {
            categoryDao.updateCategory(category);
        } catch (Exception e) {
            throw e;
        }
    }

    public Category getCategoryById(int categoryId) throws Exception {
        try {
            return categoryDao.getCategoryById(categoryId);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Category> getAllCategories() throws Exception {
        try {
            return categoryDao.getAllCategories();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Category> getCategoriesByName(String categoryName) throws Exception {
        try {
            return categoryDao.getCategoriesByName(categoryName);
        } catch (Exception e) {
            throw e;
        }
    }

    public int getTopicCountByCategoryId(int categoryId) throws Exception{
        try {
            return categoryDao.getTopicCountByCategoryId(categoryId);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getImageUrlByCategoryId(int categoryId) throws Exception{
        try {
            return categoryDao.getImageUrlByCategoryId(categoryId);
        } catch (Exception e) {
            throw e;
        }
    }
}
