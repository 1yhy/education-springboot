package com.example.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.example.commonutils.R;
import com.example.servicebase.exceptionhandler.GuliException;
import com.example.vod.service.VodService;
import com.example.vod.utils.ConstantVodUtils;
import com.example.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadALiYunVideo(MultipartFile file) {
        try{
            String fileName= file.getOriginalFilename();
            String title=fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId=null;
            if (response.isSuccess()) {
                videoId=response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId=response.getVideoId();

            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void removeMoreVideo(List<String> videoList) {
        try{
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request= new DeleteVideoRequest();
            request.setVideoIds(StringUtils.join(videoList.toArray(),","));
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(201,"删除失败");
        }
    }
}
