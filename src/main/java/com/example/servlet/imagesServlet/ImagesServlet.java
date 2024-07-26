package com.example.servlet.imagesServlet;

import com.example.util.ExceptionUtil;
import com.example.util.RequestUtil;
import com.example.util.ResultUtil;
import com.example.entity.Images;
import com.example.service.ImagesService;
import com.example.service.impl.ImagesServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

@WebServlet("/ImagesServlet")
public class ImagesServlet extends HttpServlet {
    private final ImagesService imagesService = new ImagesServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.findAllImages(response);
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            JSONObject jsonRequest;
            jsonRequest = RequestUtil.readRequestToJson(request);
            if (jsonRequest == null) {
                out.println("jsonRequest为空");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } else {
                out.println("jsonRequest不为空");
            }

            String req = jsonRequest.getString("request");
            out.println("ImagesServlet@doPost 接收到的请求是" + req);

            if ("deleteImage".equals(req)) {

                this.deleteImage(response, jsonRequest);
            } else if ("deleteSomeImages".equals(req)) {

                this.deleteSomeImage(response, jsonRequest);
            } else if ("findImageById".equals(req)) {
                this.findImageById(response, jsonRequest);
            } else if ("findAllImages".equals(req)) {

                this.findAllImages(response);
            } else if ("updateImage".equals(req)) {

                this.updateImage(response, jsonRequest);
            } else if ("searchImages".equals(req)) {

                this.searchImages(response, jsonRequest);
            } else {
                out.println("else 被调用");
            }
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }

    }

    // 增一条图片信息
    protected void addImage(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        // TODO
    }

    // 删-根据图片ID删除一条信息
    protected void deleteSomeImage(HttpServletResponse response, JSONObject jsonRequest) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            JSONArray image_id = jsonRequest.getJSONArray("image_id_s");
            for (int i = 0; i < image_id.size(); i++) {

                imagesService.deleteImage(image_id.getInteger(i));
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {

                List<Images> imagesList = null;
                List<Map<String, Object>> imagesData = new ArrayList<>();
                try {

                    imagesList = imagesService.findAllImages();
                    if (imagesList == null) {
                        out.println("imagesList为空");
                    } else {
                        out.println("imagesList不为空");
                    }

                } catch (Exception e) {
                    throw new Exception(e);
                }

                for (Images image : imagesList) {
                    Map<String, Object> imageData = new LinkedHashMap<>();
                    imageData.put("image_id", image.getImageId());
                    imageData.put("image_url", image.getImageUrl());
                    imageData.put("image_name", image.getImageName());
                    imageData.put("image_size", String.valueOf(image.getImageSize()));
                    imageData.put("dimensions", image.getDimensions());
                    imageData.put("image_status", String.valueOf(image.getImageStatus()));
                    imageData.put("image_type", image.getImageType());
                    imageData.put("upload_time", image.getUploadTime().toString());
                    imageData.put("uploader_id", image.getUploaderId() != null ? image.getUploaderId().toString() : "No");

                    imagesData.add(imageData);
                }

                Map<String, Object> responseMap = new LinkedHashMap<>();
                responseMap.put("code", 0);
                responseMap.put("msg", "");
                responseMap.put("count", imagesList.size());
                responseMap.put("data", imagesData);

                Gson gson = new Gson();
                String jsonResponse = gson.toJson(responseMap);

                response.getWriter().write(jsonResponse);

            } catch (Exception e) {
                try {
                    response.getWriter().write(ResultUtil.jsonResponseFailed());
                } catch (Exception e1) {
                    throw new Exception(e1);
                }
                throw new Exception(e);
            }
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw new Exception(e1);
            }
            throw new Exception(e);
        }

    }

    // 删-根据图片ID删除一条信息
    protected void deleteImage(HttpServletResponse response, JSONObject jsonRequest) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int image_id = jsonRequest.getInteger("image_id");

            imagesService.deleteImage(image_id);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw new Exception(e1);
            }
            throw new Exception(e);
        }

    }

    // 查-根据图片ID查找一条信息
    protected void findImageById(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        // TODO
    }

    protected void searchImages(HttpServletResponse response, JSONObject jsonRequest) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String image_name = jsonRequest.getString("image_name").replaceAll("\\s+", "");
        String image_type = jsonRequest.getString("image_type").replaceAll("\\s+", "");
        List<Images> imagesList;

        try {
            if (!image_name.isEmpty() && !image_type.isEmpty()) {
                imagesList = imagesService.findAllImagesByNameAneType(image_name, image_type);
            } else if (!image_name.isEmpty()) {
                imagesList = imagesService.findAllImagesByName(image_name);
            } else if (!image_type.isEmpty()) {
                imagesList = imagesService.findAllImagesByType(image_type);
            } else {
                List<Map<String, Object>> imagesData = new ArrayList<>();
                Map<String, Object> responseMap = new LinkedHashMap<>();
                responseMap.put("code", 0);
                responseMap.put("msg", "");
                responseMap.put("count", 0);
                responseMap.put("data", imagesData);
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(responseMap);
                response.getWriter().write(jsonResponse);
                return;
            }

            List<Map<String, Object>> imagesData = new ArrayList<>();
            for (Images image : imagesList) {
                Map<String, Object> imageData = new LinkedHashMap<>();
                imageData.put("image_id", image.getImageId());
                imageData.put("image_url", image.getImageUrl());
                imageData.put("image_name", image.getImageName());
                imageData.put("image_size", String.valueOf(image.getImageSize()));
                imageData.put("dimensions", image.getDimensions());
                imageData.put("image_status", String.valueOf(image.getImageStatus()));
                imageData.put("image_type", image.getImageType());
                imageData.put("upload_time", image.getUploadTime().toString());
                imageData.put("uploader_id", image.getUploaderId() != null ? image.getUploaderId().toString() : "No");

                imagesData.add(imageData);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", imagesList.size());
            responseMap.put("data", imagesData);

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseMap);
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            response.getWriter().write(ResultUtil.jsonResponseFailed());
            throw new Exception(e);
        }
    }

    // 查-返回所有图片信息
    protected void findAllImages(HttpServletResponse response) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            List<Images> imagesList = null;
            List<Map<String, Object>> imagesData = new ArrayList<>();
            try {

                imagesList = imagesService.findAllImages();
                if (imagesList == null) {
                    out.println("imagesList为空");
                } else {
                    out.println("imagesList不为空");
                }

            } catch (Exception e) {
                throw new Exception(e);
            }

            for (Images image : imagesList) {
                Map<String, Object> imageData = new LinkedHashMap<>();
                imageData.put("image_id", image.getImageId());
                imageData.put("image_url", image.getImageUrl());
                imageData.put("image_name", image.getImageName());
                imageData.put("image_size", String.valueOf(image.getImageSize()));
                imageData.put("dimensions", image.getDimensions());
                imageData.put("image_status", String.valueOf(image.getImageStatus()));
                imageData.put("image_type", image.getImageType());
                imageData.put("upload_time", image.getUploadTime().toString());
                imageData.put("uploader_id", image.getUploaderId() != null ? image.getUploaderId().toString() : "No");

                imagesData.add(imageData);
            }

            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("code", 0);
            responseMap.put("msg", "");
            responseMap.put("count", imagesList.size());
            responseMap.put("data", imagesData);

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseMap);

            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw new Exception(e1);
            }
            throw new Exception(e);
        }

    }

    // 改-更新一张图片信息
    protected void updateImage(HttpServletResponse response, JSONObject jsonRequest) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            int image_id = jsonRequest.getInteger("image_id");
            String image_name = jsonRequest.getString("image_name");
            String image_status = String.valueOf(jsonRequest.getBoolean("image_status"));

            Images images = new Images();
            images.setImageId(image_id);
            images.setImageName(image_name);
            images.setImageStatus(Boolean.parseBoolean(image_status));
            imagesService.updateImage(images);
            response.getWriter().write(ResultUtil.jsonResponseSuccess());
        } catch (Exception e) {
            try {
                response.getWriter().write(ResultUtil.jsonResponseFailed());
            } catch (Exception e1) {
                throw new Exception(e1);
            }
            throw new Exception(e);
        }

    }
}

