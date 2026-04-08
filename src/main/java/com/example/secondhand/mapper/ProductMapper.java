package com.example.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.secondhand.entity.Product;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {
    List<Product> findByUserId(Long userId);
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> search(String keyword);
}