package com.example.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadALiYunVideo(MultipartFile file);

    void removeMoreVideo(List<String> videoList);
}
