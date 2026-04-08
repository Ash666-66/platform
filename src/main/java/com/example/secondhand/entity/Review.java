package com.example.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("review")
public class Review {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Long reviewerId;
    private Long reviewedId;
    private String content;
    private Integer rating; // 1-5星
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}