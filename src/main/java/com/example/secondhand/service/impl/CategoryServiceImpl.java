package com.example.secondhand.service.impl;

import com.example.secondhand.entity.Category;
import com.example.secondhand.mapper.CategoryMapper;
import com.example.secondhand.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Override
    public Category create(Category category) {
        categoryMapper.insert(category);
        return category;
    }
    
    @Override
    public Category update(Category category) {
        categoryMapper.updateById(category);
        return category;
    }
    
    @Override
    public void delete(Integer id) {
        categoryMapper.deleteById(id);
    }
    
    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectById(id);
    }
    
    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }
    
    @Override
    public List<Category> findByParentId(Integer parentId) {
        return categoryMapper.findByParentId(parentId);
    }
}