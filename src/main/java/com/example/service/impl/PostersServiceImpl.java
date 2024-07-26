package com.example.service.impl;

import com.example.entity.Posters;
import com.example.dao.PostersDao;
import com.example.dao.impl.PostersDaoImpl;
import com.example.service.PostersService;

import java.util.List;

public class PostersServiceImpl implements PostersService {

    private final PostersDao postersDao = new PostersDaoImpl();

    // 添加一个新数据的函数
    public void addPoster(Posters posters) throws Exception {
        try {
            postersDao.addPoster(posters);
        } catch (Exception e) {
            throw e;
        }
    }

    // 依靠id列表，删除一系列数据的函数
    public void deletePostersByIdList(List<Integer> postersList) throws Exception {
        try {
            postersDao.deletePostersByIdList(postersList);
        } catch (Exception e) {
            throw e;
        }
    }

    // 返回全部查询数据的函数
    public List<Posters> getAllPosters() throws Exception {
        try {
            return postersDao.getAllPosters();
        } catch (Exception e) {
            throw e;
        }
    }

    // 修改表行数据的函数
    public void updatePoster(Posters posters) throws Exception {
        try {
            postersDao.updatePoster(posters);
        } catch (Exception e) {
            throw e;
        }
    }

    // 删除一条新数据的函数，依靠posters_id
    public void deletePosterById(int posterId) throws Exception {
        try {
            postersDao.deletePosterById(posterId);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getImageUrlByPosterId(int posterId) throws Exception {
        try {
            return postersDao.getImageUrlByPosterId(posterId);
        } catch (Exception e) {
            throw e;
        }
    }
    public List<Posters> getAllPostersByLocation(String posterLocation) throws Exception{
        try {
            return postersDao.getAllPostersByLocation(posterLocation);
        } catch (Exception e) {
            throw e;
        }
    }
}
