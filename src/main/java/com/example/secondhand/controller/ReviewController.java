package com.example.secondhand.controller;

import com.example.secondhand.entity.Review;
import com.example.secondhand.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Review review) {
        Map<String, Object> result = new HashMap<>();
        try {
            Review addedReview = reviewService.add(review);
            result.put("success", true);
            result.put("message", "评价成功");
            result.put("review", addedReview);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "评价失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/detail/{id}")
    public Map<String, Object> detail(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Review review = reviewService.findById(id);
            if (review != null) {
                result.put("success", true);
                result.put("review", review);
            } else {
                result.put("success", false);
                result.put("message", "评价不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/product/{productId}")
    public Map<String, Object> findByProductId(@PathVariable Long productId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Review> reviews = reviewService.findByProductId(productId);
            result.put("success", true);
            result.put("reviews", reviews);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/reviewer/{reviewerId}")
    public Map<String, Object> findByReviewerId(@PathVariable Long reviewerId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Review> reviews = reviewService.findByReviewerId(reviewerId);
            result.put("success", true);
            result.put("reviews", reviews);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/reviewed/{reviewedId}")
    public Map<String, Object> findByReviewedId(@PathVariable Long reviewedId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Review> reviews = reviewService.findByReviewedId(reviewedId);
            result.put("success", true);
            result.put("reviews", reviews);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        return result;
    }
}