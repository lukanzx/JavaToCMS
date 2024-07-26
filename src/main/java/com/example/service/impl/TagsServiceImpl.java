package com.example.service.impl;

import com.example.entity.Tags;
import com.example.dao.TagsDao;
import com.example.dao.impl.TagsDaoImpl;
import com.example.service.TagsService;

import java.util.List;

public class TagsServiceImpl implements TagsService {

    private final TagsDao tagsDao = new TagsDaoImpl();

    // 添加一个新数据的函数
    public void addTag(Tags tags) throws Exception {
        try {
            tagsDao.addTag(tags);
        } catch (Exception e) {
            throw e;
        }
    }

    // 依靠id列表，删除一系列数据的函数
    public void deleteTagsByIdList(List<Integer> tagsList) throws Exception {
        try {
            tagsDao.deleteTagsByIdList(tagsList);
        } catch (Exception e) {
            throw e;
        }
    }

    // 返回全部查询数据的函数
    public List<Tags> getAllTags() throws Exception {
        try {
            return tagsDao.getAllTags();
        } catch (Exception e) {
            throw e;
        }
    }

    // 修改表行数据的函数
    public void updateTag(Tags tags) throws Exception {
        try {
            tagsDao.updateTag(tags);
        } catch (Exception e) {
            throw e;
        }

    }

    // 删除一条新数据的函数，依靠tags_id
    public void deleteTagById(int tagId) throws Exception {
        try {
            tagsDao.deleteTagById(tagId);
        } catch (Exception e) {
            throw e;
        }
    }
    public List<Tags> getTagsByAttribute(String tagsAttribute) throws Exception{
        try {
            return tagsDao.getTagsByAttribute(tagsAttribute);
        } catch (Exception e) {
            throw e;
        }
    }
}
