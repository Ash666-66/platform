package com.example.secondhand.service;

import com.example.secondhand.entity.Review;

import java.util.List;

public interface ReviewService {
    Review add(Review review);
    Review findById(Long id);
    List<Review> findByProductId(Long productId);
    List<Review> findByReviewerId(Long reviewerId);
    List<Review> findByReviewedId(Long reviewedId);
}