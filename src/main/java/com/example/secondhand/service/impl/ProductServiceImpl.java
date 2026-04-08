package com.example.secondhand.service.impl;

import com.example.secondhand.entity.Product;
import com.example.secondhand.mapper.ProductMapper;
import com.example.secondhand.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public Product publish(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setStatus(1);
        productMapper.insert(product);
        return product;
    }
    
    @Override
    public Product update(Product product) {
        product.setUpdatedAt(LocalDateTime.now());
        productMapper.updateById(product);
        return product;
    }
    
    @Override
    public void delete(Long id) {
        productMapper.deleteById(id);
    }
    
    @Override
    public Product findById(Long id) {
        return productMapper.selectById(id);
    }
    
    @Override
    public List<Product> findByUserId(Long userId) {
        return productMapper.findByUserId(userId);
    }
    
    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productMapper.findByCategoryId(categoryId);
    }
    
    @Override
    public List<Product> search(String keyword) {
        return productMapper.search(keyword);
    }
    
    @Override
    public List<Product> findAll() {
        return productMapper.selectList(null);
    }
}