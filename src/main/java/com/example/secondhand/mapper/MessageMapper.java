package com.example.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.secondhand.entity.Message;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findUnreadByReceiverId(Long receiverId);
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}