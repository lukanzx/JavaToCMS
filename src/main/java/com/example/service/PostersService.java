package com.example.service;

import com.example.entity.Posters;

import java.util.List;

public interface PostersService {
    // 添加一个新数据的函数
    void addPoster(Posters posters) throws Exception;

    // 依靠id列表，删除一系列数据的函数
    void deletePostersByIdList(List<Integer> postersList) throws Exception;

    // 返回全部查询数据的函数
    List<Posters> getAllPosters() throws Exception;

    // 修改表行数据的函数
    void updatePoster(Posters posters) throws Exception;

    // 删除一条新数据的函数，依靠posters_id
    void deletePosterById(int posterId) throws Exception;

    String getImageUrlByPosterId(int posterId) throws Exception;

    List<Posters> getAllPostersByLocation(String posterLocation) throws Exception;
}
