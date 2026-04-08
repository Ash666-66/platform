package com.example.secondhand.service;

import com.example.secondhand.entity.Product;

import java.util.List;

public interface ProductService {
    Product publish(Product product);
    Product update(Product product);
    void delete(Long id);
    Product findById(Long id);
    List<Product> findByUserId(Long userId);
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> search(String keyword);
    List<Product> findAll();
}