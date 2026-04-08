package com.example.secondhand.service.impl;

import com.example.secondhand.entity.Review;
import com.example.secondhand.mapper.ReviewMapper;
import com.example.secondhand.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    
    @Override
    public Review add(Review review) {
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        reviewMapper.insert(review);
        return review;
    }
    
    @Override
    public Review findById(Long id) {
        return reviewMapper.selectById(id);
    }
    
    @Override
    public List<Review> findByProductId(Long productId) {
        return reviewMapper.findByProductId(productId);
    }
    
    @Override
    public List<Review> findByReviewerId(Long reviewerId) {
        return reviewMapper.findByReviewerId(reviewerId);
    }
    
    @Override
    public List<Review> findByReviewedId(Long reviewedId) {
        return reviewMapper.findByReviewedId(reviewedId);
    }
}