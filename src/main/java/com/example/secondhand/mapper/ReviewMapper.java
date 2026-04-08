package com.example.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.secondhand.entity.Review;

import java.util.List;

public interface ReviewMapper extends BaseMapper<Review> {
    List<Review> findByProductId(Long productId);
    List<Review> findByReviewerId(Long reviewerId);
    List<Review> findByReviewedId(Long reviewedId);
}