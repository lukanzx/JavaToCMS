package com.example.service;

import com.example.entity.Tags;

import java.util.List;

public interface TagsService {

    // 添加一个新数据的函数
    void addTag(Tags tags) throws Exception;

    // 依靠id列表，删除一系列数据的函数
    void deleteTagsByIdList(List<Integer> tagsList) throws Exception;

    // 返回全部查询数据的函数
    List<Tags> getAllTags() throws Exception;

    // 修改表行数据的函数
    void updateTag(Tags tags) throws Exception;

    // 删除一条新数据的函数，依靠tags_id
    void deleteTagById(int tagId) throws Exception;

    List<Tags> getTagsByAttribute(String tagsAttribute) throws Exception;
}
