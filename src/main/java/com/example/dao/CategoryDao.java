package com.example.dao;

import com.example.entity.Category;

import java.util.List;

public interface CategoryDao {

    void addCategory(Category category) throws Exception;

    void deleteCategory(int categoryId) throws Exception;

    void updateCategory(Category category) throws Exception;

    Category getCategoryById(int categoryId) throws Exception;

    List<Category> getAllCategories() throws Exception;

    List<Category> getCategoriesByName(String categoryName) throws Exception;

    int getTopicCountByCategoryId(int categoryId) throws Exception;

    String getImageUrlByCategoryId(int categoryId) throws Exception;
}
