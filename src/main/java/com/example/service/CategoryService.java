package com.example.service;

import com.example.entity.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category) throws Exception;

    void deleteCategory(int categoryId) throws Exception;

    void updateCategory(Category category) throws Exception;

    Category getCategoryById(int categoryId) throws Exception;

    List<Category> getAllCategories() throws Exception;

    List<Category> getCategoriesByName(String categoryName) throws Exception;

    int getTopicCountByCategoryId(int categoryId) throws Exception;

    public String getImageUrlByCategoryId(int categoryId) throws Exception;
}
