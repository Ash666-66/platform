package com.example.secondhand.controller;

import com.example.secondhand.entity.Message;
import com.example.secondhand.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    
    @PostMapping("/send")
    public Map<String, Object> send(@RequestBody Message message) {
        Map<String, Object> result = new HashMap<>();
        try {
            Message sentMessage = messageService.send(message);
            result.put("success", true);
            result.put("message", "发送成功");
            result.put("message", sentMessage);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "发送失败：" + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/mark-as-read/{id}")
    public Map<String, Object> markAsRead(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            messageService.markAsRead(id);
            result.put("success", true);
            result.put("message", "标记已读成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "标记已读失败：" + e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/mark-all-as-read/{receiverId}")
    public Map<String, Object> markAllAsRead(@PathVariable Long receiverId) {
        Map<String, Object> result = new HashMap<>();
        try {
            messageService.markAllAsRead(receiverId);
            result.put("success", true);
            result.put("message", "全部标记已读成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "全部标记已读失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/detail/{id}")
    public Map<String, Object> detail(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Message message = messageService.findById(id);
            if (message != null) {
                result.put("success", true);
                result.put("message", message);
            } else {
                result.put("success", false);
                result.put("message", "消息不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/receiver/{receiverId}")
    public Map<String, Object> findByReceiverId(@PathVariable Long receiverId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Message> messages = messageService.findByReceiverId(receiverId);
            result.put("success", true);
            result.put("messages", messages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/unread/{receiverId}")
    public Map<String, Object> findUnreadByReceiverId(@PathVariable Long receiverId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Message> messages = messageService.findUnreadByReceiverId(receiverId);
            result.put("success", true);
            result.put("messages", messages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/conversation")
    public Map<String, Object> findBySenderIdAndReceiverId(@RequestParam Long senderId, @RequestParam Long receiverId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Message> messages = messageService.findBySenderIdAndReceiverId(senderId, receiverId);
            result.put("success", true);
            result.put("messages", messages);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
}