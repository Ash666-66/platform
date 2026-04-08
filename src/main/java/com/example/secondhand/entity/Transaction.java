package com.example.secondhand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("transaction")
public class Transaction {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Long buyerId;
    private Long sellerId;
    private BigDecimal price;
    private Integer status; // 1: 待确认, 2: 已确认, 3: 待付款, 4: 已付款, 5: 待发货, 6: 已发货, 7: 待收货, 8: 已完成, 9: 已取消
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}