package com.example.secondhand.service;

import com.example.secondhand.entity.Message;

import java.util.List;

public interface MessageService {
    Message send(Message message);
    void markAsRead(Long id);
    void markAllAsRead(Long receiverId);
    Message findById(Long id);
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findUnreadByReceiverId(Long receiverId);
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}