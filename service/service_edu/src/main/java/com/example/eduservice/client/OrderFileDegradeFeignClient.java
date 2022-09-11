package com.example.eduservice.client;

import com.example.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class OrderFileDegradeFeignClient implements OrderClient{
    @Override
    public boolean isBuy(String courseId, String memberId) {
        throw new GuliException(201,"查询购买课程情况出错");
    }
}
