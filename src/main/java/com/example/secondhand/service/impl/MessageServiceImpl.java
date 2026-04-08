package com.example.secondhand.service.impl;

import com.example.secondhand.entity.Message;
import com.example.secondhand.mapper.MessageMapper;
import com.example.secondhand.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    
    @Override
    public Message send(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        message.setStatus(0); // 初始状态为未读
        messageMapper.insert(message);
        return message;
    }
    
    @Override
    public void markAsRead(Long id) {
        Message message = messageMapper.selectById(id);
        if (message != null) {
            message.setStatus(1);
            message.setUpdatedAt(LocalDateTime.now());
            messageMapper.updateById(message);
        }
    }
    
    @Override
    public void markAllAsRead(Long receiverId) {
        List<Message> messages = messageMapper.findUnreadByReceiverId(receiverId);
        for (Message message : messages) {
            message.setStatus(1);
            message.setUpdatedAt(LocalDateTime.now());
            messageMapper.updateById(message);
        }
    }
    
    @Override
    public Message findById(Long id) {
        return messageMapper.selectById(id);
    }
    
    @Override
    public List<Message> findByReceiverId(Long receiverId) {
        return messageMapper.findByReceiverId(receiverId);
    }
    
    @Override
    public List<Message> findUnreadByReceiverId(Long receiverId) {
        return messageMapper.findUnreadByReceiverId(receiverId);
    }
    
    @Override
    public List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        return messageMapper.findBySenderIdAndReceiverId(senderId, receiverId);
    }
}