package com.example.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("message")
public class Message {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Integer type; // 1: 系统消息, 2: 交易消息, 3: 聊天消息
    private Integer status; // 0: 未读, 1: 已读
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}